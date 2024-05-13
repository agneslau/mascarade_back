package mascarade.mascaradebackend.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mascarade.mascaradebackend.dtos.InfluenceDto;
import mascarade.mascaradebackend.services.impl.InfluenceServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/influences")
@CrossOrigin
@Slf4j
@AllArgsConstructor
public class InfluenceController {

    private final InfluenceServiceImpl influenceService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_STORY_TELLER')")
    public ResponseEntity<List<InfluenceDto>> getAllInfluences(){
        log.info("Get all influences");
        return ResponseEntity.ok(influenceService.findAll());
    }

    @GetMapping("/character/{characterId}")
    @PreAuthorize("hasAnyRole('ROLE_STORY_TELLER', 'ROLE_PLAYER')")
    public ResponseEntity<List<InfluenceDto>> getInfluencesByCharacterId(@PathVariable String characterId){
        log.info("Get all influences by character id: {}", characterId);
        return ResponseEntity.ok(influenceService.findByCharacterId(characterId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_STORY_TELLER')")
    public ResponseEntity<List<InfluenceDto>> addInfluences(@RequestBody List<InfluenceDto> influences){
        log.info("Add influences: {}", influences);
        return ResponseEntity.ok(influenceService.addInfluences(influences));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_STORY_TELLER')")
    public ResponseEntity<List<InfluenceDto>> editInfluences(@RequestBody List<InfluenceDto> influences){
        log.info("Edit influences: {}", influences);
        return ResponseEntity.ok(influenceService.editInfluences(influences));
    }
}
