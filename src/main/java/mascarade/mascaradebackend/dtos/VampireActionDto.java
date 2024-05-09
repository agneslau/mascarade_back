package mascarade.mascaradebackend.dtos;

import lombok.Builder;
import mascarade.mascaradebackend.entities.VampireAction;

@Builder
public record VampireActionDto(
        String title,
        String description,
        boolean isEffective,
        String response,
        String notes) {

    public static VampireActionDto fromEntity(VampireAction entity) {
        return VampireActionDto.builder()
                .title(entity.title())
                .description(entity.description())
                .isEffective(entity.isEffective())
                .response(entity.response())
                .notes(entity.notes())
                .build();
    }

}
