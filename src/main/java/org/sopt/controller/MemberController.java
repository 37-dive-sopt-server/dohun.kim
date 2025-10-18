package org.sopt.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import org.sopt.service.MemberService;

public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public Long createMember(String name, String email, Gender gender, LocalDate birthDate) {
        return memberService.join(name, email, gender, birthDate);
    }

    public Optional<Member> findMemberById(Long id) {
        return memberService.findOne(id);
    }

    public List<Member> getAllMembers() {
        return memberService.findAllMembers();
    }

    public void deleteMemberById(Long memberId) {
        memberService.deleteMember(memberId);
    }
}
