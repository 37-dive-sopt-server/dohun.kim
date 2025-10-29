package org.sopt.dto;

import java.util.List;

public record MemberListResponse(
        List<MemberResponse> members
) {
}
