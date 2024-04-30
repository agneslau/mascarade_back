package mascarade.mascaradebackend.services;

import mascarade.mascaradebackend.dtos.InfluenceDto;

import java.util.List;

public interface InfluenceService {

    List<InfluenceDto> findAll();
    List<InfluenceDto> findByCharacterId(String characterId);
    List<InfluenceDto> addInfluences(List<InfluenceDto> influenceDtos);
    List<InfluenceDto> editInfluences(List<InfluenceDto> influencesDtos);
    InfluenceDto addInfluence(InfluenceDto influenceDto);

    void archiveInfluence(String influenceId);
}
