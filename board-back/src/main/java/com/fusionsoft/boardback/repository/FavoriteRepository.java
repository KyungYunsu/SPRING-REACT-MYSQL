package com.fusionsoft.boardback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.fusionsoft.boardback.entity.FavoriteEntity;
import com.fusionsoft.boardback.entity.primaryKey.FavoritePk;
import com.fusionsoft.boardback.repository.resultSet.GetFavoriteListResultSet;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, FavoritePk>{
    
    FavoriteEntity findByBoardNumberAndUserEmail(Integer boardNumber, String userEmail);

    @Query(
        value = 
            "SELECT "
            + "u.email as email, "
            + "u.nickname as nickname, "
            + "u.profile_image as profileImage "
            + "FROM "
            + "favorite f "
            + "JOIN user u "
            + "ON f.user_email = u.email "
            + "WHERE f.board_number = ?1 ",
        nativeQuery = true
    )
    List<GetFavoriteListResultSet> getFavoriteList(Integer boardNumber);
}
