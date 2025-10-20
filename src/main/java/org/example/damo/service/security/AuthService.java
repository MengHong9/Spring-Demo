package org.example.damo.service.security;

import org.example.damo.dto.user.UserDto;
import org.example.damo.entity.User;
import org.example.damo.exception.model.DuplicateResourceException;
import org.example.damo.mapper.UserMapper;
import org.example.damo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(UserDto payload) {
        if (userRepository.existsByName(payload.getName())){
            throw new DuplicateResourceException("user already exists with name : " + payload.getName());
        }


        if(userRepository.existsByEmail(payload.getEmail())){
            throw new DuplicateResourceException("user already exists with email : " + payload.getEmail());
        }

        User user = mapper.toEntity(payload);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User createdUser = userRepository.save(user);
        String token = jwtUtil.generateToken(createdUser);

        return token;
    }
}
