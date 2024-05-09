package mascarade.mascaradebackend.dtos;

import lombok.Builder;
import mascarade.mascaradebackend.entities.Expenditure;

@Builder
public record ExpenditureDto(
        int sum,
        String reason,
        String response
) {
    public static ExpenditureDto fromEntity(Expenditure expenditure) {
        return ExpenditureDto.builder()
                .sum(expenditure.sum())
                .reason(expenditure.reason())
                .response(expenditure.response())
                .build();
    }
}
