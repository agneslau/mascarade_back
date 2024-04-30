package mascarade.mascaradebackend.repositories;

import mascarade.mascaradebackend.entities.DeletedCharacter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeletedCharacterRepository extends MongoRepository<DeletedCharacter, String> {
}
