package mascarade.mascaradebackend.dtos;

import lombok.Builder;
import mascarade.mascaradebackend.entities.User;
import mascarade.mascaradebackend.security.Role;

import java.util.List;

@Builder
public record UserDto(

        String id,
        String email,
        String name,
        List<Role> roles,
        String password,
        List<String> charactersIds
) {

    public static UserDto fromUser(User user) {
        return UserDto.builder()
                .id(user.id().toString())
                .email(user.email())
                .name(user.name())
                .roles(user.roles())
                .password(user.password())
                .build();
    }

    public static UserDto minimalFromUser(User user) {
        return UserDto.builder()
                .id(user.id().toString())
                .name(user.name())
                .build();
    }
}
