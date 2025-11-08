package org.sopt.domain.member.validator;

import java.time.LocalDate;
import java.util.regex.Pattern;
import org.sopt.domain.member.entity.Gender;
import org.sopt.domain.member.dto.request.MemberCreateRequest;
import org.sopt.domain.member.exception.MemberValidationErrorCode;
import org.sopt.domain.member.exception.MemberValidationException;
import org.springframework.stereotype.Component;

@Component
public class MemberInputValidator {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9가-힣]+$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public void validate(MemberCreateRequest memberCreateRequest) {
        validateEmail(memberCreateRequest.email());
        validateName(memberCreateRequest.name());
        validateBirthDate(memberCreateRequest.birthdate());
        validateGender(memberCreateRequest.gender());
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new MemberValidationException(MemberValidationErrorCode.EMPTY_EMAIL);
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new MemberValidationException(MemberValidationErrorCode.INVALID_EMAIL_FORMAT);
        }
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new MemberValidationException(MemberValidationErrorCode.EMPTY_NAME);
        }
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new MemberValidationException(MemberValidationErrorCode.INVALID_NAME_FORMAT);
        }
    }

    private void validateBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new MemberValidationException(MemberValidationErrorCode.EMPTY_BIRTHDATE);
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new MemberValidationException(MemberValidationErrorCode.INVALID_BIRTHDATE);
        }
    }

    private void validateGender(Gender gender) {
        if (gender == null) {
            throw new MemberValidationException(MemberValidationErrorCode.EMPTY_GENDER);
        }
    }

}
