package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dto.Article;
import dto.Member;

public class ArticleController extends Controller {
	int lastId = 3;
	Scanner sc;
	MemberController memberController;
	List<Article> articles = new ArrayList<>();

	public ArticleController(Scanner sc, MemberController memberController) {
		this.sc = sc;
		this.memberController = memberController;
	}

	@Override
	public void doAction(String actionName, String cmd) {
		switch (actionName) {
		case "write":
			doWrite();
			break;
		case "list":
			showList();
			break;
		case "detail":
			showDetail(cmd);
			break;
		case "modify":
			doModify(cmd);
			break;
		case "delete":
			doDelete(cmd);
			break;
		default:
			System.out.println("명령어를 확인해주세요.(article 이후)");
		}
	}

	private void doWrite() {
		System.out.println("제목: ");
		String title = sc.nextLine();
		System.out.println("내용: ");
		String body = sc.nextLine();
		
		articles.add(new Article(++lastId, now, now, loginedMember.id, title, body, 0));
		System.out.println(lastId + "번 글이 생성되었습니다.");
	}

	private void showList() {
		Article article = null;
		List<Member> members = memberController.members;
		String writerName = null;
		System.out.println(" 번호  //  제목  //  조회  //  작성자 ");
		
		for (int i = articles.size() - 1; i >= 0; i-- ) {
			article = articles.get(i);
			for (Member member : members) {
				if (member.id == article.memberId) {
					writerName = member.name;
					break;
				} else {
					writerName = "알 수 없음";
				}
			}
			System.out.printf(" %d  //  %s  //  %d  //  %s  \n", article.id, article.title, article.hit, writerName);
		}
	}

	private void showDetail(String cmd) {
		String[] cmdDiv = cmd.split(" ");
		if (cmdDiv.length !=3) {
			System.out.println("명령어를 확인해요세요.(명령어 띄어쓰기)");
			return;
		}
		
		int id = Integer.parseInt(cmdDiv[2]);
		int index = getIndexById(id);
		Article article = articles.get(index);
		
		if (index == -1) {
			System.out.println(id + "번 글은 존재하지 않습니다.");
			return;
		}
		
		List<Member> members = memberController.members;
		String writerName = null;
		
		for (Member member : members) {
			if (member.id == article.memberId) {
				writerName = member.name;
				break;
			} else {
				writerName = "알 수 없음";
			}
		}
		
		System.out.printf("번호: %d\n", article.id);
		System.out.printf("작성날짜: %s\n", article.regDate);
		System.out.printf("수정날짜: %s\n", article.updateDate);
		System.out.printf("작성자: %s\n", writerName);
		System.out.printf("제목: %s\n", article.title);
		System.out.printf("내용: %s\n", article.body);
		System.out.printf("조회: %d\n", article.hit);
		
	}

	private void doModify(String cmd) {
		String[] cmdDiv = cmd.split(" ");
		if (cmdDiv.length !=3) {
			System.out.println("명령어를 확인해요세요.(명령어 띄어쓰기)");
			return;
		}
		
		int id = Integer.parseInt(cmdDiv[2]);
		int index = getIndexById(id);
		Article article = articles.get(index);
		
		if (index == -1) {
			System.out.println(id + "번 글은 존재하지 않습니다.");
			return;
		}
		
		if (article.memberId != loginedMember.id) {
			System.out.println("권한이 없습니다.");
			return;
		}
		
		System.out.print("새 제목: ");
		String title = sc.nextLine();
		System.out.print("새 내용: ");
		String body = sc.nextLine();
		
		article.title = title;
		article.body = body;
		System.out.println(id + "번 글을 수정했습니다.");
	}

	private void doDelete(String cmd) {
		String[] cmdDiv = cmd.split(" ");
		if (cmdDiv.length !=3) {
			System.out.println("명령어를 확인해요세요.(명령어 띄어쓰기)");
			return;
		}
		
		int id = Integer.parseInt(cmdDiv[2]);
		int index = getIndexById(id);
		Article article = articles.get(index);
		
		if (index == -1) {
			System.out.println(id + "번 글은 존재하지 않습니다.");
			return;
		}
		
		if (article.memberId != loginedMember.id) {
			System.out.println("권한이 없습니다.");
			return;
		}
		
		articles.remove(index);
		System.out.println(id + "번 글을 삭제했습니다.");
	}

	private int getIndexById(int id) {
		int i = 0;
		for (Article article : articles) {
			if (id == article.id) {
				return i;
			}
			i++;
		}
		return -1;
	}

	@Override
	public void makeTestData() {
		System.out.println("테스트를 위한 게시글 데이터를 생성합니다.");
		articles.add(new Article(1, now, now, 2, "제목1", "내용1", 11));
		articles.add(new Article(2, now, now, 3, "제목2", "내용2", 22));
		articles.add(new Article(3, now, now, 3, "제목3", "내용3", 33));	
	}

}
