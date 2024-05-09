package mascarade.mascaradebackend.dtos;

import lombok.Builder;
import mascarade.mascaradebackend.entities.GlobalAction;
import mascarade.mascaradebackend.enums.GlobalActionType;
import mascarade.mascaradebackend.enums.Specialty;

@Builder
public record GlobalActionDto(
        String influenceId,
        String description,
        String response,
        Specialty specialty,
        int points,
        GlobalActionType type,
        boolean isSpecialtyRespected
) {
    public static GlobalActionDto fromEntity(GlobalAction glocalAction) {
        return GlobalActionDto.builder()
                .influenceId(glocalAction.influenceId())
                .description(glocalAction.description())
                .response(glocalAction.response())
                .specialty(glocalAction.specialty())
                .points(glocalAction.points())
                .type(glocalAction.type())
                .isSpecialtyRespected(glocalAction.isSpecialtyRespected())
                .build();
    }
}
