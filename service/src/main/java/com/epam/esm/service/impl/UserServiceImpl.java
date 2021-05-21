package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.request.LoginRequest;
import com.epam.esm.dto.request.SignupRequest;
import com.epam.esm.dto.request.UserDetailsImpl;
import com.epam.esm.dto.response.JwtResponse;
import com.epam.esm.dto.response.OrderResponse;
import com.epam.esm.dto.response.UserResponse;
import com.epam.esm.exception.ExceptionDefinition;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.models.*;
import com.epam.esm.service.RoleService;
import com.epam.esm.service.UserService;
import com.epam.esm.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private AuthenticationManager authenticationManager;
    private UserDao userDao;
    private JwtUtils jwtUtils;
    private PasswordEncoder encoder;
    private RoleService roleService;

    @Autowired
    public UserServiceImpl(UserDao userDao,
                           AuthenticationManager authenticationManager,
                           JwtUtils jwtUtils,
                           PasswordEncoder encoder,
                           RoleService roleService) {
        this.userDao = userDao;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
        this.roleService = roleService;
    }

    @Override
    public UserResponse findById(int id) {
        return UserResponse.toDto(userDao.findById(id)
                .orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND)));
    }

    @Override
    public PageableResponse<UserResponse> findAll(int page, int pageSize) {
        PageableResponse<User> users = userDao.findAll(page, pageSize);
        List<UserResponse> responses = users.getResponses().stream()
                .map(UserResponse::toDto).collect(Collectors.toList());
        return new PageableResponse<>(responses,
                users.getCurrentPage(),
                users.getLastPage(),
                users.getPageSize(),
                users.getTotalElements());
    }

    @Override
    public PageableResponse<OrderResponse> findOrdersByUserId(int id, int page, int pageSize) {
        PageableResponse<Order> orders = userDao.findUserOrders(id, page, pageSize);
        List<OrderResponse> responses = orders.getResponses().stream()
                .map(OrderResponse::toDto).collect(Collectors.toList());
        return new PageableResponse<>(responses,
                orders.getCurrentPage(),
                orders.getLastPage(),
                orders.getPageSize(),
                orders.getTotalElements());
    }

    @Override
    public OrderResponse findOrderById(int userId, int orderId) {
        return OrderResponse.toDto(userDao.findUserOrderById(userId, orderId)
                .orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND)));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userDao.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public User findByName(ERole name) {
        return userDao.findByName(name.name()).orElseThrow(() -> new ServiceException(ExceptionDefinition.IDENTITY_NOT_FOUND));
    }

    @Override
    public JwtResponse logIn(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @Override
    public void register(SignupRequest signupRequest) {
        if (userDao.existsByEmail(signupRequest.getEmail())) {
            throw new ServiceException(ExceptionDefinition.REGISTER_EMAIL);
        }
        if (userDao.existsByUsername(signupRequest.getUsername())) {
            throw new ServiceException(ExceptionDefinition.REGISTER_NAME);
        }
        Set<Role> rolesSet = new HashSet<>();
        rolesSet.add(roleService.findByName(ERole.ROLE_USER));
        User user = User.builder()
                .setName(signupRequest.getUsername())
                .setEmail(signupRequest.getEmail())
                .setPassword(encoder.encode(signupRequest.getPassword()))
                .setRoles(rolesSet)
                .build();
        userDao.add(user);
    }


}
