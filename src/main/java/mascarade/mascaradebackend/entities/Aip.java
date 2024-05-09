package mascarade.mascaradebackend.entities;

import lombok.Builder;
import mascarade.mascaradebackend.dtos.AipDto;
import mascarade.mascaradebackend.enums.Challenge;

import java.util.List;

@Builder
public record Aip(
        String characterId,
        List<Challenge> challenges,
        List<VampireAction> vampireActions,
        List<TargetedAction> targetedActions,
        List<GlobalAction> globalActions,
        List<IndirectAttackAction> indirectAttackActions,
        List<Hunt> hunts,
        List<Expenditure> expenditures,
        String response,
        boolean isOpen,
        boolean isClosed,
        boolean isRendered

) {
    public static Aip fromDto(AipDto aipDto) {
        return Aip.builder()
                .characterId(aipDto.characterId())
                .challenges(aipDto.challenges())
                .vampireActions(aipDto.vampireActions().stream().map(VampireAction::fromDto).toList())
                .targetedActions(aipDto.targetedActions().stream().map(TargetedAction::fromDto).toList())
                .globalActions(aipDto.globalActions().stream().map(GlobalAction::fromDto).toList())
                .indirectAttackActions(aipDto.indirectAttackActions().stream().map(IndirectAttackAction::fromDto).toList())
                .hunts(aipDto.hunts().stream().map(Hunt::fromDto).toList())
                .expenditures(aipDto.expenditures().stream().map(Expenditure::fromDto).toList())
                .response(aipDto.response())
                .isOpen(aipDto.isOpen())
                .isClosed(aipDto.isClosed())
                .isRendered(aipDto.isRendered())
                .build();
    }

}
