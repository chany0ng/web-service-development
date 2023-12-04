package db.project.service;

import db.project.dto.BoardListResponseDto;
import db.project.dto.PostBoardCreateAndUpdateDto;
import db.project.dto.ReturnGetBoardInfoDto;
import db.project.dto.ReturnGetBoardListDto;
import db.project.exceptions.BoardException;
import db.project.exceptions.ErrorCode;
import db.project.repository.BoardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public BoardListResponseDto boardList(int page) {
        page = (page - 1) * 10;
        Optional<List<ReturnGetBoardListDto>> boardListOptional = boardRepository.boardList(page);
        if(boardListOptional.isEmpty()) {
            throw new BoardException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        int boardCount = boardRepository.getBoardCount();
        if(boardCount / 10 < page) {
            throw new BoardException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        List<ReturnGetBoardListDto> boardList = boardListOptional.get();
        BoardListResponseDto response = new BoardListResponseDto(boardCount);
        for (ReturnGetBoardListDto board : boardList) {
            response.getBoardList().add(board);
        }
        return response;
    }

    public ReturnGetBoardInfoDto boardInfo(int boardId) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<ReturnGetBoardInfoDto> returnGetBoardInfoDtoOptional = boardRepository.boardInfo(boardId - 1);
        if(returnGetBoardInfoDtoOptional.isEmpty()) {
            throw new BoardException("page not post", ErrorCode.NOT_FOUND_POST);
        }
        ReturnGetBoardInfoDto returnGetBoardInfoDto = returnGetBoardInfoDtoOptional.get();
        if(user_id.equals(returnGetBoardInfoDto.getUser_id())) {
            returnGetBoardInfoDto.setAuthor(true);
        } else {
            returnGetBoardInfoDto.setAuthor(false);
        }
        return returnGetBoardInfoDto;
    }

    public void boardCreate(PostBoardCreateAndUpdateDto postBoardCreateAndUpdateDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        boardRepository.boardCreate(postBoardCreateAndUpdateDto, user_id);
    }

    @Transactional
    public void boardUpdate(PostBoardCreateAndUpdateDto postBoardCreateAndUpdateDto, int board_id) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Integer> boardId = boardRepository.getBoardId(board_id - 1);
        if(boardId.isEmpty()) {
            throw new BoardException("page not post", ErrorCode.NOT_FOUND_POST);
        }
        String userId = boardRepository.isAuthor(boardId.get());
        if(user_id.equals(userId)) {
            boardRepository.boardUpdate(postBoardCreateAndUpdateDto, boardId.get());
        } else {
            throw new BoardException("not author of the post", ErrorCode.NOT_AUTHOR);
        }

    }

    @Transactional
    public void boardDelete(int board_id) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Integer> boardId = boardRepository.getBoardId(board_id - 1);
        if(boardId.isEmpty()) {
            throw new BoardException("page not post", ErrorCode.NOT_FOUND_POST);
        }
        String userId = boardRepository.isAuthor(boardId.get());
        if(user_id.equals(userId)) {
            boardRepository.boardDelete(boardId.get());
        } else {
            throw new BoardException("not author of the post", ErrorCode.NOT_AUTHOR);
        }
    }
}
