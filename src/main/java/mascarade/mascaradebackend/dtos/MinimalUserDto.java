package mascarade.mascaradebackend.dtos;

import lombok.Builder;

@Builder
public record MinimalUserDto(
        String id,
        String name
) {
    public static MinimalUserDto fromUser(mascarade.mascaradebackend.entities.User user) {
        return MinimalUserDto.builder()
                .id(user.id().toString())
                .name(user.name())
                .build();
    }
}
