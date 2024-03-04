package mascarade.mascaradebackend.controllers;


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
        return ResponseEntity.ok(userService.addUser(userDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @RequestBody UserDto userDto){
        if(!id.equals(userDto.id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id in path and body do not match");
        }
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> isNameTaken(@PathVariable String name){
        return ResponseEntity.ok(userService.isNameTaken(name));
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> isEmailTaken(@PathVariable String email){
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
