package mascarade.mascaradebackend.dtos;

import lombok.Builder;
import mascarade.mascaradebackend.enums.Category;
import mascarade.mascaradebackend.enums.District;
import mascarade.mascaradebackend.enums.Specialty;

@Builder
public record InfluenceDto(
        String id,
        String name,
        Category category,
        Specialty specialty,
        int level,
        District district,
        String characterId
){
    public static InfluenceDto fromInfluence(mascarade.mascaradebackend.entities.Influence influence) {
        return InfluenceDto.builder()
                .id(influence.id().toString())
                .name(influence.name())
                .category(influence.category())
                .specialty(influence.specialty())
                .level(influence.level())
                .district(influence.district())
                .characterId(influence.characterId().toString())
                .build();
    }
}
