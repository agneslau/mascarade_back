package mascarade.mascaradebackend.entities;

import com.mongodb.lang.Nullable;
import lombok.Builder;
import mascarade.mascaradebackend.dtos.AipSessionDto;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.List;

@Builder
public record AipSession(
        @MongoId
        ObjectId id,
        String name,
        LocalDate beginDate,
        LocalDate endDate,
        boolean isOpen,
        boolean isClosed,
        boolean isRendered,

        @Nullable
        @Field("aips")
        List<Aip> aips
) {
        public static AipSession fromAipSessionDto(AipSessionDto aipSessionDto){
                return AipSession.builder()
                        .id(new ObjectId(aipSessionDto.id()))
                        .name(aipSessionDto.name())
                        .beginDate(aipSessionDto.beginDate())
                        .endDate(aipSessionDto.endDate())
                        .isOpen(aipSessionDto.isOpen())
                        .isClosed(aipSessionDto.isClosed())
                        .isRendered(aipSessionDto.isRendered())
                        .aips(aipSessionDto.aips().stream()
                                .map(Aip::fromDto)
                                .toList())
                        .build();
        }
        public static AipSession fromAipSessionDtoToInsert(AipSessionDto aipSessionDto){
                return AipSession.builder()
                        .name(aipSessionDto.name())
                        .beginDate(aipSessionDto.beginDate())
                        .endDate(aipSessionDto.endDate())
                        .isOpen(aipSessionDto.isOpen())
                        .isClosed(aipSessionDto.isClosed())
                        .isRendered(aipSessionDto.isRendered())
                        .aips(aipSessionDto.aips().stream()
                                .map(Aip::fromDto)
                                .toList())
                        .build();
        }

}
