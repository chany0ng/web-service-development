package db.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
<<<<<<<< HEAD:backend/src/main/java/db/project/dto/ReturnGetBoardAndNoticeListDto.java
@AllArgsConstructor
public class ReturnGetBoardAndNoticeListDto {
    private String title;
    private String date;
========
public class ReturnGetAdminMainDto {
    private String user_id;
    private int report;
>>>>>>>> backend:backend/src/main/java/db/project/dto/ReturnGetAdminMainDto.java
}
