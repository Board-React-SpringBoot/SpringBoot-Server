package com.example.boardserver.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public class UserRequestDTO {

    /**
     * 닉네임 변경용 RequestDTO
     * @param nickname String
     */
    @Builder
    public record UpdateNickname(@NotBlank String nickname) {}

    /**
     * 프로필 변경용 RequestDTO
     * @param profile String
     */
    @Builder
    public record UpdateProfile(String profile) {

        /**
         * Profile이 "" 빈 문자열일 경우 null로 변환
         * @param profile String
         */
        public UpdateProfile {
            if (profile != null && profile.isBlank()) {
                profile = null;
            }
        }
    }
}
