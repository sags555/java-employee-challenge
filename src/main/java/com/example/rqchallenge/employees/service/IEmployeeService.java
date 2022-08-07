package com.example.rqchallenge.employees.service;

import java.io.IOException;
import java.util.List;

import com.example.rqchallenge.employees.entity.Employee;

public interface IEmployeeService {
	List<Employee> getAllEmployees() throws IOException;
	List<Employee> getEmployeesByNameSearch(String searchString);
	Employee getEmployeeById(String id);
	Integer getHighestSalaryOfEmployees();
	List<String> getTopTenHighestEarningEmployeeNames();
	String createEmployee(Employee employeeReq);
	String deleteEmployeeById(String id);
}
