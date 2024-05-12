package mascarade.mascaradebackend.services;

import mascarade.mascaradebackend.dtos.CharacterDto;
import mascarade.mascaradebackend.dtos.HerdDto;
import mascarade.mascaradebackend.entities.Herd;
import mascarade.mascaradebackend.enums.Asset;
import mascarade.mascaradebackend.enums.Clan;
import mascarade.mascaradebackend.repositories.CharacterRepository;
import mascarade.mascaradebackend.services.impl.CharacterServiceImpl;
import mascarade.mascaradebackend.entities.Character;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CharacterServiceTest {

    @Mock
    private CharacterRepository characterRepository;

    @InjectMocks
    private CharacterServiceImpl characterService;

    @Test
    void shouldFindAllCharacters() {
        ObjectId objectId1 = new ObjectId();
        ObjectId objectId2 = new ObjectId();

        Herd herd1 = Herd.builder()
                .name("herd1")
                .build();
        Herd herd2 = Herd.builder()
                .name("")
                .build();

        Character character1 = Character.builder()
                .name("name1")
                .clan(Clan.BRUJAH)
                .herd(herd1)
                .id(objectId1)
                .build();

        Character character2 = Character.builder()
                .name("name2")
                .clan(Clan.GANGREL)
                .herd(herd2)
                .id(objectId2)
                .build();

        List<Character> allCharacters = List.of(character1, character2);

        when(characterRepository.findAll()).thenReturn(allCharacters);

        HerdDto herdDto1 = HerdDto.builder()
                .name("herd1")
                .build();

        HerdDto herdDto2 = HerdDto.builder()
                .name("")
                .build();

        List<CharacterDto> result = characterService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.getFirst().id()).isEqualTo(objectId1.toString());
        assertThat(result.getFirst().name()).isEqualTo("name1");
        assertThat(result.getFirst().clan()).isEqualTo(Clan.BRUJAH);
        assertThat(result.getFirst().herd()).isEqualTo(herdDto1);
        assertThat(result.getLast().id()).isEqualTo(objectId2.toString());
        assertThat(result.getLast().name()).isEqualTo("name2");
        assertThat(result.getLast().clan()).isEqualTo(Clan.GANGREL);
        assertThat(result.getLast().herd()).isEqualTo(herdDto2);

        verify(characterRepository, times(1)).findAll();
    }

    @Test
    void shouldAddCharacter() {
        ObjectId objectId = new ObjectId();

        Herd herd = Herd.builder()
                .name("herd")
                .build();

        HerdDto herdDto = HerdDto.builder()
                .name("herd")
                .build();

        Character character = Character.builder()
                .name("name")
                .clan(Clan.BRUJAH)
                .herd(herd)
                .id(objectId)
                .build();

        CharacterDto characterDto = CharacterDto.builder()
                .name("name")
                .clan(Clan.BRUJAH)
                .herd(herdDto)
                .build();

        when(characterRepository.findByNameAndClan("name", Clan.BRUJAH)).thenReturn(java.util.Optional.empty());
        when(characterRepository.insert(Character.fromCharacterDto(characterDto))).thenReturn(character);

        CharacterDto result = characterService.addCharacter(characterDto);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(objectId.toString());
        assertThat(result.name()).isEqualTo("name");
        assertThat(result.clan()).isEqualTo(Clan.BRUJAH);
        assertThat(result.herd()).isEqualTo(herdDto);

        verify(characterRepository, times(1)).findByNameAndClan("name", Clan.BRUJAH);
        verify(characterRepository, times(1)).insert(Character.fromCharacterDto(characterDto));
    }

    @Test
    void shouldUpdateCharacter() {
        ObjectId objectId = new ObjectId();

        Herd herd = Herd.builder()
                .name("herd")
                .build();

        HerdDto herdDto = HerdDto.builder()
                .name("herd")
                .build();

        Character character = Character.builder()
                .name("name")
                .clan(Clan.BRUJAH)
                .herd(herd)
                .assets(List.of())
                .id(objectId)
                .build();

        Character characterToSave = Character.builder()
                .name("name")
                .clan(Clan.BRUJAH)
                .herd(herd)
                .assets(List.of(Asset.PARANGON, Asset.MARIONNETTISTE))
                .id(objectId)
                .build();

        CharacterDto characterDto = CharacterDto.builder()
                .id(objectId.toString())
                .name("name")
                .clan(Clan.BRUJAH)
                .assets(List.of(Asset.PARANGON, Asset.MARIONNETTISTE))
                .herd(herdDto)
                .build();

        when(characterRepository.findById(objectId)).thenReturn(Optional.of(character));
        when(characterRepository.save(characterToSave)).thenReturn(characterToSave);

        CharacterDto result = characterService.updateCharacter(characterDto);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(objectId.toString());
        assertThat(result.name()).isEqualTo("name");
        assertThat(result.clan()).isEqualTo(Clan.BRUJAH);
        assertThat(result.assets()).containsExactly(Asset.PARANGON, Asset.MARIONNETTISTE);
        assertThat(result.herd()).isEqualTo(herdDto);

        verify(characterRepository, times(1)).findById(objectId);
        verify(characterRepository, times(1)).save(characterToSave);
    }

    @Test
    void shouldReturnTrue_whenNameAndClanAreTaken() {
        when(characterRepository.findByNameAndClan("name", Clan.BRUJAH)).thenReturn(Optional.of(Character.builder().build()));

        Boolean result = characterService.isTaken("name", Clan.BRUJAH);

        assertThat(result).isTrue();

        verify(characterRepository, times(1)).findByNameAndClan("name", Clan.BRUJAH);
    }

    @Test
    void shouldReturnFalse_whenNameAndClanAreNotTaken() {
        when(characterRepository.findByNameAndClan("name", Clan.BRUJAH)).thenReturn(Optional.empty());

        Boolean result = characterService.isTaken("name", Clan.BRUJAH);

        assertThat(result).isFalse();

        verify(characterRepository, times(1)).findByNameAndClan("name", Clan.BRUJAH);
    }

    @Test
    void shouldReturnListOfCharacter_whenPlayerIdHasMatches() {
        ObjectId objectId = new ObjectId();
        ObjectId objectId2 = new ObjectId();
        ObjectId playerId = new ObjectId();

        Herd herd = Herd.builder()
                .name("herd")
                .build();

        Character character = Character.builder()
                .name("name")
                .clan(Clan.BRUJAH)
                .herd(herd)
                .playerId(playerId.toString())
                .id(objectId)
                .build();

        Character character2 = Character.builder()
                .name("name2")
                .clan(Clan.GANGREL)
                .herd(herd)
                .playerId(playerId.toString())
                .id(objectId2)
                .build();

        when(characterRepository.findByPlayerId(playerId.toString())).thenReturn(Optional.of(List.of(character, character2)));

        List<CharacterDto> result = characterService.findByPlayerId(playerId.toString());

        assertThat(result).hasSize(2);
        assertThat(result.getFirst().id()).isEqualTo(objectId.toString());
        assertThat(result.getFirst().playerId()).isEqualTo(playerId.toString());
        assertThat(result.getLast().id()).isEqualTo(objectId2.toString());
        assertThat(result.getLast().playerId()).isEqualTo(playerId.toString());

        verify(characterRepository, times(1)).findByPlayerId(playerId.toString());
    }

    @Test
    void shouldReturnEmptyList_whenPlayerIdHasNoMatch() {
        ObjectId playerId = new ObjectId();

        when(characterRepository.findByPlayerId(playerId.toString())).thenReturn(Optional.empty());

        List<CharacterDto> result = characterService.findByPlayerId(playerId.toString());

        assertThat(result).isEmpty();

        verify(characterRepository, times(1)).findByPlayerId(playerId.toString());
    }

    @Test
    void shouldReturnCharacter_whenCharacterExists() {
        ObjectId objectId = new ObjectId();

        Herd herd = Herd.builder()
                .name("herd")
                .build();

        Character character = Character.builder()
                .name("name")
                .clan(Clan.BRUJAH)
                .herd(herd)
                .id(objectId)
                .build();

        when(characterRepository.findById(objectId)).thenReturn(Optional.of(character));

        CharacterDto result = characterService.findById(objectId.toString());

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(objectId.toString());
        assertThat(result.name()).isEqualTo("name");
        assertThat(result.clan()).isEqualTo(Clan.BRUJAH);
        assertThat(result.herd().name()).isEqualTo("herd");

        verify(characterRepository, times(1)).findById(objectId);
    }
}
