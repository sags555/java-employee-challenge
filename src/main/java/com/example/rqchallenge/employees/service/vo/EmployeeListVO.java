package com.example.rqchallenge.employees.service.vo;

import java.util.List;

import com.example.rqchallenge.employees.entity.Employee;

public class EmployeeListVO {
	private String status;
	private List<Employee> data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Employee> getData() {
		return data;
	}

	public void setData(List<Employee> data) {
		this.data = data;
	}

}
