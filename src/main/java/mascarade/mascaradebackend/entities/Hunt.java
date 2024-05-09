package mascarade.mascaradebackend.entities;

import lombok.Builder;
import mascarade.mascaradebackend.dtos.HuntDto;
import mascarade.mascaradebackend.enums.HuntType;

@Builder
public record Hunt(
        HuntType type,
        String description
) {
    public static Hunt fromDto(HuntDto huntDto) {
        return Hunt.builder()
                .type(huntDto.type())
                .description(huntDto.description())
                .build();
    }
}
