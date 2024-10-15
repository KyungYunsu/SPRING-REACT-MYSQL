package com.fusionsoft.boardback.service;

import org.springframework.http.ResponseEntity;

import com.fusionsoft.boardback.dto.response.user.GetSignInUserResponseDto;
import com.fusionsoft.boardback.dto.response.user.GetUserResponseDto;

public interface UserService {
    
    ResponseEntity<? super GetUserResponseDto> getUser(String email);
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);
    
}
