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
 * Servlet implementation class InfoUpdateServlet
 */
@WebServlet(name = "InfoUpdate", urlPatterns = { "/infoUpdate.do" })
public class InfoUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InfoUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		String userId = ((Member)session.getAttribute("member")).getUserId();
		String userPwd = request.getParameter("userPwd");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String hobby = request.getParameter("hobby");
		
		Member m = new Member();
		m.setUserId(userId);
		m.setUserPwd(userPwd);
		m.setPhone(phone);
		m.setEmail(email);
		m.setAddress(address);
		m.setHobby(hobby);
		
		int result = new MemberService().updateMember(m);
		
		
		//세션 정보 업데이트를 위해 정보 가져오기
		Member member = new MemberService().selectOneMember(userId, userPwd);
		session.setAttribute("member", member);
		
		if(result > 0) {
			response.sendRedirect("/views/member/updateSuccess.jsp");
		}else {
			response.sendRedirect("/views/member/error.jsp");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
