package com.epam.esm.service.auth;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.request.LoginRequest;
import com.epam.esm.dto.request.SignupRequest;
import com.epam.esm.dto.response.JwtResponse;
import com.epam.esm.exception.ExceptionDefinition;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.models.ERole;
import com.epam.esm.models.Role;
import com.epam.esm.models.User;
import com.epam.esm.service.RoleService;
import com.epam.esm.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class AuthenticationManagerHelper {

    private AuthenticationManager authenticationManager;
    private UserDao userDao;
    private JwtUtils jwtUtils;
    private PasswordEncoder encoder;
    private RoleService roleService;

    @Autowired
    public AuthenticationManagerHelper(AuthenticationManager authenticationManager,
                                       UserDao userDao,
                                       JwtUtils jwtUtils,
                                       PasswordEncoder encoder,
                                       RoleService roleService) {
        this.authenticationManager = authenticationManager;
        this.userDao = userDao;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
        this.roleService = roleService;
    }


    public JwtResponse logIn(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new JwtResponse(jwt,
                userDetails.getId());
    }

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
