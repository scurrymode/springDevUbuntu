package com.sist.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController2 {
	@RequestMapping("temp/input_ok.do")
	public String temp_input_ok(String id){
		String data = "";
		if(id.equals("admin")){
			data="<script>"
				+"alert('로그인 성공했습니다.');"
				+"location.href='success.do';"
				+"</script>";
		}else{
			data="<script>"
					+"alert('로그인 실패했습니다.');"
					+"location.href='fail.do';"
					+"</script>";
		}
		return data;
	}
}
