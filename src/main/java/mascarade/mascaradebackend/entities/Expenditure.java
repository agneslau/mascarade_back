package mascarade.mascaradebackend.entities;

import lombok.Builder;
import mascarade.mascaradebackend.dtos.ExpenditureDto;

@Builder
public record Expenditure(
        int sum,
        String reason,
        String response
) {

    public static Expenditure fromDto(ExpenditureDto expenditureDto) {
        return Expenditure.builder()
                .sum(expenditureDto.sum())
                .reason(expenditureDto.reason())
                .response(expenditureDto.response())
                .build();
    }
}
