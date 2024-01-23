package mascarade.mascaradebackend.services;

import mascarade.mascaradebackend.dtos.UserDto;
import mascarade.mascaradebackend.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {

    public List<UserDto> findAll();
    public UserDto insertUser(UserDto userDto);
}
