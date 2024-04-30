package mascarade.mascaradebackend.entities;

import mascarade.mascaradebackend.enums.Category;
import mascarade.mascaradebackend.enums.District;
import mascarade.mascaradebackend.enums.Specialty;
import org.bson.types.ObjectId;

public record DeletedInfluence(
        ObjectId id,
        String name,
        Category category,
        Specialty specialty,
        int level,
        District district,
        ObjectId characterId
) {

    public static DeletedInfluence fromInfluence(Influence influence) {
        return new DeletedInfluence(influence.id(), influence.name(), influence.category(), influence.specialty(), influence.level(), influence.district(), influence.characterId());
    }

}
