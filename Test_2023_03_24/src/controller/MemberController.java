package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dto.Member;

public class MemberController extends Controller {
	int lastId = 3;
	Scanner sc;
	List<Member> members = new ArrayList<>();

	public MemberController(Scanner sc) {
		this.sc = sc;
	}

	@Override
	public void doAction(String actionName, String cmd) {
		switch (actionName) {
		case "join":
			doJoin();
			break;
		case "login":
			doLogin();
			break;
		case "logout":
			doLogout();
			break;
		default:
			System.out.println("명령어를 확인해주세요.(member 이후)");
		}

	}

	private void doJoin() {
		String loginId;
		String loginPw;
		
		while (true) {
			System.out.print("로그인 아이디: ");
			loginId = sc.nextLine();
			if(isLoginIdDup(loginId)) {
				System.out.println("이미 사용중인 아이디입니다.");
				continue;
			}
			break;
		}
		
		while (true) {
			System.out.print("로그인 비밀번호: ");
			loginPw = sc.nextLine();
			System.out.print("로그인 비밀번호 확인: ");
			String loginPwConfirm = sc.nextLine();
			if (!loginPw.equals(loginPwConfirm)) {
				System.out.println("비밀번호를 확인해주세요.");
				continue;
			}
			break;
		}
		System.out.print("이름: ");
		String name = sc.nextLine();
		
		members.add(new Member(++lastId, now, loginId, loginPw, name));
		System.out.println(lastId + "번 회원이 가입되었습니다");
	}



	private void doLogin() {
		String loginId;
		String loginPw;
		Member member;
		
		while (true) {
			System.out.print("로그인 아이디: ");
			loginId = sc.nextLine();
			
			member = getMemberByLoginId(loginId);

			System.out.print("로그인 비밀번호: ");
			loginPw = sc.nextLine();
			
			if (member == null) {
				System.out.println("일치하는 회원이 없습니다.");
				return;
			}
			if (!member.loginPw.equals(loginPw)) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				return;
			}
			break;
		}
		
		loginedMember = member;
		System.out.printf("로그인 성공! %s님 반갑습니다.\n", member.name);
	}

	private void doLogout() {
		loginedMember = null;
		System.out.println("로그아웃 되었습니다.");
	}
	
	private int getIndexByLoginId(String loginId) {
		int i = 0;
		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	private Member getMemberByLoginId(String loginId) {
		int index = getIndexByLoginId(loginId);
		if (index == -1) {
			return null;
		}
		return members.get(index);
	}
	
	private boolean isLoginIdDup(String loginId) {
		int index = getIndexByLoginId(loginId);
		if (index == -1 ) {
			return false;
		}
		return true;
	}

	@Override
	public void makeTestData() {
		System.out.println("테스트를 위한 회원 데이터를 생성합니다.");
		members.add(new Member(1, now, "test1", "test1", "김철수"));
		members.add(new Member(2, now, "test2", "test2", "홍길동"));
		members.add(new Member(3, now, "test3", "test3", "김영희"));
	}

}
