package mascarade.mascaradebackend.repositories;

import mascarade.mascaradebackend.entities.Character;
import mascarade.mascaradebackend.enums.Clan;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CharacterRepository extends MongoRepository<Character, ObjectId> {
    Optional<Character> findByName(String name);

    Optional<Character> findByNameAndClan(String name, Clan clan);
}
