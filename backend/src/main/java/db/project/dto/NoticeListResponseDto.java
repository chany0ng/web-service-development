package db.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class NoticeListResponseDto {
    private List<ReturnGetNoticeListDto> noticeList;

    public NoticeListResponseDto() {
        this.noticeList = new ArrayList<>();
    }
}
