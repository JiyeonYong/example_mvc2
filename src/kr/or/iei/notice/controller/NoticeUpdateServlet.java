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
 * Servlet implementation class NoticeUpdateServlet
 */
@WebServlet(name = "NoticeUpdate", urlPatterns = { "/noticeUpdate.do" })
public class NoticeUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NoticeUpdateServlet() {
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

		int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));
		String subject = request.getParameter("subject");
		String contents = request.getParameter("contents");
		String userId = null;

		System.out.println(noticeNo + subject + contents);

		// 해당글을 수정하기 위해 작성자를 확인하여 처리하도록 세션을 이용
		HttpSession session = request.getSession(false);

		try {

			userId = ((Member) session.getAttribute("member")).getUserId();

			if (userId != null) {
				int result = new NoticeService().noticeUpdate(noticeNo, subject, contents, userId);

				if (result > 0) {
					response.sendRedirect("/views/notice/updateSuccess.jsp?noticeNo=" + noticeNo);
				} else {
					response.sendRedirect("/veiws/notice/updateFail.jsp?noticeNo="+noticeNo);
				}

			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			// TODO: handle exception
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
