package mascarade.mascaradebackend.controllers;


import mascarade.mascaradebackend.dtos.UserDto;
import mascarade.mascaradebackend.security.Role;
import mascarade.mascaradebackend.services.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> getUsers(){
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> insertUser(@RequestBody Map<String, String> params){
        return createUserDto(params).map(userDto -> ResponseEntity.ok(userService.insertUser(userDto)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    private Optional<UserDto> createUserDto(Map<String, String> params) {
        if(params.isEmpty() || !params.containsKey("name") || !params.containsKey("email") || !params.containsKey("role")){
            return Optional.empty();
        } else {
            return Optional.of(UserDto.builder()
                    .email(params.get("email"))
                    .name(params.get("name"))
                    .roles(determineRoles(params.get("roles")))
                    .build());
        }
    }

    private Role determineRole(String role) {
        if (role.equals("ROLE_ADMIN")) {
            return Role.ROLE_ADMIN;
        } else if (role.equals("ROLE_PLAYER")) {
            return Role.ROLE_PLAYER;
        } else if (role.equals("ROLE_STORY_TELLER")){
            return Role.ROLE_STORY_TELLER;
        } else {
            throw new IllegalArgumentException("Role not found");
        }
    }

    private List<Role> determineRoles(String roles) {
        return Stream.of(roles.split(","))
                .map(this::determineRole)
                .toList();
    }
}
