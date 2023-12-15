package db.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
<<<<<<<< HEAD:backend/src/main/java/db/project/dto/ReturnGetAdminMainDto.java
public class ReturnGetAdminMainDto {
    private String user_id;
    private int report;
========
public class ReturnGetBikeListDto {
    private String bike_id;
    private String address;
    private String status;
>>>>>>>> backend:backend/src/main/java/db/project/dto/ReturnGetBikeListDto.java
}
