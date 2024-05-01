package mascarade.mascaradebackend.entities;

import lombok.Builder;
import mascarade.mascaradebackend.dtos.AipSessionDto;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;

@Builder
public record AipSession(
        @MongoId
        ObjectId id,
        String name,
        LocalDate beginDate,
        LocalDate endDate,
        boolean isOpen,
        boolean isClosed,
        boolean isRendered
) {
        public static AipSession fromAipSessionDto(AipSessionDto aipSessionDto){
                return AipSession.builder()
                        .name(aipSessionDto.name())
                        .beginDate(aipSessionDto.beginDate())
                        .endDate(aipSessionDto.endDate())
                        .isOpen(aipSessionDto.isOpen())
                        .isClosed(aipSessionDto.isClosed())
                        .isRendered(aipSessionDto.isRendered())
                        .build();
        }

}
