package com.example.boardserver.user.repository;

import com.example.boardserver.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * Email로 유저 정보를 조회하는 메소드
     * @param email
     * @return
     */
    Optional<User> findByEmail(String email);

    /**
     * 해당 Email 계정이 DB에 존재하는 지 검증하는 메소드
     * @param email String
     * @return Boolean
     */
    Boolean existsByEmail(String email);

    /**
     * 해당 Nickname 계정이 DB에 존재하는 지 검증하는 메소드
     * @param nickname String
     * @return Boolean
     */
    Boolean existsByNickname(String nickname);
}
