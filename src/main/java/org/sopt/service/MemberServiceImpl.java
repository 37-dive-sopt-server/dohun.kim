package org.sopt.service;

import java.util.List;
import java.util.Optional;
import org.sopt.domain.Member;
import org.sopt.dto.MemberCreateRequest;
import org.sopt.dto.MemberListResponse;
import org.sopt.dto.MemberResponse;
import org.sopt.exception.MemberErrorCode;
import org.sopt.exception.MemberException;
import org.sopt.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse join(MemberCreateRequest memberCreateRequest) {
        if (memberRepository.existsByEmail(memberCreateRequest.email())) {
            throw new MemberException(MemberErrorCode.DUPLICATE_EMAIL);
        }
        Member savedMember = memberRepository.save(Member.createWithoutId(memberCreateRequest));
        return savedMember.toMemberResponse();
    }

    public MemberResponse findOne(Long memberId) {
        Optional<Member> foundMember = memberRepository.findById(memberId);
        if (foundMember.isEmpty()) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }
        return foundMember.get().toMemberResponse();
    }

    public MemberListResponse findAllMembers() {
        List<Member> allMember = memberRepository.findAll();
        return new MemberListResponse(
                allMember.stream()
                        .map(Member::toMemberResponse)
                        .toList()
        );
    }

    public void deleteMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }
        memberRepository.deleteById(memberId);
    }
}
