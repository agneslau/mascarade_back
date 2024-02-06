package mascarade.mascaradebackend.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import mascarade.mascaradebackend.entities.User;
import mascarade.mascaradebackend.security.Role;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
public record UserDto(
        String email,
        String name,
        @Enumerated(EnumType.STRING)
        Role role,

        String password
) {

    public static UserDto fromUser(User user) {
        return UserDto.builder()
                .email(user.email())
                .name(user.name())
                .role(user.role())
                .password(user.password())
                .build();
    }
}
