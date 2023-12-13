package db.project.service;

import db.project.dto.*;
import db.project.exceptions.BoardException;
import db.project.exceptions.ErrorCode;
import db.project.repository.BoardCommentRepository;
import db.project.repository.BoardRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardCommentRepository boardCommentRepository;

    public BoardService(BoardRepository boardRepository, BoardCommentRepository boardCommentRepository) {
        this.boardRepository = boardRepository;
        this.boardCommentRepository = boardCommentRepository;
    }

    @Transactional
    public BoardListResponseDto boardList(Optional<Integer> page) {
        int boardPage;
        if(page.isEmpty()) {
            boardPage = 0;
        } else {
            boardPage = (page.get() - 1) * 10;
        }

        Optional<List<ReturnGetBoardListDto>> boardListOptional = boardRepository.boardList(boardPage);
        if(boardListOptional.isEmpty()) {
            throw new BoardException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        int boardCount = boardRepository.getBoardCount();

        if(boardPage != 0 && boardCount <= boardPage) {
            throw new BoardException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        List<ReturnGetBoardListDto> boardList = boardListOptional.get();
        BoardListResponseDto response = new BoardListResponseDto(boardCount);
        for (ReturnGetBoardListDto board : boardList) {
            response.getBoardList().add(board);
        }
        return response;
    }

    @Transactional
    public ReturnGetBoardInfoDto boardInfo(int boardId) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Integer> view_id = boardRepository.getUserIdAndBoardId(boardId, user_id);
        if(view_id.isEmpty()) {
            Optional<Integer> checkPage = boardRepository.insertBoardViews(boardId, user_id);
            if(checkPage.isEmpty()) {
                throw new BoardException("page not post", ErrorCode.NOT_FOUND_POST);
            }
            boardRepository.updateBoardView(boardId);
        }

        Optional<ReturnGetBoardInfoDto> returnGetBoardInfoDtoOptional = boardRepository.boardInfo(boardId);
        if(returnGetBoardInfoDtoOptional.isEmpty()) {
            throw new BoardException("page not post", ErrorCode.NOT_FOUND_POST);
        }

        List<GetBoardCommentDto> boardCommentList = boardCommentRepository.getCommentList(boardId);

        ReturnGetBoardInfoDto returnGetBoardInfoDto = returnGetBoardInfoDtoOptional.get();
        if(user_id.equals(returnGetBoardInfoDto.getUser_id())) {
            returnGetBoardInfoDto.setAuthor(true);
        } else {
            returnGetBoardInfoDto.setAuthor(false);
        }

        if(boardCommentList.isEmpty()) {
            returnGetBoardInfoDto.setComments(null);
        } else {
            returnGetBoardInfoDto.setComments(boardCommentList);
        }

        return returnGetBoardInfoDto;
    }

    public void boardCreate(PostBoardAndNoticeCreateAndUpdateDto postBoardCreateDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        boardRepository.boardCreate(postBoardCreateDto, user_id);
    }

    @Transactional
    public void boardUpdate(PostBoardAndNoticeCreateAndUpdateDto postBoardUpdateDto, int board_id) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<String> userId = boardRepository.isAuthor(board_id);
        if(userId.isEmpty()) {
            throw new BoardException("page not post", ErrorCode.NOT_FOUND_POST);
        }

        if(user_id.equals(userId.get())) {
            boardRepository.boardUpdate(postBoardUpdateDto, board_id);
        } else {
            throw new BoardException("not author of the post", ErrorCode.NOT_AUTHOR);
        }

    }

    @Transactional
    public void boardDelete(PostBoardAndNoticeDeleteDto postBoardDeleteDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<String> userId = boardRepository.isAuthor(postBoardDeleteDto.getId());
        if(userId.isEmpty()) {
            throw new BoardException("page not post", ErrorCode.NOT_FOUND_POST);
        }

        if(user_id.equals(userId.get())) {
            boardRepository.boardDelete(postBoardDeleteDto);
        } else {
            throw new BoardException("not author of the post", ErrorCode.NOT_AUTHOR);
        }
    }
}
