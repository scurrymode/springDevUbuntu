package com.sist.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sist.dao.BoardDAO;
import com.sist.dao.BoardVO;

@RestController
public class BoardController {
	@Autowired
	private BoardDAO dao;
	
	@RequestMapping("board/update_ok.do")
	public String board_update_ok(BoardVO vo){
		boolean bCheck=dao.boardUpdate(vo);
		String sendData="";
		if(bCheck==true){
			sendData="<script>location.href='content.do?no="+vo.getNo()+"';</script>";
		}else{
			sendData="<script>alert('비밀번호가 틀렸습니다.'); history.back();</script>";
		}
		return sendData;
	}
	
	@RequestMapping("board/delete_ok.do")
	public String board_delete_ok(int no, String pwd){
		boolean bCheck=dao.boardDelete(no, pwd);
		String sendData="";
		if(bCheck==true){
			sendData="<script>location.href='list.do';</script>";
		}else{
			sendData="<script>alert('비밀번호가 틀렸습니다.'); history.back();</script>";
		}
		return sendData;
	}
	

}
