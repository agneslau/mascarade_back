package mascarade.mascaradebackend.repositories;

import mascarade.mascaradebackend.entities.DeletedUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeletedUserRepository extends MongoRepository<DeletedUser, String>{
}
