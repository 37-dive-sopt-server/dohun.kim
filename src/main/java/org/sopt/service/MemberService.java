package org.sopt.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.sopt.domain.Gender;
import org.sopt.domain.Member;

public interface MemberService {

    Long join(String name, String email, Gender gender, LocalDate birthDate);

    Optional<Member> findOne(Long memberId);

    List<Member> findAllMembers();

    void deleteMember(Long memberId);
}
