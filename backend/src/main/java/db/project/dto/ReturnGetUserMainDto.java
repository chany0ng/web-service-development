package db.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnGetUserMainDto {
    private String user_id;
    private String email;
    private String phone_number;
    private int cash;
    private int overfee;
    private int hour;
    private int isRented;
    private String bike_id;
}
