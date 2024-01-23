package mascarade.mascaradebackend.services.impl;

import mascarade.mascaradebackend.dtos.UserDto;
import mascarade.mascaradebackend.entities.User;
import mascarade.mascaradebackend.repositories.UserRepository;
import mascarade.mascaradebackend.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserDto::fromUser)
                .toList();
    }

    @Override
    public UserDto insertUser(UserDto userDto) {
        User user = User.fromUserDto(userDto);
        return UserDto.fromUser(userRepository.insert(user));
    }
}
