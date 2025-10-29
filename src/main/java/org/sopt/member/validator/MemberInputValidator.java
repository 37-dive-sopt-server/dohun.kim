package org.sopt.member.validator;

import java.time.LocalDate;
import org.sopt.member.domain.Gender;
import org.sopt.member.dto.request.MemberCreateRequest;
import org.sopt.member.exception.MemberErrorCode;
import org.sopt.member.exception.MemberException;
import org.springframework.stereotype.Component;

@Component
public class MemberInputValidator {

    private static final String NAME_REGEX = "^[a-zA-Z0-9가-힣]+$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public void validate(MemberCreateRequest memberCreateRequest) {
        validateEmail(memberCreateRequest.email());
        validateName(memberCreateRequest.name());
        validateBirthDate(memberCreateRequest.birthdate());
        validateGender(memberCreateRequest.gender());
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new MemberException(MemberErrorCode.EMPTY_EMAIL);
        }
        if (!email.matches(EMAIL_REGEX)) {
            throw new MemberException(MemberErrorCode.INVALID_EMAIL_FORMAT);
        }
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new MemberException(MemberErrorCode.EMPTY_NAME);
        }
        if (!name.matches(NAME_REGEX)) {
            throw new MemberException(MemberErrorCode.INVALID_NAME_FORMAT);
        }
    }

    private void validateBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new MemberException(MemberErrorCode.EMPTY_BIRTHDATE);
        }
        if( birthDate.isAfter(LocalDate.now())) {
            throw new MemberException(MemberErrorCode.INVALID_BIRTHDATE);
        }
    }

    private void validateGender(Gender gender) {
        if (gender == null) {
            throw new MemberException(MemberErrorCode.EMPTY_GENDER);
        }
    }

}
