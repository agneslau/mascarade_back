package mascarade.mascaradebackend.services.impl;

import lombok.AllArgsConstructor;
import mascarade.mascaradebackend.dtos.UserDto;
import mascarade.mascaradebackend.entities.DeletedUser;
import mascarade.mascaradebackend.entities.User;
import mascarade.mascaradebackend.repositories.DeletedUserRepository;
import mascarade.mascaradebackend.repositories.UserRepository;
import mascarade.mascaradebackend.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DeletedUserRepository deletedUserRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserDto::fromUser)
                .toList();
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        Optional<User> userOpt = userRepository.findByEmail(userDto.email());
        if(userOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists in database");
        }
        Optional <User> userOpt2 = userRepository.findByName(userDto.name());
        if(userOpt2.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name already exists in database");
        }
        var user = User.builder()
                .name(userDto.name())
                .email(userDto.email())
                .password(passwordEncoder.encode(userDto.password()))
                .roles(userDto.roles())
                .build();
        return UserDto.fromUser(userRepository.insert(user));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        Optional<User> userOpt = userRepository.findById(new ObjectId(userDto.id()));
        if(userOpt.isPresent()) {
            User user = this.updateUserFromDto(userOpt.get(), userDto);
            return UserDto.fromUser(userRepository.save(user));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    @Override
    public Boolean isNameTaken(String name) {
        Optional<User> userOpt = userRepository.findByName(name);
        return userOpt.isPresent();
    }

    @Override
    public Boolean isEmailTaken(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        return userOpt.isPresent();
    }

    @Override
    public void deleteUser(String id) {
        Optional<User> userOpt = userRepository.findById(new ObjectId(id));
        if(userOpt.isPresent()) {
            deletedUserRepository.insert(DeletedUser.fromUser(userOpt.get()));
            userRepository.delete(userOpt.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    private User updateUserFromDto(User user, UserDto userDto) {
        return User.builder()
                .id(user.id())
                .email(user.email())
                .name(userDto.name())
                .roles(userDto.roles())
                .password(user.password())
                .build();
    }
}
