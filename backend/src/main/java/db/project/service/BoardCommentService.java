package db.project.service;

import db.project.dto.BoardCommentDto;
import db.project.exceptions.BoardCommentException;
import db.project.exceptions.ErrorCode;
import db.project.repository.BoardCommentRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BoardCommentService {
    private final BoardCommentRepository boardCommentRepository;

    public BoardCommentService(BoardCommentRepository boardCommentRepository) {
        this.boardCommentRepository = boardCommentRepository;
    }

    @Transactional
    public void createComment(BoardCommentDto.BoardCommentCreateAndUpdate boardCommentCreateDto, int board_id) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        boardCommentRepository.createComment(boardCommentCreateDto, user_id, board_id);
    }

    @Transactional
    public void deleteComment(BoardCommentDto.BoardCommentDelete boardCommentDeleteDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<String> userId = boardCommentRepository.isAuthor(boardCommentDeleteDto.getComment_id());
        if(userId.isEmpty()) {
            throw new BoardCommentException("page not post", ErrorCode.NOT_FOUND_POST);
        }

        if(user_id.equals(userId.get())) {
            boardCommentRepository.deleteComment(boardCommentDeleteDto.getComment_id());
        } else {
            throw new BoardCommentException("not author of the post", ErrorCode.NOT_AUTHOR);
        }
    }

    @Transactional
    public void updateComment(BoardCommentDto.BoardCommentCreateAndUpdate boardCommentUpdateDto, int comment_id) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<String> userId = boardCommentRepository.isAuthor(comment_id);
        if(userId.isEmpty()) {
            throw new BoardCommentException("page not post", ErrorCode.NOT_FOUND_POST);
        }

        if(user_id.equals(userId.get())) {
            boardCommentRepository.updateComment(boardCommentUpdateDto, comment_id);
        } else {
            throw new BoardCommentException("not author of the post", ErrorCode.NOT_AUTHOR);
        }
    }
}
