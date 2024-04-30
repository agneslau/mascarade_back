package mascarade.mascaradebackend.repositories;

import mascarade.mascaradebackend.entities.Character;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArchivedCharacterRepository extends MongoRepository<Character, String> {
}
