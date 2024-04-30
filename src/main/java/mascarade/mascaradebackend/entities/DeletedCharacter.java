package mascarade.mascaradebackend.entities;

import mascarade.mascaradebackend.enums.Asset;
import mascarade.mascaradebackend.enums.Clan;
import mascarade.mascaradebackend.enums.Competence;
import mascarade.mascaradebackend.enums.Ritual;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record DeletedCharacter(
        ObjectId id,
        String name,
        LocalDate creationDate,
        Clan clan,
        Map<Competence, Integer> competences,
        List<Asset> assets,
        Herd herd,
        List<Ritual> rituals,
        String playerId
) {
    public static DeletedCharacter fromCharacter(Character character) {
        return new DeletedCharacter(character.id(), character.name(), character.creationDate(), character.clan(), character.competences(), character.assets(), character.herd(), character.rituals(), character.playerId());
    }
}
