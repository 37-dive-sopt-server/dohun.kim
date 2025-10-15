package org.sopt.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import org.sopt.service.MemberServiceImpl;

public class MemberController {

    private final MemberServiceImpl memberServiceImpl = new MemberServiceImpl();

    public Long createMember(String name, String email, Gender gender, LocalDate birthDate) {
        return memberServiceImpl.join(name, email, gender, birthDate);
    }

    public Optional<Member> findMemberById(Long id) {
        return memberServiceImpl.findOne(id);
    }

    public List<Member> getAllMembers() {
        return memberServiceImpl.findAllMembers();
    }

    public void deleteMemberById(Long memberId) {
        memberServiceImpl.deleteMember(memberId);
    }
}
