package com.example.boardserver.user.domain;

import com.example.boardserver.board.domain.Board;
import com.example.boardserver.board.domain.Comment;
import com.example.boardserver.common.domain.BaseEntity;
import com.example.boardserver.user.domain.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 50, unique = true)
    private String nickname;

    @Column(length = 150)
    private String profile;

    @Builder.Default
    @Column(nullable = false, length = 10, columnDefinition = "VARCHAR(10) DEFAULT 'false'")
    private Boolean social = false;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false, length = 10, columnDefinition = "VARCHAR(10) DEFAULT 'USER'")
    private RoleType role = RoleType.USER;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Board> boardList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    /**
     * 유저 권한 변경
     * @param role RoleType
     */
    public void changeRole(RoleType role) {
        this.role = role;
    }

    /**
     * 패스워드 암호화
     * @param encodedPassword String
     */
    public void encodedPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    /**
     * 닉네임 변경
     * @param nickname String
     */
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 프로필 변경
     * @param profile String
     */
    public void changeProfile(String profile) {
        this.profile = profile;
    }
}
