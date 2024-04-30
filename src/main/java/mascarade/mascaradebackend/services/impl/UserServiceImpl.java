package mascarade.mascaradebackend.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mascarade.mascaradebackend.dtos.MinimalUserDto;
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
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND_MESSAGE = "User not found";

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
            log.error("Email already exists in database");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists in database");
        }
        Optional <User> userOpt2 = userRepository.findByName(userDto.name());
        if(userOpt2.isPresent()) {
            log.error("Name already exists in database");
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
        log.error(USER_NOT_FOUND_MESSAGE);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND_MESSAGE);
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
            log.error(USER_NOT_FOUND_MESSAGE);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, USER_NOT_FOUND_MESSAGE);
        }
    }

    @Override
    public List<MinimalUserDto> findMinimalUsers() {
        return userRepository.findAll().stream()
                .map(MinimalUserDto::fromUser)
                .toList();
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
