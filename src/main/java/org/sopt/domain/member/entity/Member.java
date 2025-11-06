package org.sopt.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.domain.member.dto.request.MemberCreateRequest;
import org.sopt.domain.member.dto.response.MemberResponse;
import org.sopt.domain.member.exception.MemberDomainErrorCode;
import org.sopt.domain.member.exception.MemberDomainException;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birthDate;

    private static void validateAge(LocalDate birthDate) {
        if (calculateAgeFrom(birthDate) < 20) {
            throw new MemberDomainException(MemberDomainErrorCode.UNDERAGE_MEMBER);
        }
    }

    private static int calculateAgeFrom(LocalDate birthDate) {
        return LocalDate.now().getYear() - birthDate.getYear() + 1;
    }

    public static Member of(MemberCreateRequest memberCreateRequest) {
        validateAge(memberCreateRequest.birthdate());
        return Member.builder()
                .name(memberCreateRequest.name())
                .email(memberCreateRequest.email())
                .gender(memberCreateRequest.gender())
                .birthDate(memberCreateRequest.birthdate())
                .build();

    }

    public MemberResponse toMemberResponse() {
        return new MemberResponse(id, name, email, gender, birthDate);
    }

}
