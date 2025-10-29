package org.sopt.member.domain;

import java.time.LocalDate;
import org.sopt.member.dto.request.MemberCreateRequest;
import org.sopt.member.dto.response.MemberResponse;
import org.sopt.member.exception.MemberErrorCode;
import org.sopt.member.exception.MemberException;

public class Member {

    private Long id;
    private String name;
    private String email;
    private Gender gender;
    private LocalDate birthDate;

    private Member() {
    }

    private Member(Long id, String name, String email, Gender gender, LocalDate birthDate) {
        validateAge(birthDate);
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    private void validateAge(LocalDate birthDate) {
        if(calculateAgeFrom(birthDate) < 20) {
            throw new MemberException(MemberErrorCode.UNDERAGE_MEMBER);
        }
    }

    private static int calculateAgeFrom(LocalDate birthDate) {
        return LocalDate.now().getYear() - birthDate.getYear() + 1;
    }

    public static Member createWithoutId(MemberCreateRequest memberCreateRequest) {
        return new Member(
                null,
                memberCreateRequest.name(),
                memberCreateRequest.email(),
                memberCreateRequest.gender(),
                memberCreateRequest.birthdate()
        );
    }

    public static Member createWithId(Long id, String name, String email, Gender gender, LocalDate birthDate) {
        return new Member(id, name, email, gender, birthDate);
    }

    public MemberResponse toMemberResponse() {
        return new MemberResponse(id, name, email, gender, birthDate);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean hasEmail(String email) {
        return this.email.equals(email);
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}
