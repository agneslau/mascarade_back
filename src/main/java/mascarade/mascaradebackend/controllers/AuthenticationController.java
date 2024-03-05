package mascarade.mascaradebackend.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mascarade.mascaradebackend.dtos.AuthenticationRequest;
import mascarade.mascaradebackend.dtos.AuthenticationResponse;
import mascarade.mascaradebackend.dtos.RegisterRequest;
import mascarade.mascaradebackend.services.impl.AuthenticationServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest){
        log.info("Register request: {}", registerRequest);
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest registerRequest){
        log.info("Authenticate request: {}", registerRequest);
        return ResponseEntity.ok(authenticationService.authenticate(registerRequest));
    }
}
