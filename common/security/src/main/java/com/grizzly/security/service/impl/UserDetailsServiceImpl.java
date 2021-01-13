package com.grizzly.security.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        //直接写死数据信息，可以在这里获取数据库的信息并进行验证
//        UserDetails userDetails  = User.withUsername(username).password(new BCryptPasswordEncoder().encode("123456"))
//                .authorities("admin","editor").build();
//        return userDetails;
//    }
//}