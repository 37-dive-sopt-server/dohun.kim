package org.sopt.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.domain.member.dto.response.MemberResponse;
import org.sopt.domain.member.service.AuthService;
import org.sopt.global.common.dto.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "헤더 기반 Basic-Authentication")
    @PostMapping("/v1/login")
    public ResponseEntity<ApiResponse<MemberResponse>> login(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization
    ) {
        log.info(authorization);
        MemberResponse result = authService.login(authorization);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    @Operation(summary = "이메일/비밀번호 기반 로그인 (쿠키 발급)")
    @PostMapping("/v2/login")
    public ResponseEntity<ApiResponse<MemberResponse>> loginV2(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        MemberResponse result = authService.loginWithCredentials(email, password);

        String credentials = email + ":" + password;

        ResponseCookie cookie = ResponseCookie.from("basic", credentials)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .maxAge(Duration.ofHours(1))
                .path("/")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.ok(result));
    }

    @Operation(summary = "쿠키로 로그인 여부 확인")
    @GetMapping("/v2/me")
    public ResponseEntity<ApiResponse<MemberResponse>> checkSession(
            @Parameter(hidden = true) @CookieValue(value = "basic", required = false) String basicCookie
    ) {
        if (basicCookie == null) {
            throw new IllegalArgumentException("로그인 쿠키가 없습니다.");
        }

        int idx = basicCookie.indexOf(":");
        if (idx < 0) {
            throw new IllegalArgumentException("쿠키에 이메일과 비밀번호 구분자(:)가 없습니다.");
        }
        String email = basicCookie.substring(0, idx);
        String password = basicCookie.substring(idx + 1);

        log.info("[v2] 세션 확인, 이메일: {}", email);
        MemberResponse result = authService.loginWithCredentials(email, password);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }
}


