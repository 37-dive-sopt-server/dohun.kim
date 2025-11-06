package org.sopt.domain.member.service;

import java.util.List;
import java.util.Optional;
import org.sopt.domain.member.entity.Member;
import org.sopt.domain.member.dto.request.MemberCreateRequest;
import org.sopt.domain.member.dto.response.MemberListResponse;
import org.sopt.domain.member.dto.response.MemberResponse;
import org.sopt.domain.member.exception.MemberDomainErrorCode;
import org.sopt.domain.member.exception.MemberDomainException;
import org.sopt.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse join(MemberCreateRequest memberCreateRequest) {
        Member savedMember = memberRepository.save(Member.of(memberCreateRequest));
        return savedMember.toMemberResponse();
    }

    public MemberResponse findOne(Long memberId) {
        Optional<Member> foundMember = memberRepository.findById(memberId);
        if (foundMember.isEmpty()) {
            throw new MemberDomainException(MemberDomainErrorCode.MEMBER_NOT_FOUND);
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
        memberRepository.deleteById(memberId);
    }
}
