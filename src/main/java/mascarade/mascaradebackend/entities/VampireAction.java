package mascarade.mascaradebackend.entities;

import lombok.Builder;
import mascarade.mascaradebackend.dtos.VampireActionDto;

@Builder
public record VampireAction(
        String title,
        String description,
        boolean isEffective,
        String response,
        String notes

) {

    public static VampireAction fromDto(VampireActionDto dto) {
        return VampireAction.builder()
                .title(dto.title())
                .description(dto.description())
                .isEffective(dto.isEffective())
                .response(dto.response())
                .notes(dto.notes())
                .build();
    }
}
