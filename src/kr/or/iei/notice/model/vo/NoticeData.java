package kr.or.iei.notice.model.vo;

import java.util.ArrayList;

public class NoticeData {
	private ArrayList<Comment> list;
	private Notice notice;

	public NoticeData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NoticeData(ArrayList<Comment> list, Notice notice) {
		super();
		this.list = list;
		this.notice = notice;
	}

	public ArrayList<Comment> getList() {
		return list;
	}

	public void setList(ArrayList<Comment> list) {
		this.list = list;
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

}

