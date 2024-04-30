package mascarade.mascaradebackend.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mascarade.mascaradebackend.dtos.CharacterDto;
import mascarade.mascaradebackend.enums.Clan;
import mascarade.mascaradebackend.services.impl.CharacterServiceImpl;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/characters")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class CharacterController {

    private final CharacterServiceImpl characterService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_STORY_TELLER')")
    public ResponseEntity<List<CharacterDto>> getCharacters(){
        log.info("Get all characters");
        return ResponseEntity.ok(characterService.findAll());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_STORY_TELLER')")
    public ResponseEntity<CharacterDto> createCharacter(@RequestBody CharacterDto characterDto){
        log.info("Create character: {}", characterDto);
        return ResponseEntity.ok(characterService.addCharacter(characterDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_STORY_TELLER')")
        public ResponseEntity<Void> archiveCharacter(@PathVariable String id){
        log.info("Archive character with id: {}", id);
        characterService.archiveCharacter(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_STORY_TELLER')")
    public ResponseEntity<CharacterDto> updateCharacter(@PathVariable String id, @RequestBody CharacterDto characterDto){
        log.info("Update character with id: {}", id);
        if(!id.equals(characterDto.id())){
            log.error("Id in path and in body are different");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id in path and body do not match");
        }
        return ResponseEntity.ok(characterService.updateCharacter(characterDto));
    }
    @GetMapping("/isTaken")
    @PreAuthorize("hasAnyRole('ROLE_STORY_TELLER')")
    public ResponseEntity<Boolean> isTaken(@RequestParam String name, @RequestParam Clan clan){
        log.info("Check if pair name {} and clan {} is taken", name, clan );
        return ResponseEntity.ok(characterService.isTaken(name, clan));
    }
}
