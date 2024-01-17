package com.adllo.springTOTPxml.service;


import com.adllo.TOTP;
import com.adllo.springTOTPxml.dto.UserDTO;
import com.adllo.springTOTPxml.enums.Role;
import com.adllo.springTOTPxml.model.User;
import com.adllo.springTOTPxml.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    /**
     * Creacion de usuario desde DTO de {@link User}
     *
     * @param user Datos recogido del formulario de la clase {@link UserDTO}
     * @return {@link User}
     */
    public User createUser(UserDTO user) {
        var us = findByEmail(user.getEmail());

        if (us == null) {
            us = new User();

            us.setEmail(user.getEmail());
            us.setPassword(passwordEncoder.encode(user.getPassword()));
            us.setAuthority(Set.of(Role.USER));
            us.setSecret(TOTP.generateBase32Secret());

            userRepository.save(us);
        }

        return us;
    }

    /**
     * Adicion de rol a {@link User}
     *
     * @param user Registro completo del usuario al que se le quiera añadir un rol
     * @param role Rol especifico para añadir al usuario
     */
    public void addAuthority(User user, Role role) {
        Set<Role> roles = new HashSet<>(user.getAuthority());
        roles.add(role);

        user.setAuthority(roles);

        userRepository.save(user);
    }

}
