package com.crm.erp.service;

import static com.crm.erp.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static com.crm.erp.constant.UserConstant.EMAIL_NOT_FOUND;
import static com.crm.erp.constant.UserConstant.INVALID_USER_ID;

import java.time.LocalDate;
import java.util.List;

import javax.validation.ConstraintViolationException;

import com.crm.erp.exception.domain.*;
import com.crm.erp.model.request.NewUserRequest;
import com.crm.erp.util.ParseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.crm.erp.config.security.UserPrincipal;
import com.crm.erp.model.request.LoginRequest;
import com.crm.erp.model.request.PasswordRequest;
import com.crm.erp.model.response.UserResponse;
import com.crm.erp.jwt.JwtTokenProvider;
import com.crm.erp.model.entity.Logging;
import com.crm.erp.model.entity.User;
import com.crm.erp.repository.LoggingRepository;
import com.crm.erp.repository.UserRepository;
import com.crm.erp.util.BasicUtil;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final LoggingRepository loggingRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, LoggingRepository loggingRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.loggingRepository = loggingRepository;
    }

    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<UserResponse> registerUser(NewUserRequest request) throws ConstraintViolationException, EmailExistException {

        isEmailExist(request.getEmailId());
        User user = ParseUtil.prepareNewUserData(request,bCryptPasswordEncoder);
        user = userRepository.save(user);
        UserResponse response = ParseUtil.convertUserToUserDto(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<UserResponse> login(LoginRequest loginReq) {
        LOGGER.info("Method login() started");
        authentication(loginReq.getEmailId(),loginReq.getPassword());
        User loginUser = userRepository.findByEmailId(loginReq.getEmailId()).get();
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        loggingRepository.save(Logging.prepareDataForUserLogging(loginUser));
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        UserResponse userDto = ParseUtil.convertUserToUserDto(loginUser);
        userDto.setJwtToken(getJwtToken(userPrincipal));
        LOGGER.info("Method login() ended");
        return new ResponseEntity<>(userDto,jwtHeader,HttpStatus.OK);
    }

    public ResponseEntity<List<UserResponse>> getAllUser(String orgId) {
        LOGGER.info("Method getAllUser() started");
        List<User> userList = userRepository.findAllByOrgId(orgId);
        List<UserResponse> userDtoList = ParseUtil.convertUserListToUserDtoList(userList);
        LOGGER.info("Method getAllUser() ended");
        return new ResponseEntity<>(userDtoList,HttpStatus.OK);
    }

    public ResponseEntity<UserResponse> getUserById(String id) throws UserNotFoundException {
        User eUser = findUserById(id);
        UserResponse userResponse = ParseUtil.convertUserToUserDto(eUser);
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }

    public ResponseEntity<UserResponse> changePassword(String id, PasswordRequest passwordRequest) throws  InvalidInputException, PasswordUnMatchException, UserNotFoundException {

        if(!BasicUtil.notEmptyNotNull(passwordRequest.getOldPassword()) || !BasicUtil.notEmptyNotNull(passwordRequest.getNewPassword()))
            throw new InvalidInputException("Please enter proper old and new password");

        User eUser = findUserById(id);
        if(!bCryptPasswordEncoder.matches(passwordRequest.getOldPassword(),eUser.getPassword()))
            throw new PasswordUnMatchException("Incorrect old password please enter correct password");

        eUser.setPassword(bCryptPasswordEncoder.encode(passwordRequest.getNewPassword()));
        eUser.setUpdatedDateTime(LocalDate.now());
        User updatedUser = userRepository.save(eUser);
        UserResponse userResponse = ParseUtil.convertUserToUserDto(updatedUser);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);

    }

    public ResponseEntity<String> forgetPassword(LoginRequest loginRequest) throws EmailNotFoundException {

        User eUser = userRepository.findByEmailId(loginRequest.getEmailId())
                .orElseThrow(()-> new EmailNotFoundException(EMAIL_NOT_FOUND));

        // TODO: 07/07/22  send a otp to Email Id
        return null;
    }

    private void authentication(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER,jwtTokenProvider.generateToken(userPrincipal));
        return headers;
    }

    private String getJwtToken(UserPrincipal userPrincipal) {
        return jwtTokenProvider.generateToken(userPrincipal);
    }

    private User findUserById(String id) throws UserNotFoundException {
        return userRepository.findById(id).
                orElseThrow(()-> new UserNotFoundException(INVALID_USER_ID));
    }
    private void isEmailExist(String emailId) throws EmailExistException {
        if(userRepository.countByEmailId(emailId) >0 )
            throw new EmailExistException("Email ID is already used please use another emailId : "+emailId);
    }
}
