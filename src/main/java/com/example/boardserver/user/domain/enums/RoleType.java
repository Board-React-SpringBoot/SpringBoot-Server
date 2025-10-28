package com.example.boardserver.user.domain.enums;

public enum RoleType {
    USER, ADMIN;

    /**
     * String 타입의 Role을 RoleType으로 변환
     * @param role String
     * @return RoleType - ROLE_ 접두사가 제거됨
     */
    public static RoleType toRoleType(String role) {
        if (role.startsWith("ROLE_")) {
            return RoleType.valueOf(role.substring(5));
        }

        return RoleType.valueOf(role);
    }
}
