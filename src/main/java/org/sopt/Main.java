package org.sopt;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.sopt.config.AppConfig;
import org.sopt.controller.MemberController;
import org.sopt.domain.Gender;
import org.sopt.domain.Member;
import org.sopt.exception.MemberErrorCode;
import org.sopt.exception.MemberException;

public class Main {
    public static void main(String[] args) {

        AppConfig appConfig = new AppConfig();
        MemberController memberController = appConfig.memberController();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n✨ --- DIVE SOPT 회원 관리 서비스 --- ✨");
            System.out.println("---------------------------------");
            System.out.println("1️⃣. 회원 등록 ➕");
            System.out.println("2️⃣. ID로 회원 조회 🔍");
            System.out.println("3️⃣. 전체 회원 조회 📋");
            System.out.println("4️⃣. ID로 회원 삭제 ❌");
            System.out.println("5️⃣. 종료 🚪");
            System.out.println("---------------------------------");
            System.out.print("메뉴를 선택하세요: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    try{
                        System.out.print("등록할 회원 이름을 입력하세요: ");
                        String name = scanner.nextLine();
                        if (name.trim().isEmpty()) {
                            throw new MemberException(MemberErrorCode.EMPTY_NAME);
                        }

                        System.out.println("등록할 회원 생년월일을 입력하세요 (예: 1990-01-01)");
                        String birthDateInput = scanner.nextLine();
                        if (birthDateInput.trim().isEmpty()) {
                            throw new MemberException(MemberErrorCode.EMPTY_BIRTHDATE);
                        }
                        LocalDate birthDate;
                        try {
                            birthDate = LocalDate.parse(birthDateInput);
                        } catch (Exception e) {
                            throw new MemberException(MemberErrorCode.INVALID_BIRTHDATE_FORMAT);
                        }

                        System.out.println("등록할 회원 이메일을 입력하세요");
                        String email = scanner.nextLine();
                        if (email.trim().isEmpty()) {
                            throw new MemberException(MemberErrorCode.EMPTY_EMAIL);
                        }
                        System.out.println("등록할 회원 성별을 입력하세요 (MALE/FEMALE/OTHER)");
                        Gender gender;
                        try {
                            gender = Gender.valueOf(scanner.nextLine().toUpperCase());
                        } catch (IllegalArgumentException e) {
                            throw new MemberException(MemberErrorCode.INVALID_GENDER_INPUT);
                        }
                        Long createdId = memberController.createMember(name, email, gender, birthDate);
                        if (createdId != null) {
                            System.out.println("✅ 회원 등록 완료 (ID: " + createdId + ")");
                        } else {
                            throw new MemberException(MemberErrorCode.MEMBER_REGISTRATION_FAILED);
                        }
                    }catch (MemberException e){
                        System.out.println(e.getMessage());
                        break;
                    }
                    break;
                case "2":
                    try {
                        System.out.print("조회할 회원 ID를 입력하세요: ");
                        try {
                            Long id = Long.parseLong(scanner.nextLine());
                            Optional<Member> foundMember = memberController.findMemberById(id);
                            if (foundMember.isPresent()) {
                                System.out.println(
                                        "✅ 조회된 회원: ID=" + foundMember.get().getId() + ", 이름=" + foundMember.get().getName()
                                                + ", 생년월일=" + foundMember.get().getBirthDate() + ", 이메일=" + foundMember.get()
                                                .getEmail() + ", 성별=" + foundMember.get().getGender().name());
                            } else {
                                throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
                            }
                        } catch (NumberFormatException e) {
                            throw new MemberException(MemberErrorCode.INVALID_ID_FORMAT);
                        }
                    }catch (MemberException e){
                        System.out.println(e.getMessage());
                        break;
                    }
                    break;
                case "3":
                    List<Member> allMembers = memberController.getAllMembers();
                    if (allMembers.isEmpty()) {
                        System.out.println("ℹ️ 등록된 회원이 없습니다.");
                    } else {
                        System.out.println("--- 📋 전체 회원 목록 📋 ---");
                        for (Member member : allMembers) {
                            System.out.println(
                                    "👤 ID=" + member.getId() + ", 이름=" + member.getName() + ", 생년월일="
                                            + member.getBirthDate() + ", 이메일="
                                            + member.getEmail() + ", 성별=" + member.getGender().name());
                        }
                        System.out.println("--------------------------");
                    }
                    break;
                case "4":
                    System.out.print("삭제할 회원 ID를 입력하세요: ");
                    try {
                        try {
                            Long idToDelete = Long.parseLong(scanner.nextLine());
                            memberController.deleteMemberById(idToDelete);
                            System.out.println("✅ 회원 삭제 완료 (ID: " + idToDelete + ")");
                        } catch (NumberFormatException e) {
                            throw new MemberException(MemberErrorCode.INVALID_ID_FORMAT);
                        }
                    } catch (MemberException e) {
                        System.out.println(e.getMessage());
                        break;
                    }
                    break;
                case "5":
                    System.out.println("👋 서비스를 종료합니다. 안녕히 계세요!");
                    scanner.close();
                    return;
                default:
                    System.out.println("🚫 잘못된 메뉴 선택입니다. 다시 시도해주세요.");
            }
        }
    }
}
