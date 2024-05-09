package mascarade.mascaradebackend.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mascarade.mascaradebackend.dtos.AipDto;
import mascarade.mascaradebackend.dtos.AipSessionDto;
import mascarade.mascaradebackend.services.impl.AipServiceImpl;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/aips")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class AipController {
    private final AipServiceImpl aipService;

    @GetMapping("/sessions")
    @PreAuthorize("hasAnyRole('ROLE_STORY_TELLER')")
    public ResponseEntity<List<AipSessionDto>> getAipSessions(){
        log.info("Get all AIP sessions");
        return ResponseEntity.ok(aipService.findAllAipSessions());
    }

    @PostMapping("/sessions")
    @PreAuthorize("hasAnyRole('ROLE_STORY_TELLER')")
    public ResponseEntity<AipSessionDto> createAipSession(@RequestBody AipSessionDto aipSessionDto){
        log.info("Create AIP session");
        return ResponseEntity.ok(aipService.addAipSession(aipSessionDto));
    }

    @DeleteMapping("/sessions/{id}")
    @PreAuthorize("hasAnyRole('ROLE_STORY_TELLER')")
    public ResponseEntity<Void> deleteAipSession(@PathVariable String id){
        log.info("Delete AIP session, id: {}", id);
        aipService.deleteAipSession(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/sessions")
    @PreAuthorize("hasAnyRole('ROLE_STORY_TELLER')")
    public ResponseEntity<AipSessionDto> updateAipSession(@RequestBody AipSessionDto aipSessionDto){
        log.info("Update AIP session, id: {}", aipSessionDto.id());
        return ResponseEntity.ok(aipService.updateAipSession(aipSessionDto));
    }

    @GetMapping("/sessions/opened/character/{characterId}")
    @PreAuthorize("hasAnyRole('ROLE_STORY_TELLER', 'ROLE_PLAYER')")
    public ResponseEntity<List<AipSessionDto>> getOpenedAipSessions(@PathVariable String characterId){
        log.info("Get all opened AIP sessions fo character: {}", characterId);
        return ResponseEntity.ok(aipService.findOpenedAipSessionsByCharacterId(characterId));
    }

    @GetMapping("/sessions/{sessionId}/character/{characterId}")
    @PreAuthorize("hasAnyRole('ROLE_STORY_TELLER', 'ROLE_PLAYER')")
    public ResponseEntity<AipDto> getAip(@PathVariable String sessionId, @PathVariable String characterId){
        log.info("Get AIP for session: {} and character: {}", sessionId, characterId);
        return ResponseEntity.ok(aipService.findAip(sessionId, characterId));
    }

    @PostMapping("/sessions/{sessionId}")
    @PreAuthorize("hasAnyRole('ROLE_STORY_TELLER', 'ROLE_PLAYER')")
    public ResponseEntity<AipDto> createAip(@PathVariable String sessionId, @RequestBody AipDto aipDto){
        log.info("Create AIP for session: {} and character: {}", sessionId, aipDto.characterId());
        return ResponseEntity.ok(aipService.addAip(aipDto, sessionId));
    }

}
