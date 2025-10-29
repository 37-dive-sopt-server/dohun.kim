package org.sopt.member.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.sopt.member.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    private final Map<Long, Member> store = new HashMap<>();
    private long sequence = 1L;

    @Override
    public Member save(Member member) {
        Member memberToStore = member;
        if (member.getId() == null) {
            memberToStore = Member.createWithId(sequence++, member.getName(), member.getEmail(), member.getGender(),
                    member.getBirthDate());
        }
        store.put(memberToStore.getId(), memberToStore);
        return memberToStore;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean existsById(Long memberId) {
        return store.containsKey(memberId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return store.values().stream()
                .anyMatch(member -> member.hasEmail(email));
    }

    @Override
    public void deleteById(Long memberId) {
        store.remove(memberId);
    }
}
