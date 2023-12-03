package database4.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReturnPostUserLoginDto {

    private String user_id;
    private int cash;
    private String bike_id;
    private String remain_rental_time;

    public ReturnPostUserLoginDto(){};

    public ReturnPostUserLoginDto(String user_id, int cash, String bike_id, String remain_rental_time) {
        this.user_id = user_id;
        this.cash = cash;
        this.bike_id = bike_id;
        this.remain_rental_time = remain_rental_time;
    }
}
