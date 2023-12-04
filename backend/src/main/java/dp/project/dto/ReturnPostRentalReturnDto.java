package dp.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnPostRentalReturnDto {
    private int overfee;
    private int fee;
    private int withdraw;

    public ReturnPostRentalReturnDto() {
    }

    public ReturnPostRentalReturnDto(int fee, int withdraw, int overfee) {
        this.overfee = overfee;
        this.withdraw = withdraw;
        this.fee = fee;
    }
}
