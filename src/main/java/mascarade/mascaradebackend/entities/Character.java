package mascarade.mascaradebackend.entities;

import lombok.Builder;
import mascarade.mascaradebackend.dtos.CharacterDto;
import mascarade.mascaradebackend.enums.Asset;
import mascarade.mascaradebackend.enums.Clan;
import mascarade.mascaradebackend.enums.Competence;
import mascarade.mascaradebackend.enums.Ritual;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Document(collection = "Character")
@Builder
public record Character(
        @MongoId
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
    public static Character fromCharacterDto(CharacterDto characterDto){
        return Character.builder()
                .name(characterDto.name())
                .creationDate(characterDto.creationDate())
                .clan(characterDto.clan())
                .competences(characterDto.competences())
                .assets(characterDto.assets())
                .herd(Objects.isNull(characterDto.herd()) ? Herd.createDefaultHerd() : Herd.fromHerdDto(characterDto.herd()))
                .rituals(characterDto.rituals())
                .playerId(characterDto.playerId())
                .build();
    }
}
