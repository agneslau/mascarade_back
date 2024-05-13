package mascarade.mascaradebackend.repositories;

import mascarade.mascaradebackend.entities.DeletedInfluence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeletedInfluenceRepository extends MongoRepository<DeletedInfluence, String> {
}
