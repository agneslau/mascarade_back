package mascarade.mascaradebackend.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mascarade.mascaradebackend.dtos.InfluenceDto;
import mascarade.mascaradebackend.entities.DeletedInfluence;
import mascarade.mascaradebackend.entities.Influence;
import mascarade.mascaradebackend.repositories.DeletedInfluenceRepository;
import mascarade.mascaradebackend.repositories.InfluenceRepository;
import mascarade.mascaradebackend.services.InfluenceService;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class InfluenceServiceImpl implements InfluenceService {

    private final InfluenceRepository influenceRepository;
    private final DeletedInfluenceRepository deletedInfluenceRepository;

    @Override
    public List<InfluenceDto> findAll() {
        return influenceRepository.findAll().stream().map(InfluenceDto::fromInfluence).toList();
    }

    @Override
    public List<InfluenceDto> findByCharacterId(String characterId) {
        return influenceRepository.findByCharacterId(new ObjectId(characterId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Influence not found"))
                .stream()
                .map(InfluenceDto::fromInfluence)
                .toList();
    }

    @Override
    public List<InfluenceDto> addInfluences(List<InfluenceDto> influenceDtos) {
        return influenceDtos.stream()
                .map(influenceDto -> InfluenceDto.fromInfluence(influenceRepository.insert(Influence.fromInfluenceDto(influenceDto))))
                .toList();
    }

    @Override
    public List<InfluenceDto> editInfluences(List<InfluenceDto> influencesDtos) {
        Optional<List<Influence>> influencesOpt = influenceRepository.findByCharacterId(new ObjectId(influencesDtos.getFirst().characterId()));

        //delete influences
        if(influencesOpt.isPresent()){
            List<Influence> influenceToDelete = influencesOpt.get().stream()
                    .filter(influence -> !influencesDtos.stream()
                            .map(InfluenceDto::id)
                            .toList().contains(influence.id().toString()))
                    .toList();
            influenceRepository.deleteAll(influenceToDelete);
        }
        //add or update remaining influences
        return influencesDtos.stream()
                .map(influenceDto -> {
                    if(influenceDto.id().isBlank()) {
                        return addInfluence(influenceDto);
                    } else {
                        Optional<Influence> influenceOpt = influenceRepository.findById(new ObjectId(influenceDto.id()));
                        if(influenceOpt.isPresent()){
                            return InfluenceDto.fromInfluence(influenceRepository.save(updateInfluenceFromDto(influenceDto, influenceOpt.get())));
                        } else {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Influence not found");
                        }
                    }
                })
                .toList();
    }

    @Override
    public InfluenceDto addInfluence(InfluenceDto influenceDto) {
        return InfluenceDto.fromInfluence(influenceRepository.insert(Influence.fromInfluenceDto(influenceDto)));
    }

    @Override
    public void archiveInfluence(String influenceId) {
        Optional<Influence> influenceOpt = influenceRepository.findById(new ObjectId(influenceId));
        influenceOpt.ifPresent(influence -> deletedInfluenceRepository.insert(DeletedInfluence.fromInfluence(influence)));

    }

    private Influence updateInfluenceFromDto(InfluenceDto influenceDto, Influence influence) {
        return Influence.builder()
                .id(influence.id())
                .name(influenceDto.name())
                .level(influenceDto.level())
                .specialty(influenceDto.specialty())
                .category(influenceDto.category())
                .characterId(new ObjectId(influenceDto.characterId()))
                .district(influenceDto.district())
                .build();
    }
}
