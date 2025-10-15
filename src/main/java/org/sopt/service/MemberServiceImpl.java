package org.sopt.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import org.sopt.repository.MemoryMemberRepository;

public class MemberServiceImpl implements MemberService {

    private final MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    private static long sequence = 1L;

    public Long join(String name, String email, Gender gender, LocalDate birthDate) {
        try {
            Member member = Member.createOf(sequence++, name, email, gender, birthDate);
            if (memberRepository.existsByEmail(email)) {
                throw new IllegalStateException("이미 존재하는 이메일입니다.");
            }
            Member savedMember = memberRepository.save(member);
            return savedMember.getId();
        } catch (IllegalStateException | IllegalArgumentException e){
            sequence--;
            throw e;
        }
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public void deleteMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
        memberRepository.deleteById(memberId);
    }
}
