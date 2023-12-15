package db.project.service;

import db.project.dto.*;
import db.project.exceptions.BoardException;
import db.project.exceptions.ErrorCode;
import db.project.repository.BoardCommentRepository;
import db.project.repository.BoardRepository;
import db.project.repository.BoardViewsRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final BoardViewsRepository boardViewsRepository;

    public BoardService(BoardRepository boardRepository, BoardCommentRepository boardCommentRepository, BoardViewsRepository boardViewsRepository) {
        this.boardRepository = boardRepository;
        this.boardCommentRepository = boardCommentRepository;
        this.boardViewsRepository = boardViewsRepository;
    }

    @Transactional
    public BoardDto.BoardListResponse boardList(Optional<Integer> page) {
        int boardPage;
        if(page.isEmpty()) {
            boardPage = 0;
        } else {
            boardPage = (page.get() - 1) * 10;
        }

        Optional<List<BoardDto.BoardList>> boardListDtoOptional = boardRepository.findBoard(boardPage);
        if(boardListDtoOptional.isEmpty()) {
            throw new BoardException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        int boardCount = boardRepository.findBoardCount();

        if(boardPage != 0 && boardCount <= boardPage) {
            throw new BoardException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        List<BoardDto.BoardList> boardListDto = boardListDtoOptional.get();
        BoardDto.BoardListResponse response = new BoardDto.BoardListResponse(boardCount);
        for (BoardDto.BoardList board : boardListDto) {
            response.getBoardList().add(board);
        }
        return response;
    }

    @Transactional
    public BoardDto.BoardInfo boardInfo(int boardId) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Integer> view_id = boardViewsRepository.findIdByBoardAndUser(boardId, user_id);
        if(view_id.isEmpty()) {
            Optional<Integer> checkPage = boardViewsRepository.createBoardViews(boardId, user_id);
            if(checkPage.isEmpty()) {
                throw new BoardException("page not post", ErrorCode.NOT_FOUND_POST);
            }
            boardRepository.updateViewsById(boardId);
        }

        Optional<BoardDto.BoardInfo> boardInfoDtoOptional = boardRepository.findBoardById(boardId);
        if(boardInfoDtoOptional.isEmpty()) {
            throw new BoardException("page not post", ErrorCode.NOT_FOUND_POST);
        }

        BoardDto.BoardInfo boardInfoDto = boardInfoDtoOptional.get();
        if(user_id.equals(boardInfoDto.getUser_id())) {
            boardInfoDto.setAuthor(true);
        } else {
            boardInfoDto.setAuthor(false);
        }

        List<BoardCommentDto.BoardComment> boardCommentListDto = boardCommentRepository.findCommentById(boardId);
        if(boardCommentListDto.isEmpty()) {
            boardInfoDto.setComments(null);
        } else {
            boardInfoDto.setComments(boardCommentListDto);
        }

        return boardInfoDto;
    }

    public void boardCreate(BoardDto.BoardCreateAndUpdate boardCreateDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        boardRepository.createBoard(boardCreateDto, user_id);
    }

    @Transactional
    public void boardUpdate(BoardDto.BoardCreateAndUpdate boardUpdateDto, int board_id) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<String> userId = boardRepository.findUserIdById(board_id);
        if(userId.isEmpty()) {
            throw new BoardException("page not post", ErrorCode.NOT_FOUND_POST);
        }

        if(user_id.equals(userId.get())) {
            boardRepository.updateBoardById(boardUpdateDto, board_id);
        } else {
            throw new BoardException("not author of the post", ErrorCode.NOT_AUTHOR);
        }

    }

    @Transactional
    public void boardDelete(BoardDto.BoardDelete boardDeleteDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<String> userId = boardRepository.findUserIdById(boardDeleteDto.getBoard_id());
        if(userId.isEmpty()) {
            throw new BoardException("page not post", ErrorCode.NOT_FOUND_POST);
        }

        if(user_id.equals(userId.get())) {
            boardRepository.deleteBoardById(boardDeleteDto);
        } else {
            throw new BoardException("not author of the post", ErrorCode.NOT_AUTHOR);
        }
    }
}
