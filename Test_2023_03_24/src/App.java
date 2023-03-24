import java.util.Scanner;

import controller.ArticleController;
import controller.Controller;
import controller.MemberController;

public class App {
	public void start() {
		System.out.println("==프로그램 시작==");
		Scanner sc = new Scanner(System.in);
		
		Controller controller;
		MemberController memberController = new MemberController(sc);
		ArticleController articleController = new ArticleController(sc, memberController);
		articleController.makeTestData();
		memberController.makeTestData();
		
		while(true) {
			System.out.print("명령어 > ");
			String cmd = sc.nextLine().trim();
			
			if (cmd.length() == 0) {
				System.out.println("명령어를 입력해주세요.");
				continue;
			}
			
			if (cmd.equals("exit")) {
				break;
			}
			
			String[] cmdDiv = cmd.split(" ");
			if (cmdDiv.length == 1) {
				System.out.println("명령어를 확인해주세요.(명령어 띄어쓰기)");
				continue;
			}
			
			String controllerName = cmdDiv[0];
			String actionName = cmdDiv[1];
			String strForLoginCheck = controllerName + "/" + actionName;
			
			if (controllerName.equals("article")) {
				controller = articleController;
			} else if (controllerName.equals("member")) {
				controller = memberController;
			} else {
				System.out.println("명령어를 확인해주세요.(article/member)");
				continue;
			}
			
			switch (strForLoginCheck) {
			case "article/write":
			case "article/modify":
			case "article/delete":
			case "member/logout":
				if (!Controller.isLogined()) {
					System.out.println("로그인 후 이용해주세요.");
					continue;
				}
				break;
			case "member/join":
			case "member/login":
				if (Controller.isLogined()) {
					System.out.println("로그아웃 후 이용해주세요.");
					continue;
				}
				break;
			}
			
			controller.doAction(actionName, cmd);
		}
		
		
		sc.close();
		System.out.println("==프로그램 종료==");
	}
}
