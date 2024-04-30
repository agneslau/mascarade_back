package mascarade.mascaradebackend.entities;

import lombok.Builder;
import mascarade.mascaradebackend.dtos.InfluenceDto;
import mascarade.mascaradebackend.enums.Category;
import mascarade.mascaradebackend.enums.District;
import mascarade.mascaradebackend.enums.Specialty;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Builder
public record Influence (
        @MongoId
        ObjectId id,
        String name,
        Category category,
        Specialty specialty,
        int level,
        District district,
        ObjectId characterId

) {
    public static Influence fromInfluenceDto(InfluenceDto influenceDto) {
        return Influence.builder()
                .name(influenceDto.name())
                .category(influenceDto.category())
                .specialty(influenceDto.specialty())
                .level(influenceDto.level())
                .district(influenceDto.district())
                .characterId(new ObjectId(influenceDto.characterId()))
                .build();
    }
}
