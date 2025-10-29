package org.sopt.service;

import org.sopt.dto.MemberCreateRequest;
import org.sopt.dto.MemberListResponse;
import org.sopt.dto.MemberResponse;

public interface MemberService {

    MemberResponse join(MemberCreateRequest memberCreateRequest);

    MemberResponse findOne(Long memberId);

    MemberListResponse findAllMembers();

    void deleteMember(Long memberId);
}
