package org.sopt.config;

import org.sopt.controller.MemberController;
import org.sopt.repository.FileMemberRepository;
import org.sopt.repository.MemberRepository;
import org.sopt.service.MemberService;
import org.sopt.service.MemberServiceImpl;

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
