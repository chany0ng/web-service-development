package db.project.service;

import db.project.dto.PostBoardCommentCreateAndUpdateDto;
import db.project.dto.PostBoardCommentDeleteDto;
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
    public void createComment(PostBoardCommentCreateAndUpdateDto postBoardCommentCreateAndUpdateDto, int board_id) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        boardCommentRepository.createComment(postBoardCommentCreateAndUpdateDto, user_id, board_id);
    }

    @Transactional
    public void deleteComment(PostBoardCommentDeleteDto postBoardCommentDeleteDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<String> userId = boardCommentRepository.isAuthor(postBoardCommentDeleteDto.getComment_id());
        if(userId.isEmpty()) {
            throw new BoardCommentException("page not post", ErrorCode.NOT_FOUND_POST);
        }

        if(user_id.equals(userId.get())) {
            boardCommentRepository.deleteComment(postBoardCommentDeleteDto.getComment_id());
        } else {
            throw new BoardCommentException("not author of the post", ErrorCode.NOT_AUTHOR);
        }
    }

    @Transactional
    public void updateComment(PostBoardCommentCreateAndUpdateDto postBoardCommentCreateAndUpdateDto, int comment_id) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<String> userId = boardCommentRepository.isAuthor(comment_id);
        if(userId.isEmpty()) {
            throw new BoardCommentException("page not post", ErrorCode.NOT_FOUND_POST);
        }

        if(user_id.equals(userId.get())) {
            boardCommentRepository.updateComment(postBoardCommentCreateAndUpdateDto, comment_id);
        } else {
            throw new BoardCommentException("not author of the post", ErrorCode.NOT_AUTHOR);
        }
    }
}
