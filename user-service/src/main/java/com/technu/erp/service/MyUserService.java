package com.technu.erp.service;

import com.technu.erp.config.security.UserPrincipal;
import com.technu.erp.model.entity.User;
import com.technu.erp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.technu.erp.constant.UserConstant.USER_NOT_FOUND;

@Service
public class MyUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public MyUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailId(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));

        user.setLastLoginDateDisplay(user.getLastLoginDate());
        user.setLastLoginDate(LocalDate.now());
        userRepository.save(user);
        return new UserPrincipal(user);
    }
}
