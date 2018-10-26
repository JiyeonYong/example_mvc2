package kr.or.iei.notice.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.notice.model.service.NoticeService;
import kr.or.iei.notice.model.vo.PageData;

/**
 * Servlet implementation class NoticeSearchServlet
 */
@WebServlet(name = "NoticeSearch", urlPatterns = { "/noticeSearch.do" })
public class NoticeSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("Utf-8");
		
		String keyword = request.getParameter("search");
		
		//페이징 처리
		int currentPage;
		if(request.getParameter("currentPage") == null) {
			currentPage = 1;
		}else {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		PageData pd = new NoticeService().searchList(keyword, currentPage);
		
		/*if(pd != null) {*/
			RequestDispatcher view = request.getRequestDispatcher("views/notice/noticeSearch.jsp");
			request.setAttribute("keyword", keyword);
			request.setAttribute("pageData", pd);
			view.forward(request, response);
		/*}else {
			response.sendRedirect("/views/error/error.jsp");
		}*/
	}          

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
