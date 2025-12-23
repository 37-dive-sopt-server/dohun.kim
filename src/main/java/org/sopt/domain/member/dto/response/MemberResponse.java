package org.sopt.domain.member.dto.response;

import java.time.LocalDate;
import org.sopt.domain.member.entity.Gender;
import org.sopt.domain.member.entity.Member;

public record MemberResponse(
        Long id,
        String name,
        String email,
        Gender gender,
        LocalDate birthdate
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getGender(),
                member.getBirthDate()
        );
    }
}
