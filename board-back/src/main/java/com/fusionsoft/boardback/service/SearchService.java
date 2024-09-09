package com.fusionsoft.boardback.service;

import org.springframework.http.ResponseEntity;

import com.fusionsoft.boardback.dto.response.search.GetPopularListResponseDto;

public interface SearchService {
    
    ResponseEntity<? super GetPopularListResponseDto> getPopularList();
}
