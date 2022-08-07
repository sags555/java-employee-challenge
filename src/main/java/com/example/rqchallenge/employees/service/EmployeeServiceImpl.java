package com.example.rqchallenge.employees.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.rqchallenge.employees.entity.Employee;
import com.example.rqchallenge.employees.service.vo.EmployeeDataVO;
import com.example.rqchallenge.employees.service.vo.EmployeeListVO;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private RestTemplate restTemplate;

	// @Value
	private String API_BASE_URL = "http://dummy.restapiexample.com/api/v1";

	@Override
	public List<Employee> getAllEmployees() {
		EmployeeListVO empListVO = restTemplate.getForObject(API_BASE_URL + "/employees", EmployeeListVO.class);
		return empListVO.getData();
	}

	@Override
	public List<Employee> getEmployeesByNameSearch(String searchString) {
		EmployeeListVO empListVO = restTemplate.getForObject(API_BASE_URL + "/employees", EmployeeListVO.class);
		List<Employee> list = empListVO.getData().stream()
				.filter(emp -> emp.getEmployee_name().toLowerCase().contains(searchString.toLowerCase()))
				.collect(Collectors.toList());
		return list;
	}

	@Override
	public Employee getEmployeeById(String id) {
		EmployeeDataVO employeeVo = restTemplate.getForObject(API_BASE_URL + "/employee/" + id, EmployeeDataVO.class);
		return employeeVo.getData();
	}

	@Override
	public Integer getHighestSalaryOfEmployees() {
		EmployeeListVO empListVO = restTemplate.getForObject(API_BASE_URL + "/employees", EmployeeListVO.class);
		Optional<Employee> emp = empListVO.getData().stream()
				.sorted(Comparator.comparingInt(Employee::getEmployee_salary).reversed()).findFirst();
		return emp.get().getEmployee_salary();
	}

	@Override
	public List<String> getTopTenHighestEarningEmployeeNames() {
		EmployeeListVO empListVO = restTemplate.getForObject(API_BASE_URL + "/employees", EmployeeListVO.class);
		return empListVO.getData().stream().sorted(Comparator.comparingInt(Employee::getEmployee_salary).reversed())
				.limit(10).map(Employee::getEmployee_name).collect(Collectors.toList());
	}

	@Override
	public String createEmployee(Employee employeeReq) {
		EmployeeDataVO empDataVO = restTemplate.postForObject(API_BASE_URL + "/create", employeeReq,
				EmployeeDataVO.class);
		return empDataVO.getStatus();
	}

	@Override
	public String deleteEmployeeById(String id) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> body = restTemplate.exchange(API_BASE_URL + "/delete/" + id, HttpMethod.DELETE, entity,
				new ParameterizedTypeReference<Map<String, String>>() {
				}).getBody();
		return body.get("status");
	}

}
