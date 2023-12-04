package db.project.service;

import db.project.dto.BoardListResponseDto;
import db.project.dto.PostBoardCreateAndUpdateDto;
import db.project.dto.ReturnGetBoardInfoDto;
import db.project.dto.ReturnGetBoardListDto;
import db.project.exceptions.BoardException;
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

    public BoardListResponseDto boardList(int page) {
        page = (page - 1) * 10;
        Optional<List<ReturnGetBoardListDto>> boardListOptional = boardRepository.boardList(page);
        if(boardListOptional.isEmpty()) {
            throw new BoardException("잘못된 페이지 접근입니다.");
        }
        List<ReturnGetBoardListDto> boardList = boardListOptional.get();
        BoardListResponseDto response = new BoardListResponseDto();
        for (ReturnGetBoardListDto board : boardList) {
            response.getBoardList().add(board);
        }
        return response;
    }

    public ReturnGetBoardInfoDto boardInfo(int boardId) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<ReturnGetBoardInfoDto> returnGetBoardInfoDtoOptional = boardRepository.boardInfo(boardId - 1);
        if(returnGetBoardInfoDtoOptional.isEmpty()) {
            throw new BoardException("존재하지 않는 게시물 입니다.");
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
    public String boardUpdate(PostBoardCreateAndUpdateDto postBoardCreateAndUpdateDto, int board_id) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Integer> boardId = boardRepository.getBoardId(board_id - 1);
        if(boardId.isEmpty()) {
            throw new BoardException("존재하지 않는 게시물 입니다.");
        }
        String userId = boardRepository.isAuthor(boardId.get());
        if(user_id.equals(userId)) {
            return boardRepository.boardUpdate(postBoardCreateAndUpdateDto, boardId.get())
                    .orElseThrow(() -> new BoardException("게시물 업데이트 실패"));
        } else {
            throw new BoardException("게시물의 작성자가 아닙니다.");
        }

    }

    @Transactional
    public String boardDelete(int board_id) {
        String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Integer> boardId = boardRepository.getBoardId(board_id - 1);
        if(boardId.isEmpty()) {
            throw new BoardException("존재하지 않는 게시물 입니다.");
        }
        String userId = boardRepository.isAuthor(boardId.get());
        if(user_id.equals(userId)) {
            return boardRepository.boardDelete(boardId.get())
                    .orElseThrow(() -> new BoardException("게시물 삭제 실패"));
        } else {
            throw new BoardException("게시물의 작성자가 아닙니다.");
        }
    }
}
