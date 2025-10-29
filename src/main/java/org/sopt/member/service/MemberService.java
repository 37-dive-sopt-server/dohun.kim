package org.sopt.member.service;

import org.sopt.member.dto.request.MemberCreateRequest;
import org.sopt.member.dto.response.MemberListResponse;
import org.sopt.member.dto.response.MemberResponse;

public interface MemberService {

    MemberResponse join(MemberCreateRequest memberCreateRequest);

    MemberResponse findOne(Long memberId);

    MemberListResponse findAllMembers();

    void deleteMember(Long memberId);
}
