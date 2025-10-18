package org.sopt.domain;

import java.time.LocalDate;
import org.sopt.exception.MemberErrorCode;
import org.sopt.exception.MemberException;

public class Member {

    private Long id;
    private String name;
    private String email;
    private Gender gender;
    private LocalDate birthDate;

    private Member() {
    }

    private Member(Long id, String name, String email, Gender gender, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birthDate = birthDate;
        int calculatedAge = calculateAge(birthDate);
        if (calculatedAge < 20) {
            throw new MemberException(MemberErrorCode.UNDERAGE_MEMBER);
        }
    }

    private int calculateAge(LocalDate birthDate) {
        return LocalDate.now().getYear() - birthDate.getYear() + 1;
    }

    public static Member createWithoutId(String name, String email, Gender gender, LocalDate birthDate) {
        return new Member(null, name, email, gender, birthDate);
    }

    public static Member createWithId(Long id, String name, String email, Gender gender, LocalDate birthDate) {
        return new Member(id, name, email, gender, birthDate);
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

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}
