package mascarade.mascaradebackend.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import mascarade.mascaradebackend.entities.User;
import mascarade.mascaradebackend.security.Role;

import java.util.List;

@Builder
public record UserDto(
        String email,
        String name,

        List<Role> roles,

        String password
) {

    public static UserDto fromUser(User user) {
        return UserDto.builder()
                .email(user.email())
                .name(user.name())
                .roles(user.roles())
                .password(user.password())
                .build();
    }
}
