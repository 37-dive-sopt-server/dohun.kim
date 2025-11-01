package org.sopt.member.dto.request;

import java.time.LocalDate;
import org.sopt.member.domain.Gender;

public record MemberCreateRequest(
        String name,
        String email,
        Gender gender,
        LocalDate birthdate
) {
}
