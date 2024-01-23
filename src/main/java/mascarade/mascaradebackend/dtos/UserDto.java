package mascarade.mascaradebackend.dtos;

import lombok.Builder;
import mascarade.mascaradebackend.entities.User;
import mascarade.mascaradebackend.security.Role;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
public record UserDto(
        String email,
        String name,
        Role role) {

    public static UserDto fromUser(User user) {
        return UserDto.builder()
                .email(user.email())
                .name(user.name())
                .role(user.role())
                .build();
    }
}
