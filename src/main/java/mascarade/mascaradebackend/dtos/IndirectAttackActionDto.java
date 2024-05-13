package mascarade.mascaradebackend.dtos;

import lombok.Builder;

@Builder
public record IndirectAttackActionDto(
        String influenceId,
        int points,
        String targetCharacterName,
        String targetCharacterId,
        String description,
        String response
) {

    public static IndirectAttackActionDto fromEntity(mascarade.mascaradebackend.entities.IndirectAttackAction entity) {
        return IndirectAttackActionDto.builder()
                .influenceId(entity.influenceId())
                .points(entity.points())
                .targetCharacterName(entity.targetCharacterName())
                .targetCharacterId(entity.targetCharacterId())
                .description(entity.description())
                .response(entity.response())
                .build();
    }
}
