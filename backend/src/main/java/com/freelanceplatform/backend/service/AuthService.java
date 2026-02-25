package com.freelanceplatform.backend.service;

import com.freelanceplatform.backend.dto.request.LoginRequest;
import com.freelanceplatform.backend.dto.request.RegisterRequest;
import com.freelanceplatform.backend.dto.response.AuthResponse;
import com.freelanceplatform.backend.entity.Client;
import com.freelanceplatform.backend.entity.Freelancer;
import com.freelanceplatform.backend.entity.User;
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

    @Transactional
    public User register(RegisterRequest request) {

        // Check if email is already in use
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        // Create user based on role
        if ("FREELANCER".equals(request.getRole())) {
            Freelancer freelancer = new Freelancer();
            freelancer.setName(request.getName());
            freelancer.setEmail(request.getEmail());
            freelancer.setPasswordHash(passwordEncoder.encode(request.getPassword()));
            freelancer.setCreatedDate(LocalDate.now());
            freelancer.setProfileTitle(request.getProfileTitle());
            freelancer.setAvailability("AVAILABLE");
            freelancer.setRating(BigDecimal.ZERO);
            return freelancerRepository.save(freelancer);

        } else { // Default to client if not freelancer
            Client client = new Client();
            client.setName(request.getName());
            client.setEmail(request.getEmail());
            client.setPasswordHash(passwordEncoder.encode(request.getPassword()));
            client.setCreatedDate(LocalDate.now());
            client.setClientType(request.getClientType());
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
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtTokenProvider.generateToken(user.getEmail());

        String role = (user instanceof Freelancer) ? "FREELANCER" : "CLIENT";

        return new AuthResponse(token, user.getEmail(), role);
    }
}
