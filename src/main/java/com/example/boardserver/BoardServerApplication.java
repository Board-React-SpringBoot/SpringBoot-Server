package com.example.boardserver;

import com.example.boardserver.board.domain.BoardLikeId;
import com.example.boardserver.board.repository.BoardLikeRepositoryCustom;
import com.example.boardserver.board.service.boardLikeService.boardLikeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
public class BoardServerApplication {

/*    private final BoardLikeQueryService boardLikeQueryService;
    private final BoardLikeRepositoryCustom boardLikeRepositoryCustom;*/

    public static void main(String[] args) {
        SpringApplication.run(BoardServerApplication.class, args);
    }

/*    @Bean
    CommandLineRunner runAtStart() {
        return args -> {
            System.out.println("=== FindBoardLike 실행 ===");
            boardLikeQueryService.findBoardLike(1L, 1L);
            boardLikeRepositoryCustom.findBoardLike(new BoardLikeId(1L, 1L)).ifPresent(System.out::println);
        };
    }*/

}
