package database4.service;

import database4.repository.NoticeRepository;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public void noticeList() {

    }

    public void noticeInfo() {

    }
}
