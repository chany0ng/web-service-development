package db.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
<<<<<<<< HEAD:backend/src/main/java/db/project/dto/ReturnGetBoardAndNoticeInfoDto.java
public class ReturnGetBoardAndNoticeInfoDto {
========
@AllArgsConstructor
public class ReturnGetUserInfoListDto {
>>>>>>>> backend:backend/src/main/java/db/project/dto/ReturnGetUserInfoListDto.java
    private String user_id;
    private String phone_number;
    private String email;
}
