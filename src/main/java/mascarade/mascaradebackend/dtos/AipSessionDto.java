package mascarade.mascaradebackend.dtos;

import mascarade.mascaradebackend.entities.AipSession;

import java.time.LocalDate;

public record AipSessionDto(
        String id,
        String name,
        LocalDate beginDate,
        LocalDate endDate,
        boolean isOpen,
        boolean isClosed,
        boolean isRendered
) {
    public static AipSessionDto fromAipSession(AipSession aipSession){
        return new AipSessionDto(
                aipSession.id().toString(),
                aipSession.name(),
                aipSession.beginDate(),
                aipSession.endDate(),
                aipSession.isOpen(),
                aipSession.isClosed(),
                aipSession.isRendered()
        );
    }
}
