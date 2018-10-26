package kr.or.iei.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.member.model.service.MemberService;
import kr.or.iei.member.model.vo.Member;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. 인코딩 (한글이 있을 경우 처리하기 위함)
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		// 2. View에서 보낸 데이터를 변수에 저장
		// 로그인의 경우에는 ID와 PW
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		int popup = 0;
		
		if(request.getParameter("popup") != null) {
			popup = Integer.parseInt(request.getParameter("popup"));
		}
		
		// 3. 비즈니스 로직(Controller -> Service -> Dao -> DB)
		Member member = new MemberService().selectOneMember(userId, userPwd);

		// 4. 결과 출력
		// member가 null 이면 로그인 실패
		// null이 아니라면 로그인을 성공
		// 로그인 했을때 파일 입출력을 이용해서 로그를 남기기도 하고, db에 로그 테이블을 만들어 남기기도 함.
		if (member != null) {

			HttpSession session = request.getSession(true);
			// true : session 값을 없으면 새롭게 생성
			// false : session 값이 없으면 null 리턴
			System.out.println("발급된 session ID값 : " + session.getId());

			session.setAttribute("member", member);
			// SESSION에 많은 정보를 넣는것은 비추천, ID만 담아서 하는 방법 있음
			// session.setAttribute("userId", member.getUserId();

			if (popup != 1) {
				//popup으로 로그인 하지 않은 경우
				response.sendRedirect("/views/member/loginSuccess.jsp");
			} else {
				//popup으로 로그인 한 경우
				response.sendRedirect("/views/member/popupLoginSuccess.jsp");
			}
			
			// sendRedirect 메소드는 해당되는 페이지로 이동시키는 메소드

		} else {
			response.sendRedirect("/views/member/loginFail.jsp");
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
