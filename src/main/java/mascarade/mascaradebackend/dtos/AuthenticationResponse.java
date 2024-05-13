package mascarade.mascaradebackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mascarade.mascaradebackend.security.Role;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String name;
    private String email;
    private String accessToken;
    private String refreshToken;
    private List<Role> roles;
}
