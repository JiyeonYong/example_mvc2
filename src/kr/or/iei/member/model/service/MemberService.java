package kr.or.iei.member.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.or.iei.common.*;
import kr.or.iei.member.model.dao.MemberDao;
import kr.or.iei.member.model.vo.Member;

public class MemberService {
	public Member selectOneMember(String userId, String userPwd) {
		Connection conn = JDBCTemplate.getConnection();
		Member member = new MemberDao().selectOneMember(userId, userPwd, conn);
		
		JDBCTemplate.Close(conn);
		
		return member;
	}

	public int insertMember(Member m) {
		Connection conn = JDBCTemplate.getConnection();
		int result = new MemberDao().insertMember(m, conn);
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.Close(conn);
		
		return result;
	}
	
	public boolean checkId(String checkId) {
		Connection conn = JDBCTemplate.getConnection();
		String userId = new MemberDao().checkId(checkId, conn);
		JDBCTemplate.Close(conn);
		
		if(userId == null) {
			//해당 아이디를 사용하는 사용자가 없음
			return false;
		}else {
			//해당 아이디를 사용하는 사용자가 있음 
			return true;
		}
		
	}

	public int updateMember(Member m) {
		Connection conn = JDBCTemplate.getConnection();
		int result = new MemberDao().updateMember(m, conn);
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.Close(conn);
		
		return result;
	}

	public int deleteMember(String userId) {
		Connection conn = JDBCTemplate.getConnection();
		int result = new MemberDao().deleteMember(userId, conn);
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.Close(conn);
		
		return result;
	}

	public ArrayList<Member> memberAllList() {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Member> list = new MemberDao().memberAllList(conn);
		
		JDBCTemplate.Close(conn);
		
		return list;
		
	}
}
