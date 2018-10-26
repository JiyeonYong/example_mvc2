package kr.or.iei.notice.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.notice.model.vo.Comment;
import kr.or.iei.notice.model.vo.Notice;

public class NoticeDao {

	public ArrayList<Notice> noticeAllList(Connection conn) {
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<Notice> list = new ArrayList<Notice>();

		String query = "select * from notice";

		// notice_no number primary key,
		// subject char(100) not null,
		// contents varchar2(4000) not null,
		// user_id varchar2(20) not null,
		// reg_date date,

		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);

			while (rset.next()) {
				Notice notice = new Notice();

				notice.setNoticeNo(rset.getInt("notice_no"));
				notice.setSubject(rset.getString("subject"));
				notice.setContents(rset.getString("contents"));
				notice.setUserId(rset.getString("user_id"));
				notice.setRegDate(rset.getDate("reg_date"));

				list.add(notice);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.Close(rset);
			JDBCTemplate.Close(stmt);
		}

		return list;

	}

	public Notice selectOneNotice(int noticeNo, Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Notice notice = null;

		String query = "select * from notice where notice_no=?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, noticeNo);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				notice = new Notice();

				notice.setNoticeNo(rset.getInt("notice_no"));
				notice.setSubject(rset.getString("subject"));
				notice.setContents(rset.getString("contents"));
				notice.setUserId(rset.getString("user_id"));
				notice.setRegDate(rset.getDate("reg_date"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.Close(rset);
			JDBCTemplate.Close(pstmt);
		}

		return notice;
	}

	public int noticeUpdate(int noticeNo, String subject, String contents, String userId, Connection conn) {
		PreparedStatement pstmt = null;
		int result = 0;

		String query = "update notice set subject=?, contents=? where notice_no =? and user_id =?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, subject);
			pstmt.setString(2, contents);
			pstmt.setInt(3, noticeNo);
			pstmt.setString(4, userId);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.Close(pstmt);
		}

		return result;
	}

	public int noticeDelete(int noticeNo, String userId, Connection conn) {

		PreparedStatement pstmt = null;
		int result = 0;

		String query = "delete from notice where notice_no=? and user_id=?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			pstmt.setString(2, userId);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.Close(pstmt);
		}

		return result;
	}

	public ArrayList<Notice> getCurrentPage(int currentPage, int recordCountPerPage, Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Notice notice = null;
		ArrayList<Notice> list = new ArrayList<Notice>();

		// 시작 게시물 계산
		// 요청한 페이지가 1페이지라면? 1*10-(10-1) = 1 : 1페이지의 시작 게시물은 1번
		// 요청한 페이지가 4페이지라면? 4*10-(10-1) = 31 : 4 페이지의 시작 게시물은 31번
		int start = currentPage * recordCountPerPage - (recordCountPerPage - 1);

		// 끝 게시물 계산
		// 요청한 페이지가 1페이지라면? 1*10 = 10 : 1페이지의 끝 게시물은 10번
		int end = currentPage * recordCountPerPage;

		String query = "select * from (select notice.*, row_number() over(order by notice_no desc) as rnum from notice) where rnum between ? and ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				notice = new Notice();
				notice.setNoticeNo(rset.getInt("notice_no"));
				notice.setSubject(rset.getString("subject"));
				notice.setContents(rset.getString("contents"));
				notice.setUserId(rset.getString("user_id"));
				notice.setRegDate(rset.getDate("reg_date"));

				list.add(notice);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.Close(rset);
			JDBCTemplate.Close(pstmt);
		}

		return list;

	}

	public String getPageNavi(int currentPage, int recordCountPerPage, int naviCountPerPage, Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		// 게시물의 토탈 개수를 구해야 함
		// 초기값은 정보가 없으므로 0으로 세팅
		int recordTotalCount = 0;

		String query = "select count(*) as totalcount from notice";

		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				recordTotalCount = rset.getInt("totalcount");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.Close(rset);
			JDBCTemplate.Close(pstmt);
		}

		// 구해온 게시물의 토탈 개수를 바탕으로 페이지의 토탈 개수를 구해양함
		// 게시물이 124개 라면? page는 13개
		int pageTotalCount = 0; // 정보가 없으므로 초기값은 0 세팅

		if (recordTotalCount % recordCountPerPage != 0) {
			pageTotalCount = (recordTotalCount / recordCountPerPage) + 1;
		} else {
			pageTotalCount = recordTotalCount / recordCountPerPage;
		}

		// 에러방지 코드
		if (currentPage < 1) {
			currentPage = 1;
		} else if (currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}

		// 현재 페이지를 기점으로 시작 navi와 끝 navi를 만들어야함
		// 현재 페이지가 1이라면? 1 2 3 4 5
		// 현재 페이지가 3이라면? 1 2 3 4 5
		// 현재 페이지가 7이라면? 6 7 8 9 10

		// 시작페이지 구하는 공식
		// ((현재페이지-1)/리스트개수)*리스트개수+1
		// 현재 페이지가 1이라면? ((1-1)/5)*5+1 = 1
		// 현재 페이지가 3이라면? ((3-1)/5)*5+1 = 1
		// 현재 페이지가 7이라면? ((7-1)/5)*5+1 = 6
		int startNavi = ((currentPage - 1) / naviCountPerPage) * naviCountPerPage + 1;

		// 끝페이지 구하는 공식
		// 시작navi + 보여줄navi - 1;
		// 시작 navi가 11이라면? 11+5-1 = 15 -> 11 12 13 14 15
		int endNavi = startNavi + naviCountPerPage - 1;

		// 끝 navi를 구할 때 주의해야할 점
		// 토탈 개수가 122개라고 할 때 총 토탚 페이지는 13개
		// 이때 navi는 (1 2 3 4 5) (6 7 8 9 10) (11 12 13 14 15)로 생성됨
		// 토탈페이지를 고려하지 않고 만들게 되면 끝 navi가 이상하게 만들어짐
		if (endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}

		// 페이지를 표현하는 navi에서 사용할 '<' 모양과 '>'모양을 쓰기위해
		// 필요한 변수 2개를 생성 (변수의 값에 다라가 시작 부분과 끝부분은 표현하지 않기 위해)

		boolean needPrev = true;
		boolean needNext = true;

		if (startNavi == 1) {
			needPrev = false;
		}

		if (endNavi == pageTotalCount) {
			needNext = false;
		}

		StringBuilder sb = new StringBuilder();

		// 시작 페이지가 1페이지가 아니라면
		// needPrev는 시작페이지가 1이면 false, 시작페이지가 1이 아니라면 true
		// 현재 내 위치가 (startNavi)가 2라면? '<'버튼을 누르면 1페이지로 이동해야함
		if (needPrev == true) {
			// sb.append("<a href = '/noticeList.do?currentPage=" + (startNavi - 1) + "'> <
			// </a>");
			sb.append("<li class = 'page-item'><a class = 'page-link' href = '/noticeList.do?currentPage="
					+ (startNavi - 1) + "'> Preview </a><li>");
		}

		for (int i = startNavi; i <= endNavi; i++) {
			if (i == currentPage) {
				// sb.append("<a class = 'page-link' href = '/noticeList.do?currentPage=" + i +
				// "'><b>" + i + "</b></a>");
				sb.append("<li class = 'page-item active'><a class = 'page-link' href = '/noticeList.do?currentPage="
						+ i + "'>" + i + "</a><li>");
				// 현재페이지는 굵게 표시
				// <a href = '/hoticeList.do?currentPage=1'><B>1</B></a>
			} else {
				// sb.append("<a class = 'page-link' href = '/noticeList.do?currentPage=" + i +
				// "'>" + i + "</a>");
				sb.append("<li class = 'page-item'><a class = 'page-link' href = '/noticeList.do?currentPage=" + i
						+ "'>" + i + "</a><li>");
				// 현재페이지가 내가 있는 위치와 다르다면 일반 표시
			}
		}

		if (needNext == true) {
			// sb.append("<a href = '/noticeList.do?currentPage=" + (endNavi + 1) + "'> >
			// </a>");
			sb.append("<li class = 'page-item'><a class = 'page-link' href = '/noticeList.do?currentPage="
					+ (endNavi + 1) + "'> Next </a><li>");
		}

		return sb.toString();
	}

	public int insertNotice(String subject, String contents, String userId, Connection conn) {
		PreparedStatement pstmt = null;
		int result = 0;

		String query = "insert into notice values(noticeNo.NEXTVAL,?,?,?,SYSDATE)";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, subject);
			pstmt.setString(2, contents);
			pstmt.setString(3, userId);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.Close(pstmt);
		}

		return result;
	}

	public ArrayList<Notice> getSearchCurrentPage(int currentPage, int recordCountPerPage, String keyword,
			Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Notice notice = null;
		ArrayList<Notice> list = new ArrayList<Notice>();

		int start = currentPage * recordCountPerPage - (recordCountPerPage - 1);
		int end = currentPage * recordCountPerPage;

		String query = "select * from (select notice.*, row_number() over(order by notice_no desc) as rnum from notice "
				+ "where subject like ?) where rnum between ? and ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, '%' + keyword + '%');
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				notice = new Notice();
				notice.setNoticeNo(rset.getInt("notice_no"));
				notice.setSubject(rset.getString("subject"));
				notice.setContents(rset.getString("contents"));
				notice.setUserId(rset.getString("user_id"));
				notice.setRegDate(rset.getDate("reg_date"));

				list.add(notice);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.Close(rset);
			JDBCTemplate.Close(pstmt);
		}

		return list;
	}

	public String getSearchPageNavi(int currentPage, int recordCountPerPage, int naviCountPerPage, String keyword,
			Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int recordTotalCount = 0;

		String query = "select count(*) as totalcount from notice where subject like ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, '%' + keyword + '%');
			rset = pstmt.executeQuery();

			if (rset.next()) {
				recordTotalCount = rset.getInt("totalcount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.Close(rset);
			JDBCTemplate.Close(pstmt);
		}

		int pageTotalCount = 0;

		if (recordTotalCount % recordCountPerPage != 0) {
			pageTotalCount = (recordTotalCount / recordCountPerPage) + 1;
		} else {
			pageTotalCount = recordTotalCount / recordCountPerPage;
		}

		if (currentPage < 1) {
			currentPage = 1;
		} else if (currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}

		int startNavi = ((currentPage - 1) / naviCountPerPage) * naviCountPerPage + 1;

		int endNavi = startNavi + naviCountPerPage - 1;

		if (endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}

		boolean needPrev = true;
		boolean needNext = true;

		if (startNavi == 1) {
			needPrev = false;
		}

		if (endNavi == pageTotalCount) {
			needNext = false;
		}

		StringBuilder sb = new StringBuilder();

		if (needPrev == true) {
			sb.append("<li class = 'page-item'><a class = 'page-link' href = '/noticeSearch.do?search=" + keyword + "&currentPage="
					+ (startNavi - 1) + "'> Preview </a><li>");
		}

		for (int i = startNavi; i <= endNavi; i++) {
			if (i == currentPage) {
				sb.append("<li class = 'page-item active'><a class = 'page-link' href = '/noticeSearch.do?search=" + keyword + "&currentPage="
						+ i + "'>" + i + "</a><li>");
			} else {
				sb.append("<li class = 'page-item'><a class = 'page-link' href = '/noticeSearch.do?search=" + keyword + "&currentPage=" + i
						+ "'>" + i + "</a><li>");
			}
		}

		if (needNext == true) {
			sb.append("<li class = 'page-item'><a class = 'page-link' href = '/noticeSearch.do?search=" + keyword + "&currentPage="
					+ (endNavi + 1) + "'> Next </a><li>");
		}

		return sb.toString();
	}

	public ArrayList<Comment> selectComments(int noticeNo, Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset= null;
		Comment comment = null;
		ArrayList<Comment> list = new ArrayList<Comment>();
		
		String query = "select * from NOTICECOMMENT where notice_no = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				comment = new Comment();
				
				comment.setCommentNo(rset.getInt("comment_no"));
				comment.setNoticeNo(rset.getInt("notice_no"));
				comment.setContent(rset.getString("content"));
				comment.setUserId(rset.getString("user_id"));
				comment.setRegdate(rset.getDate("regdate"));
				
				list.add(comment);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.Close(rset);
			JDBCTemplate.Close(pstmt);
		}
		return list;
	}

	public int insertComment(int noticeNo, String content, String userId, Connection conn) {
		PreparedStatement pstmt = null;
		int result = 0;

		String query = "insert into noticecomment values(SEQ_noticecomment.NEXTVAL,?,?,?,SYSDATE)";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			pstmt.setString(2, content);
			pstmt.setString(3, userId);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.Close(pstmt);
		}

		return result;
	}

}
