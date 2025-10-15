package org.sopt.domain;

import java.time.LocalDate;

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
    }

    public static Member createOf(Long id, String name, String email, Gender gender, LocalDate birthDate) {
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
