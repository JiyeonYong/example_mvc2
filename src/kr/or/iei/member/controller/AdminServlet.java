package kr.or.iei.member.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.member.model.service.MemberService;
import kr.or.iei.member.model.vo.Member;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet(name = "Admin", urlPatterns = { "/admin.do" })
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// admin 계정에 대한 검증
		HttpSession session = request.getSession(false);

		// 이 서블릿이 요청될 수 있는 상황
		// 1. admin이 호출한 경우 (정상적인 경우)
		// 2. 다른 계정이 호출한 경우 (비정상적인 경우)
		// 3. 로그인을 한적없는 계정이 호출 (비정상적인 경우)
		// 4. 로그인을 한번도 안한 계정이 호출 (비정상적인 경우) -> session 도 null인지 확인

		/*if (session != null) {

			Member admin = (Member) session.getAttribute("member");

			if (admin != null && admin.getUserId().equals("admin")) {

			} else {
				System.out.println("비정상 호출 : admin외 계정의 접근, 로그인 한적없는 계정의 접근");
			}

			// * null일때 Exception이 발생함
			// if(admin.getUserId().equals("admin") && admin != null)
			// && (and) -> 둘다 참이어야 최종적으로 참
			// 앞에것이 참이고 뒤에것이 참(앞에것은 무조건 참이어야함)

		} else {
			System.out.println("비정상 호출 : session null");
		}*/

		
		//아래와 같이 해도됨 
		try {
			Member admin = (Member) session.getAttribute("member");

			if (admin.getUserId().equals("admin")) {
				//정상적으로 admin 계정이 접근 했을 때
				//수업 속도상 회원관리 처리만 만들도록..
				
				//1. 비즈니스 로직을 통해 전체 회원 리스트를 가져오는 작업
				ArrayList<Member> list = new MemberService().memberAllList();
				
				//2. 결과 리턴
				
				if(!list.isEmpty()) {
					RequestDispatcher view = request.getRequestDispatcher("views/member/memberAllList.jsp");
					request.setAttribute("memberList", list);
					view.forward(request, response);
				}else {
					response.sendRedirect("/views/member/error.jsp");
				}
				
			} else {
				throw new Exception();
			}

		} catch (Exception e) {
//			response.sendRedirect("/views/member/exception.jsp");
			RequestDispatcher view = request.getRequestDispatcher("views/error/exception.jsp");
			view.forward(request, response);
		} 
		
		/*
		 * finally에서 파일입출력으로 로그 남기기
		 */
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
