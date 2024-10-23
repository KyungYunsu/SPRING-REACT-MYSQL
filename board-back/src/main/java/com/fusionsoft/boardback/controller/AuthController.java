package com.fusionsoft.boardback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionsoft.boardback.dto.request.auth.SignInRequestDto;
import com.fusionsoft.boardback.dto.request.auth.SignUpRequestDto;
import com.fusionsoft.boardback.dto.response.auth.SignInResponseDto;
import com.fusionsoft.boardback.dto.response.auth.SignUpResponseDto;
import com.fusionsoft.boardback.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


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

    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(
        @RequestBody @Valid SignInRequestDto requestBody
    ){
            System.out.println("로그인 요청 받음: " + requestBody.toString());
            ResponseEntity<? super SignInResponseDto> response = authService.signIn(requestBody);
            System.out.println("로그인 응답: " + response.toString());
            return response;
    }
    
}
