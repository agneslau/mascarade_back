package mascarade.mascaradebackend.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mascarade.mascaradebackend.dtos.AipSessionDto;
import mascarade.mascaradebackend.entities.AipSession;
import mascarade.mascaradebackend.repositories.AipSessionRepository;
import mascarade.mascaradebackend.services.AipService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AipServiceImpl implements AipService {

    private final AipSessionRepository aipSessionRepository;

    @Override
    public List<AipSessionDto> findAllAipSessions() {
        return aipSessionRepository.findAll().stream()
                .map(AipSessionDto::fromAipSession).toList();
    }

    @Override
    public AipSessionDto addAipSession(AipSessionDto aipSessionDto) {
        return AipSessionDto.fromAipSession(aipSessionRepository.insert(AipSession.fromAipSessionDto(aipSessionDto)));
    }

    @Override
    public void deleteAipSession(String id) {
        aipSessionRepository.deleteById(new ObjectId(id));
    }
}
