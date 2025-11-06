package org.sopt.domain.member.dto.request;

import java.time.LocalDate;
import org.sopt.domain.member.entity.Gender;

public record MemberCreateRequest(
        String name,
        String email,
        Gender gender,
        LocalDate birthdate
) {
}
