package mascarade.mascaradebackend.dtos;

import lombok.Builder;
import mascarade.mascaradebackend.entities.Hunt;
import mascarade.mascaradebackend.enums.HuntType;

@Builder
public record HuntDto(
        HuntType type,
        String description
) {
    public static HuntDto fromEntity(Hunt hunt) {
        return HuntDto.builder()
                .type(hunt.type())
                .description(hunt.description())
                .build();
    }
}
