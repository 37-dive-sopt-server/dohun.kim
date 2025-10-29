package org.sopt.member.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.sopt.member.domain.Member;
import org.sopt.member.repository.storage.MemberFileStorage;
import org.springframework.stereotype.Repository;

@Repository
public class FileMemberRepository implements MemberRepository {

    private static final String STORAGE_DIR_NAME = "data";
    private static final String STORAGE_FILE_NAME = "members.csv";

    private final Map<Long, Member> store = new HashMap<>();
    private final MemberFileStorage storage = new MemberFileStorage(STORAGE_DIR_NAME, STORAGE_FILE_NAME);
    private long sequence;

    public FileMemberRepository() {
        storage.loadAll().forEach(member -> store.put(member.getId(), member));
        sequence = store.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
    }

    @Override
    public Member save(Member member) {
        Member memberToStore = member;
        if (member.getId() == null) {
            memberToStore = Member.createWithId(sequence++, member.getName(), member.getEmail(), member.getGender(),
                    member.getBirthDate());
        } else {
            sequence = Math.max(sequence, member.getId() + 1);
        }
        store.put(memberToStore.getId(), memberToStore);
        storage.writeAll(store.values());
        return memberToStore;
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return Optional.ofNullable(store.get(memberId));
    }

    @Override
    public List<Member> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public Boolean existsById(Long memberId) {
        return store.containsKey(memberId);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return store.values().stream()
                .anyMatch(member -> member.getEmail().equals(email));
    }

    @Override
    public void deleteById(Long memberId) {
        if (store.remove(memberId) != null) {
            storage.writeAll(store.values());
        }
    }
}
