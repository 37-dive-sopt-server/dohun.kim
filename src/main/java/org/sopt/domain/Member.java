package org.sopt.domain;

import java.time.LocalDate;

public class Member {

    private Long id;
    private String name;
    private String email;
    private Gender gender;
    private LocalDate birthDate;
    private int age;

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
            throw new IllegalArgumentException("20세 미만은 가입할 수 없습니다.");
        }
        this.age = calculatedAge;
    }

    private int calculateAge(LocalDate birthDate) {
        return LocalDate.now().getYear() - birthDate.getYear() + 1;
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
