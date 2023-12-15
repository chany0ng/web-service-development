package db.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
<<<<<<<< HEAD:backend/src/main/java/db/project/dto/ReturnGetBikeListDto.java
public class ReturnGetBikeListDto {
    private String bike_id;
    private String address;
    private String status;
========
public class ReturnGetLocationListDto {
    private String location_id;
    private String address;
    private int bikeCount;
>>>>>>>> backend:backend/src/main/java/db/project/dto/ReturnGetLocationListDto.java
}
