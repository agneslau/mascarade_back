package mascarade.mascaradebackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@CrossOrigin
public class TestController {

    @GetMapping("/all")
    public ResponseEntity<String> all(){
        return ResponseEntity.ok("Hello World");
    }

    @PostMapping("/all")
    public ResponseEntity<String> allPost(@RequestBody String body){
        return ResponseEntity.ok(body);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<String> admin(){
        return ResponseEntity.ok("Hello Admin");
    }

    @GetMapping("/storyteller")
    @PreAuthorize("hasAnyRole('ROLE_STORY_TELLER')")
    public ResponseEntity<String> storyteller(){
        return ResponseEntity.ok("Hello Story Teller");
    }

    @GetMapping("/player")
    @PreAuthorize("hasAnyRole('ROLE_PLAYER')")
    public ResponseEntity<String> player(){
        return ResponseEntity.ok("Hello Player");
    }

}
