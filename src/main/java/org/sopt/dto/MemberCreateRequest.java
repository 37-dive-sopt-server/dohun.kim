package org.sopt.dto;

import java.time.LocalDate;
import org.sopt.domain.Gender;

public record MemberCreateRequest(
        String name,
        String email,
        Gender gender,
        LocalDate birthdate
) {
}
