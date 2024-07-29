package com.fusionsoft.boardback.service;

import org.springframework.http.ResponseEntity;

import com.fusionsoft.boardback.dto.request.auth.SignInRequestDto;
import com.fusionsoft.boardback.dto.request.auth.SignUpRequestDto;
import com.fusionsoft.boardback.dto.response.auth.SignInResponseDto;
import com.fusionsoft.boardback.dto.response.auth.SignUpResponseDto;

public interface AuthService {
    
    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
}
