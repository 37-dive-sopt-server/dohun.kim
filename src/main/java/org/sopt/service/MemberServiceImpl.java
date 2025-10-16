package org.sopt.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import org.sopt.exception.MemberErrorCode;
import org.sopt.exception.MemberException;
import org.sopt.repository.MemberRepository;

public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public synchronized Long join(String name, String email, Gender gender, LocalDate birthDate) {
        if (memberRepository.existsByEmail(email)) {
            throw new MemberException(MemberErrorCode.DUPLICATE_EMAIL);
        }
        Member savedMember = memberRepository.save(Member.createWithoutId(name, email, gender, birthDate));
        return savedMember.getId();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public void deleteMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }
        memberRepository.deleteById(memberId);
    }
}
