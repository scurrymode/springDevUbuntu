package com.sist.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmpDAO {
	@Autowired
	private EmpMapper mapper;
	
	public List<EmpVO> empAllData(){
		return mapper.empAllData();
	}
	public List<DeptVO> deptAllData(){
		return mapper.deptAllDate();
	}
}

