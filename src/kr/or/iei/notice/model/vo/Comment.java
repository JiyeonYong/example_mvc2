package kr.or.iei.notice.model.vo;

import java.sql.Date;

public class Comment {
	private int commentNo;
	private int noticeNo;
	private String content;
	private String userId;
	private Date regdate;

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Comment(int commentNo, int noticeNo, String content, String userId, Date regdate) {
		super();
		this.commentNo = commentNo;
		this.noticeNo = noticeNo;
		this.content = content;
		this.userId = userId;
		this.regdate = regdate;
	}

	public int getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}

	public int getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

}
