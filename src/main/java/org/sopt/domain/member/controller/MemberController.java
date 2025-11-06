package org.sopt.domain.member.controller;

import org.sopt.domain.member.dto.request.MemberCreateRequest;
import org.sopt.domain.member.dto.response.MemberListResponse;
import org.sopt.domain.member.dto.response.MemberResponse;
import org.sopt.global.common.dto.ApiResponse;
import org.sopt.domain.member.service.MemberService;
import org.sopt.domain.member.validator.MemberInputValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberInputValidator memberInputValidator;

    public MemberController(MemberService memberService, MemberInputValidator memberInputValidator) {
        this.memberService = memberService;
        this.memberInputValidator = memberInputValidator;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponse>> createMember(@RequestBody MemberCreateRequest memberCreateRequest) {
        memberInputValidator.validate(memberCreateRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(memberService.join(memberCreateRequest)));

    }

    @GetMapping("/{memberId}")
    public ApiResponse<MemberResponse> findMemberById(@PathVariable(name = "memberId") Long memberId) {
        return ApiResponse.ok(memberService.findOne(memberId));
    }

    @GetMapping
    public ApiResponse<MemberListResponse> getAllMembers() {
        return ApiResponse.ok(memberService.findAllMembers());

    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMemberById(@PathVariable(name = "memberId") Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }
}
