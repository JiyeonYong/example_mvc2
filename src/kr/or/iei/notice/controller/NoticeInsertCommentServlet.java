package kr.or.iei.notice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.member.model.vo.Member;
import kr.or.iei.notice.model.service.NoticeService;

/**
 * Servlet implementation class NoticeInsertCommentServlet
 */
@WebServlet(name = "NoticeInsertComment", urlPatterns = { "/noticeInsertComment.do" })
public class NoticeInsertCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NoticeInsertCommentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		HttpSession session = request.getSession(false);

		String content = request.getParameter("comment");
		int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));

		try {
			String userId = ((Member) session.getAttribute("member")).getUserId();

			int result = new NoticeService().insertComment(noticeNo, content, userId);
			
			if(result > 0) {
				response.sendRedirect("/notice.do?noticeNo=" + noticeNo);
			} else {
				throw new Exception();
			}

		} catch (Exception e) {
			response.sendRedirect("/views/error/error.jsp");
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
