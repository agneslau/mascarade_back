package mascarade.mascaradebackend.repositories;

import mascarade.mascaradebackend.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<User, UUID> {

    public Optional<User> findByEmail(String email);
}
