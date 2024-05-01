package mascarade.mascaradebackend.repositories;

import mascarade.mascaradebackend.entities.AipSession;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AipSessionRepository extends MongoRepository<AipSession, ObjectId> {
}
