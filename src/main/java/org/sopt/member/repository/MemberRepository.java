package org.sopt.member.repository;

import java.util.List;
import java.util.Optional;
import org.sopt.member.domain.Member;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long memberId);
    List<Member> findAll();
    Boolean existsById(Long memberId);
    Boolean existsByEmail(String email);
    void deleteById(Long memberId);
}
