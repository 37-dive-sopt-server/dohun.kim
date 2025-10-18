package org.sopt.validator;

import java.time.LocalDate;
import org.sopt.domain.Gender;
import org.sopt.exception.MemberErrorCode;
import org.sopt.exception.MemberException;

public class MemberInputValidator {

    private static final String NAME_REGEX = "^[a-zA-Z0-9가-힣]+$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public String validateEmail(String email) {
        if (email.trim().isEmpty()) {
            throw new MemberException(MemberErrorCode.EMPTY_EMAIL);
        }
        if (!email.matches(EMAIL_REGEX)) {
            throw new MemberException(MemberErrorCode.INVALID_EMAIL_FORMAT);
        }
        return email;
    }

    public String validateName(String name) {
        if (name.trim().isEmpty()) {
            throw new MemberException(MemberErrorCode.EMPTY_NAME);
        }
        if (!name.matches(NAME_REGEX)) {
            throw new MemberException(MemberErrorCode.INVALID_NAME_FORMAT);
        }
        return name;
    }

    public LocalDate validateBirthDate(String birthDateInput) {
        if (birthDateInput.trim().isEmpty()) {
            throw new MemberException(MemberErrorCode.EMPTY_BIRTHDATE);
        }
        try {
            return LocalDate.parse(birthDateInput);
        } catch (Exception e) {
            throw new MemberException(MemberErrorCode.INVALID_BIRTHDATE_FORMAT);
        }
    }

    public Gender validateGender(String genderInput) {
        try {
            return Gender.valueOf(genderInput.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new MemberException(MemberErrorCode.INVALID_GENDER_INPUT);
        }
    }

    public Long validateId(String idInput) {
        try {
            return Long.parseLong(idInput.trim());
        } catch (NumberFormatException e) {
            throw new MemberException(MemberErrorCode.INVALID_ID_FORMAT);
        }
    }
}
