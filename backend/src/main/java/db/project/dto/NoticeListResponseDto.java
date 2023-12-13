package db.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NoticeListResponseDto {
    private int noticeCount;
    private List<ReturnGetNoticeListDto> noticeList;

    public NoticeListResponseDto(int noticeCount) {
        this.noticeCount = noticeCount;
        this.noticeList = new ArrayList<>();
    }
}
