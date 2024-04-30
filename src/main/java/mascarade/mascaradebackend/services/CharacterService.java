package mascarade.mascaradebackend.services;

import mascarade.mascaradebackend.dtos.CharacterDto;
import mascarade.mascaradebackend.enums.Clan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CharacterService {
    List<CharacterDto> findAll();

    CharacterDto addCharacter(CharacterDto characterDto);

    CharacterDto updateCharacter(CharacterDto characterDto);

    void archiveCharacter(String id);

    Boolean isTaken(String name, Clan clan);
}
