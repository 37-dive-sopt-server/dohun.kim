package org.sopt.global.config;

import org.sopt.member.controller.MemberController;
import org.sopt.member.repository.FileMemberRepository;
import org.sopt.member.repository.MemberRepository;
import org.sopt.member.service.MemberService;
import org.sopt.member.service.MemberServiceImpl;

public class AppConfig {

    private MemberRepository memberRepository;
    private MemberService memberService;
    private MemberController memberController;

    public MemberRepository memberRepository() {
        if (memberRepository == null) {
            memberRepository = new FileMemberRepository();
        }
        return memberRepository;
    }

    public MemberService memberService() {
        if (memberService == null) {
            memberService = new MemberServiceImpl(memberRepository());
        }
        return memberService;
    }

    public MemberController memberController() {
        if (memberController == null) {
            memberController = new MemberController(memberService());
        }
        return memberController;
    }
}
