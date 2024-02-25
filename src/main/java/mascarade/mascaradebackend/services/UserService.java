package mascarade.mascaradebackend.services;

import mascarade.mascaradebackend.dtos.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<UserDto> findAll();
    UserDto insertUser(UserDto userDto);
    UserDto updateUser(UserDto userDto);
}
