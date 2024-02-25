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

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        //TODO: gérer la duplication d'email
        return ResponseEntity.ok(userService.insertUser(userDto));
    }

    /*@PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody Map<String, String> params){
        return createUserDto(params)
                .map(userDto -> ResponseEntity.ok(userService.updateUser(userDto)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }*/

    private Optional<UserDto> createUserDto(Map<String, String> params) {
        if(params.isEmpty() || !params.containsKey("name") || !params.containsKey("email") || !params.containsKey("role") || !params.containsKey("password")) {
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
        return switch (role) {
            case "ROLE_ADMIN" -> Role.ROLE_ADMIN;
            case "ROLE_PLAYER" -> Role.ROLE_PLAYER;
            case "ROLE_STORY_TELLER" -> Role.ROLE_STORY_TELLER;
            default -> throw new IllegalArgumentException("Role not found");
        };
    }

    private List<Role> determineRoles(String roles) {
        return Stream.of(roles.split(","))
                .map(this::determineRole)
                .toList();
    }
}
