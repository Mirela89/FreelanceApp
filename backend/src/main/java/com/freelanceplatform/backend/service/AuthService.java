package com.freelanceplatform.backend.service;

import com.freelanceplatform.backend.dto.request.LoginRequest;
import com.freelanceplatform.backend.dto.request.RegisterRequest;
import com.freelanceplatform.backend.dto.response.AuthResponse;
import com.freelanceplatform.backend.entity.Client;
import com.freelanceplatform.backend.entity.Freelancer;
import com.freelanceplatform.backend.entity.User;
import com.freelanceplatform.backend.exception.BusinessException;
import com.freelanceplatform.backend.exception.ResourceNotFoundException;
import com.freelanceplatform.backend.mapper.UserMapper;
import com.freelanceplatform.backend.repository.ClientRepository;
import com.freelanceplatform.backend.repository.FreelancerRepository;
import com.freelanceplatform.backend.repository.UserRepository;
import com.freelanceplatform.backend.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final FreelancerRepository freelancerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    @Transactional
    public User register(RegisterRequest request) {

        // Check if email is already in use
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already in use");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        if ("FREELANCER".equals(request.getRole())) {
            Freelancer freelancer = userMapper.toFreelancer(request, encodedPassword);
            return freelancerRepository.save(freelancer);
        } else {
            Client client = userMapper.toClient(request, encodedPassword);
            return clientRepository.save(client);
        }
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String token = jwtTokenProvider.generateToken(user.getEmail());
        String role = (user instanceof Freelancer) ? "FREELANCER" : "CLIENT";

        return new AuthResponse(token, user.getEmail(), role);
    }
}
