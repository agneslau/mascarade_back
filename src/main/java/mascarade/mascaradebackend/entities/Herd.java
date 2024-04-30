package mascarade.mascaradebackend.entities;

import lombok.Builder;
import mascarade.mascaradebackend.dtos.HerdDto;

@Builder
public record Herd(
        String name,
        String description,
        int level
) {
    public static Herd createDefaultHerd() {
        return Herd.builder()
                .level(0)
                .build();
    }

    public static Herd fromHerdDto(HerdDto herdDto) {
        return Herd.builder()
                .name(herdDto.name())
                .description(herdDto.description())
                .level(herdDto.level())
                .build();
    }
}
