package database4.controller;

import database4.service.NoticeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class NoticeController {
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("notice/list")
    @ResponseBody
    public void getNoticeList(@RequestParam(defaultValue = "1") int page) {

    }

    @GetMapping("notice/info/{noticeId}")
    @ResponseBody
    public void getNoticeInfo(@PathVariable int noticeId) {

    }
}
