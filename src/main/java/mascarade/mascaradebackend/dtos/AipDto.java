package mascarade.mascaradebackend.dtos;

import lombok.Builder;
import mascarade.mascaradebackend.entities.Aip;
import mascarade.mascaradebackend.enums.Challenge;

import java.util.List;

@Builder
public record AipDto(
        String characterId,
        List<Challenge> challenges,
        List<VampireActionDto> vampireActions,
        List<TargetedActionDto> targetedActions,
        List<GlobalActionDto> globalActions,
        List<IndirectAttackActionDto> indirectAttackActions,
        List<HuntDto> hunts,
        List<ExpenditureDto> expenditures,
        String response,
        boolean isOpen,
        boolean isClosed,
        boolean isRendered
) {
    public static AipDto fromEntity(Aip aip) {
        return AipDto.builder()
                .characterId(aip.characterId())
                .challenges(aip.challenges())
                .vampireActions(aip.vampireActions().stream().map(VampireActionDto::fromEntity).toList())
                .targetedActions(aip.targetedActions().stream().map(TargetedActionDto::fromEntity).toList())
                .globalActions(aip.globalActions().stream().map(GlobalActionDto::fromEntity).toList())
                .indirectAttackActions(aip.indirectAttackActions().stream().map(IndirectAttackActionDto::fromEntity).toList())
                .hunts(aip.hunts().stream().map(HuntDto::fromEntity).toList())
                .expenditures(aip.expenditures().stream().map(ExpenditureDto::fromEntity).toList())
                .response(aip.response())
                .isOpen(aip.isOpen())
                .isClosed(aip.isClosed())
                .isRendered(aip.isRendered())
                .build();
    }
}
