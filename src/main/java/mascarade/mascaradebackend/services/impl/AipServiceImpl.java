package mascarade.mascaradebackend.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mascarade.mascaradebackend.dtos.AipDto;
import mascarade.mascaradebackend.dtos.AipSessionDto;
import mascarade.mascaradebackend.entities.AipSession;
import mascarade.mascaradebackend.repositories.AipSessionRepository;
import mascarade.mascaradebackend.services.AipService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class AipServiceImpl implements AipService {

    private final AipSessionRepository aipSessionRepository;

    @Override
    public List<AipSessionDto> findAllAipSessions() {
        return aipSessionRepository.findAll().stream()
                .map(AipSessionDto::fromEntity).toList();
    }

    @Override
    public AipSessionDto addAipSession(AipSessionDto aipSessionDto) {
        return AipSessionDto.fromEntity(aipSessionRepository.insert(AipSession.fromAipSessionDto(aipSessionDto)));
    }

    @Override
    public AipDto addAip(AipDto aipDto, String sessionId) {
        Optional<AipSession> aipSession = aipSessionRepository.findById(new ObjectId(sessionId));
        if (aipSession.isPresent()) {
            AipSessionDto sessionDto = AipSessionDto.fromEntity(aipSession.get());

            List<AipDto> aipsToSave;

            if(sessionDto.aips().isEmpty()){
                aipsToSave = List.of(aipDto);
            } else {
                aipsToSave = Stream.concat(sessionDto.aips().stream(), Stream.of(aipDto))
                        .toList();
            }

            AipSessionDto aipSessionDtoToSave = AipSessionDto.builder()
                    .id(sessionDto.id())
                    .name(sessionDto.name())
                    .beginDate(sessionDto.beginDate())
                    .endDate(sessionDto.endDate())
                    .isOpen(sessionDto.isOpen())
                    .isClosed(sessionDto.isClosed())
                    .isRendered(sessionDto.isRendered())
                    .aips(aipsToSave)
                    .build();

            return updateAipSession(aipSessionDtoToSave).aips().stream().filter(aip -> aip.characterId().equals(aipDto.characterId()))
                    .findFirst()
                    .orElseThrow(() -> {
                        log.error("Error while updating AipSession, sessionId: {} and characterId : {}", sessionId, aipDto.characterId());
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "AIP not found");
                    });
        }


        return null;
    }

    @Override
    public void deleteAipSession(String id) {
        aipSessionRepository.deleteById(new ObjectId(id));
    }

    @Override
    public AipSessionDto updateAipSession(AipSessionDto aipSessionDto) {
        return AipSessionDto.fromEntity(aipSessionRepository.save(AipSession.fromAipSessionDto(aipSessionDto)));
    }

    @Override
    public List<AipSessionDto> findOpenedAipSessionsByCharacterId(String characterId) {
        return aipSessionRepository.findAllByIsOpen(true).stream()
                .map(AipSessionDto::fromEntity)
                .map(aipSessionDto -> {
                    List<AipDto> aips = aipSessionDto.aips().stream()
                            .filter(aipDto -> aipDto.characterId().equals(characterId))
                            .toList();
                    return mascarade.mascaradebackend.dtos.AipSessionDto.builder()
                            .id(aipSessionDto.id())
                            .name(aipSessionDto.name())
                            .beginDate(aipSessionDto.beginDate())
                            .endDate(aipSessionDto.endDate())
                            .isOpen(aipSessionDto.isOpen())
                            .isClosed(aipSessionDto.isClosed())
                            .isRendered(aipSessionDto.isRendered())
                            .aips(aips)
                            .build();
                })
                .toList();
    }

    @Override
    public AipDto findAip(String sessionId, String characterId) {
        return aipSessionRepository.findById(new ObjectId(sessionId))
                .map(AipSessionDto::fromEntity)
                .orElseThrow(() -> {
                    log.error("AIP session not found, id: {}", sessionId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "AIP session not found");
                })
                .aips().stream()
                .filter(aipDto -> aipDto.characterId().equals(characterId)).findFirst()
                .orElseThrow(() -> {
                    log.error("AIP not found, sessionId: {}, characterId: {}", sessionId, characterId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,"AIP not found");
                });


    }
}
