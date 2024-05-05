package mascarade.mascaradebackend.services;

import mascarade.mascaradebackend.dtos.AipSessionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AipService {
    List<AipSessionDto> findAllAipSessions();

    AipSessionDto addAipSession(AipSessionDto aipSessionDto);

    void deleteAipSession(String id);

    AipSessionDto updateAipSession(AipSessionDto aipSessionDto);

    List<AipSessionDto> findOpenedAipSessions();
}
