package com.fusionsoft.boardback.dto.response.board;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fusionsoft.boardback.common.ResponseCode;
import com.fusionsoft.boardback.common.ResponseMessage;
import com.fusionsoft.boardback.dto.object.BoardListItem;
import com.fusionsoft.boardback.dto.response.ResponseDto;
import com.fusionsoft.boardback.entity.BoardListViewEntity;

import lombok.Getter;

@Getter
public class GetUserBoardListResponseDto extends ResponseDto {
    
    private List<BoardListItem> userBoardList;

    private GetUserBoardListResponseDto(List<BoardListViewEntity> boardListBiewEntites) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userBoardList = BoardListItem.getList(boardListBiewEntites);
    }

    public static ResponseEntity<GetUserBoardListResponseDto> success(List<BoardListViewEntity> boardListBiewEntites) {
        
        GetUserBoardListResponseDto result = new GetUserBoardListResponseDto(boardListBiewEntites);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public static ResponseEntity<ResponseDto> noExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

    }
}
