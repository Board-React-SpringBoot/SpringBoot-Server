package com.example.boardserver.board.domain;

import com.example.boardserver.board.dto.boardDTO.BoardRequestDTO;
import com.example.boardserver.common.domain.BaseEntity;
import com.example.boardserver.user.domain.User;
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
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private String mainImg;

    @Column(nullable = false)
    @Builder.Default
    private Integer likeCount = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer commentCount = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer viewCount = 0;

    @Builder.Default
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardImg> boardImgList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    public void increaseViewCount() {
        this.viewCount = this.viewCount + 1;
    }

    public void increaseLikeCount() {
        this.likeCount = this.likeCount + 1;
    }

    public void decreaseLikeCount() {
        this.likeCount = this.likeCount - 1;
    }

    public void increaseCommentCount() {
        this.commentCount = this.commentCount + 1;
    }

    public void decreaseCommentCount() {
        this.commentCount = this.commentCount - 1;
    }

    public void patchBoard (BoardRequestDTO request) {
        this.title = request.title();
        this.content = request.content();
        this.mainImg = request.boardMainImg();

        this.boardImgList.clear();

        if (request.boardMainImg() != null) {
            for (String imgUrl : request.boardImageList()) {
                BoardImg boardImg = BoardImg.builder()
                        .board(this)
                        .img(imgUrl)
                        .build();

                this.boardImgList.add(boardImg);
            }
        }
    }
}
