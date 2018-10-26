package kr.or.iei.member.controller;

import java.io.IOException;

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
 * Servlet implementation class MyInfoServlet
 */
@WebServlet(name = "MyInfo", urlPatterns = { "/myInfo.do" })
public class MyInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyInfoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. 세션을 가지고 해당 사용자 ID값 추출
		HttpSession session = request.getSession(false);
		Member m = (Member) session.getAttribute("member");
		String userId = m.getUserId();
		String userPwd = m.getUserPwd();

		// 2. 비즈니스 로직
		Member member = new MemberService().selectOneMember(userId, userPwd);
		System.out.println(member.getUserName());

		// 3. 결과 출력 및 리턴
		if (member != null) {
			RequestDispatcher view = request.getRequestDispatcher("views/member/memberMyInfo.jsp");
			//getRequestDispatcher 메소드는 상대 경로만 사용 가능하므로 '/'로 시작하지 않음
			//('/'부터 시작하면 절대경로)
			//해당되는 경로로 request객체와 response 객체를 가지고 이동
			
			request.setAttribute("member", member);
			
			view.forward(request, response);
			
		} else {
			response.sendRedirect("/views/member/error.jsp");
		}

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
