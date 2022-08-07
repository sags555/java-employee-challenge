package com.example.rqchallenge.employees.service.vo;

import com.example.rqchallenge.employees.entity.Employee;

public class EmployeeDataVO {
	private String status;
	private Employee data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Employee getData() {
		return data;
	}

	public void setData(Employee data) {
		this.data = data;
	}

}
