package mascarade.mascaradebackend.controllers;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mascarade.mascaradebackend.dtos.MinimalUserDto;
import mascarade.mascaradebackend.dtos.UserDto;
import mascarade.mascaradebackend.security.Role;
import mascarade.mascaradebackend.services.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> getUsers(){
        log.info("Get all users");
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("minimal")
    @PreAuthorize("hasAnyRole('ROLE_STORY_TELLER')")
    public ResponseEntity<List<MinimalUserDto>> getMinimalUsers(){
        log.info("Get all minimal users");
        return ResponseEntity.ok(userService.findMinimalUsers());
    }

    @GetMapping("minimal/email/{email}")
    @PreAuthorize("hasAnyRole('ROLE_PLAYER')")
    public ResponseEntity<MinimalUserDto> getMinimalUserByEmail(@PathVariable String email){
        log.info("Get minimal user matching email: {}", email);
        return ResponseEntity.ok(userService.findMinimalUserByEmail(email));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        log.info("Create user: {}", userDto);
        return ResponseEntity.ok(userService.addUser(userDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id){
        log.info("Delete user with id: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @RequestBody UserDto userDto){
        log.info("Update user with id: {}", id);
        if(!id.equals(userDto.id())) {
            log.error("Id in path and body do not match");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id in path and body do not match");
        }
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> isNameTaken(@PathVariable String name){
        log.info("Check if name is taken: {}", name);
        return ResponseEntity.ok(userService.isNameTaken(name));
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> isEmailTaken(@PathVariable String email){
        log.info("Check if email is taken: {}", email);
        return ResponseEntity.ok(userService.isEmailTaken(email));
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
