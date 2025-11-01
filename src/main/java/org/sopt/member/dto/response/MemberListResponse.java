package org.sopt.member.dto.response;

import java.util.List;

public record MemberListResponse(
        List<MemberResponse> members
) {
}
