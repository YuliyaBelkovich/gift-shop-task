package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.service.auth.UserDetailsImpl;
import com.epam.esm.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private UserDao userDao;
//
//
//}
