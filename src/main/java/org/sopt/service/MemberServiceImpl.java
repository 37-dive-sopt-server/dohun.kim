package org.sopt.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import org.sopt.repository.FileMemberRepository;
import org.sopt.repository.MemberRepository;

public class MemberServiceImpl implements MemberService {

    private static long sequence = 1L;

    private final MemberRepository memberRepository = new FileMemberRepository();

    public MemberServiceImpl() {
        sequence = Math.max(sequence,
                memberRepository.findAll().stream()
                        .mapToLong(Member::getId)
                        .max()
                        .orElse(0L)
                        + 1);
    }

    public synchronized Long join(String name, String email, Gender gender, LocalDate birthDate) {
        if (memberRepository.existsByEmail(email)) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
        Long currentId = sequence++;
        Member member = Member.createOf(currentId, name, email, gender, birthDate);
        Member savedMember = memberRepository.save(member);
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
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
        memberRepository.deleteById(memberId);
    }
}
