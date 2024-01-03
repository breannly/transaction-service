package org.proj3ct.transactionservive.dto.payout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PayoutListDto {

    private List<PayoutDto> payoutList;

    public static PayoutListDto of(List<PayoutDto> list) {
        PayoutListDto payoutListDto = new PayoutListDto();
        payoutListDto.setPayoutList(list);
        return payoutListDto;
    }
}
