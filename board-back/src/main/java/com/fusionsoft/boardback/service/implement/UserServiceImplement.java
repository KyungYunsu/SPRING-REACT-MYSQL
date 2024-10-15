package com.fusionsoft.boardback.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fusionsoft.boardback.dto.response.ResponseDto;
import com.fusionsoft.boardback.dto.response.user.GetSignInUserResponseDto;
import com.fusionsoft.boardback.dto.response.user.GetUserResponseDto;
import com.fusionsoft.boardback.entity.UserEntity;
import com.fusionsoft.boardback.repository.UserRepository;
import com.fusionsoft.boardback.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super GetUserResponseDto> getUser(String email) {
        
        UserEntity userEntity = null;

        try{
            userEntity = userRepository.findByEmail(email);
            if(userEntity == null) return GetUserResponseDto.noExistUser();

        }catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetUserResponseDto.success(userEntity);
    }

    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email) {
        
        UserEntity userEntity = null;

        try {
            
            userEntity = userRepository.findByEmail(email);
            if(userEntity == null) return GetSignInUserResponseDto.noExistUser();


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetSignInUserResponseDto.success(userEntity);
    
    }


    
}
