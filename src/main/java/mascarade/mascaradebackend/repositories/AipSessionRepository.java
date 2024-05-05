package mascarade.mascaradebackend.repositories;

import mascarade.mascaradebackend.entities.AipSession;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AipSessionRepository extends MongoRepository<AipSession, ObjectId> {

    List<AipSession> findAllByIsOpen(boolean isOpen);
}
