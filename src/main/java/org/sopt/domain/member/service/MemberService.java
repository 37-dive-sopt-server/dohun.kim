package org.sopt.domain.member.service;

import org.sopt.domain.member.dto.request.MemberCreateRequest;
import org.sopt.domain.member.dto.response.MemberListResponse;
import org.sopt.domain.member.dto.response.MemberResponse;

public interface MemberService {

    MemberResponse join(MemberCreateRequest memberCreateRequest);

    MemberResponse findOne(Long memberId);

    MemberListResponse findAllMembers();

    void deleteMember(Long memberId);
}
