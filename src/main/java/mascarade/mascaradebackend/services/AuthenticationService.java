package mascarade.mascaradebackend.services;

import mascarade.mascaradebackend.dtos.AuthenticationRequest;
import mascarade.mascaradebackend.dtos.AuthenticationResponse;
import mascarade.mascaradebackend.dtos.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest registerRequest);

    AuthenticationResponse authenticate(AuthenticationRequest registerRequest);
}
