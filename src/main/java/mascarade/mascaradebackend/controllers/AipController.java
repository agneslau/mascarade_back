package mascarade.mascaradebackend.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mascarade.mascaradebackend.dtos.AipSessionDto;
import mascarade.mascaradebackend.services.impl.AipServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    @PreAuthorize("hasRole('STORY_TELLER')")
    public ResponseEntity<List<AipSessionDto>> getAipSessions(){
        log.info("Get all AIP sessions");
        return ResponseEntity.ok(aipService.findAllAipSessions());
    }

    @PostMapping("/sessions")
    @PreAuthorize("hasRole('STORY_TELLER')")
    public ResponseEntity<AipSessionDto> createAipSession(@RequestBody AipSessionDto aipSessionDto){
        log.info("Create AIP session");
        return ResponseEntity.ok(aipService.addAipSession(aipSessionDto));
    }
    @DeleteMapping("/sessions/{id}")
    @PreAuthorize("hasRole('STORY_TELLER')")
    public ResponseEntity<Void> deleteAipSession(@PathVariable String id){
        log.info("Delete AIP session");
        aipService.deleteAipSession(id);
        return ResponseEntity.ok().build();
    }

}
