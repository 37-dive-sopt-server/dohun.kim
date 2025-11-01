package org.sopt.member.dto.response;

import java.time.LocalDate;
import org.sopt.member.domain.Gender;

public record MemberResponse(
        Long id,
        String name,
        String email,
        Gender gender,
        LocalDate birthdate
) {
}
