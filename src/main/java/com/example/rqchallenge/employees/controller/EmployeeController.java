package com.example.rqchallenge.employees.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.rqchallenge.constants.EmployeeConstants;
import com.example.rqchallenge.employees.entity.Employee;
import com.example.rqchallenge.employees.service.IEmployeeService;
import com.example.rqchallenge.exceptionhandler.EmployeeValidationException;

@RestController("employee/v1")
public class EmployeeController implements IEmployeeController {

	// private static final Logger LOG =
	// LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private IEmployeeService employeeService;

	@Override
	public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
		List<Employee> allEmployees = employeeService.getAllEmployees();
		return new ResponseEntity<>(allEmployees, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
		List<Employee> employeeList = employeeService.getEmployeesByNameSearch(searchString);
		return new ResponseEntity<>(employeeList, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Employee> getEmployeeById(String id) {
		Employee employee = employeeService.getEmployeeById(id);
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
		Integer higestSalary = employeeService.getHighestSalaryOfEmployees();
		return new ResponseEntity<>(higestSalary, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
		List<String> listEmployees = employeeService.getTopTenHighestEarningEmployeeNames();
		return new ResponseEntity<>(listEmployees, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> createEmployee(Map<String, Object> employeeInput) throws EmployeeValidationException {
		Employee employeeReq = new Employee();
		try {
			if (null != employeeInput.get("employee_name")) {
				employeeReq.setEmployee_name(employeeInput.get("employee_name").toString());
			}
			if (null != employeeInput.get("employee_age")) {
				employeeReq.setEmployee_age(Integer.parseInt(employeeInput.get("employee_age").toString()));
			}
			if (null != employeeInput.get("employee_salary")) {
				employeeReq.setEmployee_salary(Integer.parseInt(employeeInput.get("employee_salary").toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new EmployeeValidationException(EmployeeConstants.ERROR_MSG_INVALID_EMPLOYEE_REQUEST);
		}
		String status = employeeService.createEmployee(employeeReq);
		return new ResponseEntity<>(status, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<String> deleteEmployeeById(String id) {
		String status = employeeService.deleteEmployeeById(id);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

}
