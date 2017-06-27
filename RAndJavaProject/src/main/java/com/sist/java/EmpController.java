package com.sist.java;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sist.dao.DeptVO;
import com.sist.dao.EmpDAO;
import com.sist.dao.EmpVO;
import com.sist.r.RManager;


@Controller
public class EmpController {
	@Autowired
	private EmpDAO dao;
	@Autowired
	private RManager rm;
	
	@RequestMapping("main/list.do")
	public String main_list(Model model){
		List<EmpVO> eList=dao.empAllData();
		model.addAttribute("list", eList);
		rm.sawonSalGraph();
		return "main/list";
	}
	@RequestMapping("main/graph.do")
	public String main_graph(String no){
		if(no==null){
			no="1";
		}
		int i=Integer.parseInt(no);
		switch(i){
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;		
		}
		return "main/graph";
	}
	
	//init-method
	/*
	 * /home/sist/springDev/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/RAndJavaProject/ 
	 * */
	@PostConstruct
	public void init(){
		try {
			SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
			List<EmpVO> eList=dao.empAllData();
			String data="empno, ename, job, hiredate, sal, deptno\n";
			for(EmpVO vo : eList){
				data+=vo.getEmpno()+","+vo.getEname()+","+vo.getJob()+","+fm.format(vo.getHiredate())+","+vo.getSal()+","+vo.getDeptno()+"\n";
			}
			data=data.substring(0,data.lastIndexOf("\n"));
			FileWriter fw= new FileWriter("/home/sist/emp.csv");
			fw.write(data);
			fw.close();
			
			List<DeptVO> dList=dao.deptAllData();
			data="deptno, dname, loc\n";
			for(DeptVO vo : dList){
				data+=vo.getDeptno()+","+vo.getDname()+","+vo.getLoc()+"\n";
			}
			data=data.substring(0,data.lastIndexOf("\n"));
			fw= new FileWriter("/home/sist/dept.csv");
			fw.write(data);
			fw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
