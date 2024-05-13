package mascarade.mascaradebackend.dtos;

import lombok.Builder;

@Builder
public record MinimalUserDto(
        String id,
        String name,
        String email
) {
    public static MinimalUserDto fromUser(mascarade.mascaradebackend.entities.User user) {
        return MinimalUserDto.builder()
                .id(user.id().toString())
                .name(user.name())
                .email(user.email())
                .build();
    }
}
