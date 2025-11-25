package com.example.boardserver.auth.config;

import com.example.boardserver.auth.filter.CustomLogoutFilter;
import com.example.boardserver.auth.filter.JWTExceptionFilter;
import com.example.boardserver.auth.filter.JWTFilter;
import com.example.boardserver.auth.filter.LoginFilter;
import com.example.boardserver.auth.handler.CustomAccessDeniedHandler;
import com.example.boardserver.auth.handler.CustomAuthenticationEntryPoint;
import com.example.boardserver.auth.handler.CustomFailureHandler;
import com.example.boardserver.auth.handler.CustomSuccessHandler;
import com.example.boardserver.auth.jwt.JWTProvider;
import com.example.boardserver.auth.service.CustomUserDetailService;
import com.example.boardserver.auth.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTProvider jwtProvider;
    private final JWTExceptionFilter jwtExceptionFilter;
    private final CustomSuccessHandler customSuccessHandler;
    private final CustomFailureHandler customFailureHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JWTService jwtService;

    private static final String[] PERMIT_URLS = {
            "/", "/test/post",
            "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html",
            "/api/v1/auth/login", "/api/v1/auth/join",
            "/api/v1/auth/oauth2/google", "/api/v1/auth/google",
            "/api/v1/auth/oauth2/naver", "/api/v1/auth/naver",
            "/api/v1/auth/reissue"
    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomUserDetailService customUserDetailService) throws Exception {

        LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration), jwtProvider, jwtService);
        loginFilter.setFilterProcessesUrl("/api/v1/auth/login");

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(PERMIT_URLS).permitAll()
                        .requestMatchers("/api/v1/user/**", "/api/v1/board/**", "/file/**").authenticated()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().permitAll())

                .oauth2Login((oauth2) -> oauth2
                        .authorizationEndpoint(auth -> auth.baseUri("/api/v1/auth/oauth2"))
                        .userInfoEndpoint((config) -> config.userService(customUserDetailService))
                        .successHandler(customSuccessHandler)
                        .failureHandler(customFailureHandler))  // JWT 발급 핸들러 추가

                .exceptionHandling(exceptionHandler-> exceptionHandler
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler))

                .addFilterBefore(new JWTFilter(jwtProvider), LoginFilter.class)
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JWTFilter.class)
                .addFilterBefore(new CustomLogoutFilter(jwtProvider, jwtService), LoginFilter.class)

                .cors((cors) -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setMaxAge(3600L);
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Set-Cookie"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}