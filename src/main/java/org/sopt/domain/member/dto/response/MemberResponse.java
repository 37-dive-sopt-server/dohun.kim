package org.sopt.domain.member.dto.response;

import java.time.LocalDate;
import org.sopt.domain.member.entity.Gender;

public record MemberResponse(
        Long id,
        String name,
        String email,
        Gender gender,
        LocalDate birthdate
) {
}
