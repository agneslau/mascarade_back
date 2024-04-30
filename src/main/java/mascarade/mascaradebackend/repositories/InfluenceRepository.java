package mascarade.mascaradebackend.repositories;

import mascarade.mascaradebackend.entities.Influence;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface InfluenceRepository extends MongoRepository<Influence, ObjectId> {
    Optional<List<Influence>> findByCharacterId(ObjectId characterId);
    Optional<List<Influence>> insert(List<Influence> influences);
}
