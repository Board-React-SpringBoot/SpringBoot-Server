package com.example.boardserver.user.repository;

import com.example.boardserver.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

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
