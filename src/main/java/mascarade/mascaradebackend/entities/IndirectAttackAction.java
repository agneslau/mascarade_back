package mascarade.mascaradebackend.entities;

import lombok.Builder;
import mascarade.mascaradebackend.dtos.IndirectAttackActionDto;

@Builder
public record IndirectAttackAction(
        String influenceId,
        int points,
        String targetCharacterName,
        String targetCharacterId,
        String description,
        String response
) {

    public static IndirectAttackAction fromDto(IndirectAttackActionDto indirectAttackActionDto) {
        return IndirectAttackAction.builder()
                .influenceId(indirectAttackActionDto.influenceId())
                .points(indirectAttackActionDto.points())
                .targetCharacterName(indirectAttackActionDto.targetCharacterName())
                .targetCharacterId(indirectAttackActionDto.targetCharacterId())
                .description(indirectAttackActionDto.description())
                .response(indirectAttackActionDto.response())
                .build();
    }
}
