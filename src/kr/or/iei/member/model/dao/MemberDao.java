package kr.or.iei.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.member.model.vo.Member;

public class MemberDao {

	public Member selectOneMember(String userId, String userPwd, Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member member = null;

		String query = "select * from member where user_id =? and user_pwd=? and active ='Y'";

		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				member = new Member();
				member.setUserId(rset.getString("user_id"));
				member.setUserPwd(rset.getString("user_pwd"));
				member.setUserName(rset.getString("user_name"));
				member.setAge(rset.getInt("age"));
				member.setEmail(rset.getString("email"));
				member.setPhone(rset.getString("phone"));
				member.setAddress(rset.getString("address"));
				member.setGender(rset.getString("gender").charAt(0));
				member.setHobby(rset.getString("hobby"));
				member.setActive(rset.getString("active").charAt(0));
				member.setEnrollDate(rset.getDate("enrollDate"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.Close(rset);
			JDBCTemplate.Close(pstmt);
		}
		return member;
	}

	public int insertMember(Member m, Connection conn) {
		PreparedStatement pstmt = null;
		int result = 0;

		String query = "insert into member values(?,?,?,?,?,?,?,?,?,?,SYSDATE)";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setInt(4, m.getAge());
			pstmt.setString(5, m.getEmail());
			pstmt.setString(6, m.getPhone());
			pstmt.setString(7, m.getAddress());
			pstmt.setString(8, String.valueOf(m.getGender()));
			pstmt.setString(9, m.getHobby());
			pstmt.setString(10, String.valueOf(m.getActive()));

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.Close(pstmt);
		}

		return result;
	}

	public String checkId(String checkId, Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String userId = null;

		String query = "select user_id from member where user_id = ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, checkId);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				userId = rset.getString("user_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.Close(rset);
			JDBCTemplate.Close(pstmt);
		}
		return userId;
	}

	public int updateMember(Member m, Connection conn) {
		PreparedStatement pstmt = null;
		int result = 0;

		String query = "update member set user_pwd =?, phone=?, email=?, address=?, hobby=?" + "where user_id =?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, m.getUserPwd());
			pstmt.setString(2, m.getPhone());
			pstmt.setString(3, m.getEmail());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getHobby());
			pstmt.setString(6, m.getUserId());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.Close(pstmt);
		}

		return result;
	}

	public int deleteMember(String userId, Connection conn) {
		PreparedStatement pstmt = null;
		int result = 0;

		String query = "update member set active=? where user_id=?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "N");
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

	public ArrayList<Member> memberAllList(Connection conn) {
		//여러명을 처리하기위한 컬렉션을 사용(ArrayList)
		
		ArrayList<Member> list = new ArrayList<Member>();
		Statement stmt = null;
		ResultSet rset = null;
		
		String query = "select * from member order by active desc";
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			while(rset.next()) {
				Member m = new Member();
				m.setUserId(rset.getString("user_id"));
				m.setUserPwd(rset.getString("user_pwd"));
				m.setUserName(rset.getString("user_name"));
				m.setAge(rset.getInt("age"));
				m.setEmail(rset.getString("email"));
				m.setPhone(rset.getString("phone"));
				m.setAddress(rset.getString("address"));
				m.setGender(rset.getString("gender").charAt(0));
				m.setHobby(rset.getString("hobby"));
				m.setActive(rset.getString("active").charAt(0));
				m.setEnrollDate(rset.getDate("enrollDate"));
				list.add(m);
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
}
