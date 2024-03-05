package mascarade.mascaradebackend.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mascarade.mascaradebackend.dtos.AuthenticationRequest;
import mascarade.mascaradebackend.dtos.AuthenticationResponse;
import mascarade.mascaradebackend.dtos.RegisterRequest;
import mascarade.mascaradebackend.entities.User;
import mascarade.mascaradebackend.repositories.UserRepository;
import mascarade.mascaradebackend.security.Jwt.JwtService;
import mascarade.mascaradebackend.services.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(registerRequest.getRoles())
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        log.info("User registered: {} with token : {}", user.id(), jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest registerRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.getEmail(),
                        registerRequest.getPassword())
        );
        var user = repository.findByEmail(registerRequest.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .name(user.name())
                .email(user.email())
                .roles(user.roles())
                .token(jwtToken)
                .build();

    }
}
