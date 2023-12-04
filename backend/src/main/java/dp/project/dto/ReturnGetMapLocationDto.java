package dp.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReturnGetMapLocationDto {
    private int bikeCount;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public ReturnGetMapLocationDto() {
    }

    public ReturnGetMapLocationDto(BigDecimal latitude, BigDecimal longitude, int bikeCount) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.bikeCount = bikeCount;
    }
}
