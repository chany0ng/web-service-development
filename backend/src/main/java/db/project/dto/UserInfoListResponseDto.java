package db.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoListResponseDto {
    private int userCount;
    List<ReturnGetUserInfoListDto> userInfoList;

    public UserInfoListResponseDto(int userCount) {
        this.userCount = userCount;
        this.userInfoList = new ArrayList<>();
    }
}
