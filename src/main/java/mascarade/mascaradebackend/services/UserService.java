package mascarade.mascaradebackend.services;

import mascarade.mascaradebackend.dtos.MinimalUserDto;
import mascarade.mascaradebackend.dtos.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<UserDto> findAll();
    UserDto addUser(UserDto userDto);
    UserDto updateUser(UserDto userDto);

    Boolean isNameTaken(String name);

    Boolean isEmailTaken(String email);

    void deleteUser(String id);

    List<MinimalUserDto> findMinimalUsers();

    MinimalUserDto findMinimalUserByEmail(String email);
}
