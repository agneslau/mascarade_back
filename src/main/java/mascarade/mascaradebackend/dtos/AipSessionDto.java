package mascarade.mascaradebackend.dtos;

import lombok.Builder;
import mascarade.mascaradebackend.entities.AipSession;

import java.time.LocalDate;
import java.util.List;

@Builder
public record AipSessionDto(
        String id,
        String name,
        LocalDate beginDate,
        LocalDate endDate,
        boolean isOpen,
        boolean isClosed,
        boolean isRendered,
        List<AipDto> aips
) {
    public static AipSessionDto fromEntity(AipSession aipSession){
        return AipSessionDto.builder()
                .id(aipSession.id().toString())
                .name(aipSession.name())
                .beginDate(aipSession.beginDate())
                .endDate(aipSession.endDate())
                .isOpen(aipSession.isOpen())
                .isClosed(aipSession.isClosed())
                .isRendered(aipSession.isRendered())
                .aips(aipSession.aips() != null ? aipSession.aips().stream()
                        .map(AipDto::fromEntity)
                        .toList() : List.of())
                .build();
    }
}
