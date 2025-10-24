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

    @Column(nullable = false, length = 50)
    private String password;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 50, unique = true)
    private String nickname;

    @Column(length = 150)
    private String profile;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false, length = 15, columnDefinition = "VARCHAR(15) DEFAULT 'USER'")
    private RoleType role = RoleType.USER;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Board> boardList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();
}
