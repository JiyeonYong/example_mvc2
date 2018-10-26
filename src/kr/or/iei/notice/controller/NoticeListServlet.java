package kr.or.iei.notice.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.notice.model.service.NoticeService;
import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.notice.model.vo.PageData;

/**
 * Servlet implementation class NoticeListServlet
 */
@WebServlet(name = "NoticeList", urlPatterns = { "/noticeList.do" })
public class NoticeListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NoticeListServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * ArrayList<Notice> list = new NoticeService().noticeAllList();
		 * 
		 * if(!list.isEmpty()) { RequestDispatcher view =
		 * request.getRequestDispatcher("views/notice/noticeAllList.jsp");
		 * request.setAttribute("noticeList", list); view.forward(request, response);
		 * }else { response.sendRedirect("/views/error/error.jsp"); }
		 */

		// 페이징 처리를 위하여 페이지를 저장하는 변수 생성

		int currentPage; // 현재 페이지를 저장하는 변수

		// 처음 게시판에 접근하였을 땐 무조건 1페이지로 처리하고
		// 게시판에서 페이지를 이동할때는 값이 있기 때문에 해당 페이지 값을 가져와서 저장
		if (request.getParameter("currentPage") == null) {
			currentPage = 1;
		} else {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}

		// 2. 비즈니스 로직
		// Controller -> Service -> Dao
		// 페이징 처리에서는 2개의 값을 db에서 가져와야함
		// Controller -> Service -> Dao 2개호출함
		// pd에는 게시물 리스트, 네비 가 들어있음
		PageData pd = new NoticeService().noticeAllList(currentPage);

		// 3. 결과 값 view페이지로 리턴
		if (pd != null) {
			RequestDispatcher view = request.getRequestDispatcher("views/notice/noticeList.jsp");
			request.setAttribute("pageData", pd);
			view.forward(request, response);
		} else {
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
