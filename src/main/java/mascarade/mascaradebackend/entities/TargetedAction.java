package mascarade.mascaradebackend.entities;

import lombok.Builder;
import mascarade.mascaradebackend.dtos.TargetedActionDto;
import mascarade.mascaradebackend.enums.TargetedActionType;

@Builder
public record TargetedAction(
        String response,
        int points,
        String influenceId,
        String targetName,
        TargetedActionType type

) {

    public static TargetedAction fromDto(TargetedActionDto dto) {
        return TargetedAction.builder()
                .response(dto.response())
                .points(dto.points())
                .influenceId(dto.influenceId())
                .targetName(dto.targetName())
                .type(dto.type())
                .build();
    }
}
