package mascarade.mascaradebackend.services.impl;

import lombok.AllArgsConstructor;
import mascarade.mascaradebackend.dtos.UserDto;
import mascarade.mascaradebackend.entities.User;
import mascarade.mascaradebackend.repositories.UserRepository;
import mascarade.mascaradebackend.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserDto::fromUser)
                .toList();
    }

    @Override
    public UserDto insertUser(UserDto userDto) {
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
        Optional<User> userOpt = userRepository.findByEmail(userDto.email());
        if(userOpt.isPresent()) {
            User user = this.updateUserFromDto(userOpt.get(), userDto);
            return UserDto.fromUser(userRepository.save(user));
        }
        throw new RuntimeException("User not found");
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
