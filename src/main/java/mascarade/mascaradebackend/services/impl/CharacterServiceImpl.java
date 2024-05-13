package mascarade.mascaradebackend.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mascarade.mascaradebackend.dtos.CharacterDto;
import mascarade.mascaradebackend.entities.Character;
import mascarade.mascaradebackend.entities.DeletedCharacter;
import mascarade.mascaradebackend.entities.Herd;
import mascarade.mascaradebackend.enums.Clan;
import mascarade.mascaradebackend.repositories.CharacterRepository;
import mascarade.mascaradebackend.repositories.DeletedCharacterRepository;
import mascarade.mascaradebackend.services.CharacterService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CharacterServiceImpl implements CharacterService {

    private static final String CHARACTER_ALREADY_EXISTS = "Character with same name already exsists in database";
    private static final String CHARACTER_NOT_FOUND = "Character not found";

    private final CharacterRepository characterRepository;
    private final DeletedCharacterRepository deletedCharacterRepository;

    @Override
    public List<CharacterDto> findAll() {
        return characterRepository.findAll().stream()
                .map(CharacterDto::fromCharacter).toList();
    }

    @Override
    public CharacterDto addCharacter(CharacterDto characterDto) {
        Optional<Character> characterOpt = characterRepository.findByNameAndClan(characterDto.name(), characterDto.clan());
        if(characterOpt.isPresent()) {
            log.error(CHARACTER_ALREADY_EXISTS);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CHARACTER_ALREADY_EXISTS);
        }

        return CharacterDto.fromCharacter(characterRepository.insert(Character.fromCharacterDto(characterDto)));
    }

    @Override
    public CharacterDto updateCharacter(CharacterDto characterDto) {

        Optional<Character> characterOpt = characterRepository.findById(new ObjectId(characterDto.id()));
        if(characterOpt.isPresent()) {
            if(!characterOpt.get().name().equals(characterDto.name())) {
                Optional<Character> characterOpt2 = characterRepository.findByName(characterDto.name());
                if(characterOpt2.isPresent()) {
                    log.error(CHARACTER_ALREADY_EXISTS);
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CHARACTER_ALREADY_EXISTS);
                }
            }
            return CharacterDto.fromCharacter(characterRepository.save(updateCharacterFromDto(characterDto, characterOpt.get())));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, CHARACTER_NOT_FOUND);
    }

    private Character updateCharacterFromDto(CharacterDto characterDto, Character character) {
        return Character.builder()
                .id(character.id())
                .name(characterDto.name())
                .creationDate(character.creationDate())
                .clan(characterDto.clan())
                .competences(characterDto.competences())
                .assets(characterDto.assets())
                .herd(Objects.isNull(characterDto.herd()) ? Herd.createDefaultHerd() : Herd.fromHerdDto(characterDto.herd()))
                .playerId(characterDto.playerId())
                .build();
    }

    @Override
    public void archiveCharacter(String id) {
        Optional<Character> characterOpt = characterRepository.findById(new ObjectId(id));
        if(characterOpt.isPresent()) {
            deletedCharacterRepository.insert(DeletedCharacter.fromCharacter(characterOpt.get()));
            characterRepository.delete(characterOpt.get());
        } else {
            log.error(CHARACTER_NOT_FOUND);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, CHARACTER_NOT_FOUND);
        }

    }

    @Override
    public Boolean isTaken(String name, Clan clan) {
        Optional<Character> characterOpt = characterRepository.findByNameAndClan(name, clan);
        return characterOpt.isPresent();
    }

    @Override
    public List<CharacterDto> findByPlayerId(String id) {
        Optional<List<Character>> charactersOpt = characterRepository.findByPlayerId(id);
        return charactersOpt.map(characters -> characters.stream()
                .map(CharacterDto::fromCharacter)
                .toList()).orElseGet(List::of);
    }

    @Override
    public CharacterDto findById(String id) {
        Optional<Character> characterOpt = characterRepository.findById(new ObjectId(id));
        return characterOpt.map(CharacterDto::fromCharacter)
                .orElseThrow(() -> {
                    log.error(CHARACTER_NOT_FOUND);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, CHARACTER_NOT_FOUND);
                });
    }
}
