package com.fusionsoft.boardback.entity;

import java.util.Date; // Add this import statement
import java.time.Instant; // Add this import statement
import java.text.SimpleDateFormat; // Add this import statement

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fusionsoft.boardback.dto.request.board.PostCommentRequestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "comment")
@Table(name = "comment")
public class CommentEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentNumber; 
    private String content;
    private String writeDatetime;
    private String userEmail;
    private int boardNumber;

    public CommentEntity(PostCommentRequestDto dto, Integer boardNumber, String email) {
        
        Date now = Date.from(Instant.now());
        SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String writeDatetime = SimpleDateFormat.format(now);
        
        this.content = dto.getContent();
        this.writeDatetime = writeDatetime;
        this.userEmail = email;
        this.boardNumber = boardNumber;
    }
}
