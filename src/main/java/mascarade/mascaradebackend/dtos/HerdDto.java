package mascarade.mascaradebackend.dtos;

import lombok.Builder;

@Builder
public record HerdDto(
        String name,
        String description,
        int level) {
    public static HerdDto fromHerd(mascarade.mascaradebackend.entities.Herd herd) {
        return HerdDto.builder()
                .name(herd.name())
                .description(herd.description())
                .level(herd.level())
                .build();
    }
}
