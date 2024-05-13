package mascarade.mascaradebackend.dtos;

import lombok.Builder;
import mascarade.mascaradebackend.entities.TargetedAction;
import mascarade.mascaradebackend.enums.TargetedActionType;

@Builder
public record TargetedActionDto (
        String response,
        int points,
        String influenceId,
        String targetName,
        TargetedActionType type
){

    public static TargetedActionDto fromEntity(TargetedAction targetedAction) {
        return TargetedActionDto.builder()
                .response(targetedAction.response())
                .points(targetedAction.points())
                .influenceId(targetedAction.influenceId())
                .targetName(targetedAction.targetName())
                .type(targetedAction.type())
                .build();
    }
}
