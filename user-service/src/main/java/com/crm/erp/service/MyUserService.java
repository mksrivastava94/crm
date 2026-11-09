package com.crm.erp.service;

import com.crm.erp.config.security.UserPrincipal;
import com.crm.erp.model.entity.User;
import com.crm.erp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.crm.erp.constant.UserConstant.USER_NOT_FOUND;

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
