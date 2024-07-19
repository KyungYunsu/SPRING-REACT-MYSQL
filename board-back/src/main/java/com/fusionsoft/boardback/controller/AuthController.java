package com.fusionsoft.boardback.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fusionsoft.boardback.dto.request.auth.SignUpRequestDto;
import com.fusionsoft.boardback.service.AuthService;
import com.fusionsoft.boardback.dto.response.auth.SignUpResponseDto;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    
    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp(
        @RequestBody @Valid SignUpRequestDto requestDto
    ) {
        ResponseEntity<? super SignUpResponseDto> response = authService.signUp(requestDto);
        return response;
    }
}
