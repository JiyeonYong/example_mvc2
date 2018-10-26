package kr.or.iei.notice.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.notice.model.dao.NoticeDao;
import kr.or.iei.notice.model.vo.Comment;
import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.notice.model.vo.NoticeData;
import kr.or.iei.notice.model.vo.PageData;

public class NoticeService {

	/*
	 * public ArrayList<Notice> noticeAllList() { Connection conn =
	 * JDBCTemplate.getConnection();
	 * 
	 * ArrayList<Notice> list = new NoticeDao().noticeAllList(conn);
	 * 
	 * JDBCTemplate.Close(conn);
	 * 
	 * return list; }
	 */

	public PageData noticeAllList(int currentPage) {
		Connection conn = JDBCTemplate.getConnection();

		// 2개의 값을 저장하는 변수 생성(게시물의 개수, navi의 개수)
		int recordCountPerPage = 10;
		int naviCountPerPage = 5;

		// Service에서 dao를 호출(2번)
		// 1. 현재 페이지의 게시물 리스트 요청
		// 2. 현재 페이지를 중심으로 만들어지는 네비 리스트 요청
		ArrayList<Notice> list = new NoticeDao().getCurrentPage(currentPage, recordCountPerPage, conn);

		/*
		 * for(Notice n : list) { System.out.println(n.getSubject()); }
		 */

		String pageNavi = new NoticeDao().getPageNavi(currentPage, recordCountPerPage, naviCountPerPage, conn);

		PageData pd = null;

		if (!list.isEmpty() && !pageNavi.isEmpty()) {
			pd = new PageData(list, pageNavi);
		}

		JDBCTemplate.Close(conn);

		return pd;
	}

	public NoticeData selectOneNotice(int noticeNo) {
		Connection conn = JDBCTemplate.getConnection();
		// 하나의 공지사항을 읽으면 해당 공지사항의 내용과 댓글을 가져와야함

		// 공지사항 가져오기
		Notice notice = new NoticeDao().selectOneNotice(noticeNo, conn);

		// 댓글 가져오기
		ArrayList<Comment> list = new NoticeDao().selectComments(noticeNo, conn);

		NoticeData nd = null;

		if (notice != null) {
			nd = new NoticeData(list, notice);
		}

		JDBCTemplate.Close(conn);

		return nd;
	}

	public int noticeUpdate(int noticeNo, String subject, String contents, String userId) {
		// TODO Auto-generated method stub

		Connection conn = JDBCTemplate.getConnection();

		int result = new NoticeDao().noticeUpdate(noticeNo, subject, contents, userId, conn);

		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}

		JDBCTemplate.Close(conn);

		return result;

	}

	public int noticeDelete(int noticeNo, String userId) {
		// TODO Auto-generated method stub

		Connection conn = JDBCTemplate.getConnection();

		int result = new NoticeDao().noticeDelete(noticeNo, userId, conn);

		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}

		JDBCTemplate.Close(conn);

		return result;

	}

	public int insertNotice(String subject, String contents, String userId) {
		Connection conn = JDBCTemplate.getConnection();

		int result = new NoticeDao().insertNotice(subject, contents, userId, conn);

		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}

		JDBCTemplate.Close(conn);

		return result;
	}

	public PageData searchList(String keyword, int currentPage) {

		Connection conn = JDBCTemplate.getConnection();

		int recordCountPerPage = 10;
		int naviCountPerPage = 5;

		ArrayList<Notice> list = new NoticeDao().getSearchCurrentPage(currentPage, recordCountPerPage, keyword, conn);

		for (Notice n : list) {
			System.out.println(n.getSubject());
		}

		String pageNavi = new NoticeDao().getSearchPageNavi(currentPage, recordCountPerPage, naviCountPerPage, keyword,
				conn);

		PageData pd = null;

		if (!list.isEmpty() && !pageNavi.isEmpty()) {
			pd = new PageData(list, pageNavi);
		}

		JDBCTemplate.Close(conn);

		return pd;

	}

	public int insertComment(int noticeNo, String content, String userId) {
		Connection conn = JDBCTemplate.getConnection();

		int result = new NoticeDao().insertComment(noticeNo, content, userId, conn);

		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}

		JDBCTemplate.Close(conn);

		return result;
	}

}
