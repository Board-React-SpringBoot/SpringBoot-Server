package com.example.boardserver.common.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DeviceUtils {

    /**
     * User-Agent 헤더를 기반으로 요청한 클라이언트의 기기를 판별
     * @param request HttpServletRequest
     * @return "MOBILE", "TABLET", "PC", "INSOMNIA", "UNKNOWN"
     */
    public static String getDeviceType(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        System.out.println("userAgent : " + userAgent);

        if (userAgent == null || userAgent.isEmpty()) return "UNKNOWN";

        String uA = userAgent.toLowerCase();
        if (uA.contains("mobi")) return "MOBILE";
        if (uA.contains("tablet") || uA.contains("ipad")) return "TABLET";
        if (uA.contains("windows") || uA.contains("macintosh")) return "PC";
        if (uA.contains("insomnia")) return "INSOMNIA";

        return "UNKNOWN";
    }
}
