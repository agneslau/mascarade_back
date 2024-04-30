package mascarade.mascaradebackend.dtos;

import lombok.Builder;
import mascarade.mascaradebackend.entities.Character;
import mascarade.mascaradebackend.enums.Asset;
import mascarade.mascaradebackend.enums.Clan;
import mascarade.mascaradebackend.enums.Competence;
import mascarade.mascaradebackend.enums.Ritual;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Builder
public record CharacterDto(
        String id,
        String name,
        LocalDate creationDate,
        Clan clan,
        Map<Competence, Integer> competences,
        List<Asset> assets,
        HerdDto herd,
        List<Ritual> rituals,
        String playerId
) {
    public static CharacterDto fromCharacter(Character character) {
        return CharacterDto.builder()
                .id(character.id().toString())
                .name(character.name())
                .creationDate(character.creationDate())
                .clan(character.clan())
                .competences(character.competences())
                .assets(character.assets())
                .herd(HerdDto.fromHerd(character.herd()))
                .rituals(character.rituals())
                .playerId(character.playerId())
                .build();
    }


}
