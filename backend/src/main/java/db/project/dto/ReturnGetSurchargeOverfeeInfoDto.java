package db.project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnGetSurchargeOverfeeInfoDto {
    private int overfee;

    public ReturnGetSurchargeOverfeeInfoDto() {
    }

    public ReturnGetSurchargeOverfeeInfoDto(int overfee) {
        this.overfee = overfee;
    }
}
