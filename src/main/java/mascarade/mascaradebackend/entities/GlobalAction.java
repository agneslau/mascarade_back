package mascarade.mascaradebackend.entities;

import lombok.Builder;
import mascarade.mascaradebackend.dtos.GlobalActionDto;
import mascarade.mascaradebackend.enums.GlobalActionType;
import mascarade.mascaradebackend.enums.Specialty;

@Builder
public record GlobalAction (
        String influenceId,
        String description,
        String response,
        Specialty specialty,
        int points,
        GlobalActionType type,
        boolean isSpecialtyRespected){

    public static GlobalAction fromDto(GlobalActionDto globalActionDto) {
        return GlobalAction.builder()
                .influenceId(globalActionDto.influenceId())
                .description(globalActionDto.description())
                .response(globalActionDto.response())
                .specialty(globalActionDto.specialty())
                .points(globalActionDto.points())
                .type(globalActionDto.type())
                .isSpecialtyRespected(globalActionDto.isSpecialtyRespected())
                .build();
    }
}
