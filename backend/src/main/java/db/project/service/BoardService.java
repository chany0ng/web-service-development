package db.project.service;

import db.project.dto.BoardAndNoticeListResponseDto;
import db.project.dto.PostBoardAndNoticeCreateAndUpdateDto;
import db.project.dto.ReturnGetBoardAndNoticeInfoDto;
import db.project.dto.ReturnGetBoardAndNoticeListDto;
import db.project.exceptions.BoardException;
import db.project.exceptions.ErrorCode;
import db.project.repository.BoardRepository;
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
    public BoardAndNoticeListResponseDto boardList(int page) {
        page = (page - 1) * 10;
        Optional<List<ReturnGetBoardAndNoticeListDto>> boardListOptional = boardRepository.boardList(page);
        if(boardListOptional.isEmpty()) {
            throw new BoardException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        int boardCount = boardRepository.getBoardCount();

        if(page != 0 && boardCount <= page) {
            throw new BoardException("page not found", ErrorCode.NOT_FOUND_PAGE);
        }

        List<ReturnGetBoardAndNoticeListDto> boardList = boardListOptional.get();
        BoardAndNoticeListResponseDto response = new BoardAndNoticeListResponseDto(boardCount);
        for (ReturnGetBoardAndNoticeListDto board : boardList) {
            response.getBoardAndNoticeList().add(board);
        }
        return response;
    }

    public ReturnGetBoardAndNoticeInfoDto boardInfo(int boardId) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<ReturnGetBoardAndNoticeInfoDto> returnGetBoardInfoDtoOptional = boardRepository.boardInfo(boardId - 1);
        if(returnGetBoardInfoDtoOptional.isEmpty()) {
            throw new BoardException("page not post", ErrorCode.NOT_FOUND_POST);
        }
        ReturnGetBoardAndNoticeInfoDto returnGetBoardAndNoticeInfoDto = returnGetBoardInfoDtoOptional.get();
        if(user_id.equals(returnGetBoardAndNoticeInfoDto.getUser_id())) {
            returnGetBoardAndNoticeInfoDto.setAuthor(true);
        } else {
            returnGetBoardAndNoticeInfoDto.setAuthor(false);
        }
        return returnGetBoardAndNoticeInfoDto;
    }

    public void boardCreate(PostBoardAndNoticeCreateAndUpdateDto postBoardCreateDto) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        boardRepository.boardCreate(postBoardCreateDto, user_id);
    }

    @Transactional
    public void boardUpdate(PostBoardAndNoticeCreateAndUpdateDto postBoardUpdateDto, int board_id) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Integer> boardId = boardRepository.getBoardId(board_id - 1);
        if(boardId.isEmpty()) {
            throw new BoardException("page not post", ErrorCode.NOT_FOUND_POST);
        }
        String userId = boardRepository.isAuthor(boardId.get());
        if(user_id.equals(userId)) {
            boardRepository.boardUpdate(postBoardUpdateDto, boardId.get());
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
