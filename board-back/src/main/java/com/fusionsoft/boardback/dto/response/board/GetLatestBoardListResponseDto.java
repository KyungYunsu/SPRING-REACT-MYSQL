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
public class GetLatestBoardListResponseDto extends ResponseDto{

    private List<BoardListItem> latestList;
    
    private GetLatestBoardListResponseDto(List<BoardListViewEntity> boardEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.latestList = BoardListItem.getList(boardEntities);
    }

    public static ResponseEntity<GetLatestBoardListResponseDto> success(List<BoardListViewEntity> boardEntities) {
        GetLatestBoardListResponseDto result = new GetLatestBoardListResponseDto(boardEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
