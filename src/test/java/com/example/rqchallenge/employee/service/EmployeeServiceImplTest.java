package com.example.rqchallenge.employee.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.example.rqchallenge.employees.entity.Employee;
import com.example.rqchallenge.employees.service.EmployeeServiceImpl;
import com.example.rqchallenge.employees.service.vo.EmployeeDataVO;
import com.example.rqchallenge.employees.service.vo.EmployeeListVO;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceImplTest {
	
	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private EmployeeServiceImpl service;

	private String API_BASE_URL = "http://dummy.restapiexample.com/api/v1";

	@Before
	public void restTemplateBuild() throws Exception {
		restTemplate = new RestTemplate();
	}
	
	private EmployeeListVO getEmployeeListVO() {
		EmployeeListVO employeeListVO = new EmployeeListVO();
		employeeListVO.setData(getEmployeeList());
		return employeeListVO;
	}
	
	private List<Employee> getEmployeeList(){
		List<Employee> list = new ArrayList<>();

		Employee emp1 = new Employee();
		emp1.setId(1);
		emp1.setEmployee_name("Tiger Nixon");
		emp1.setEmployee_age(61);
		emp1.setEmployee_salary(320800);
		emp1.setProfile_image("");

		Employee emp2 = new Employee();
		emp2.setId(2);
		emp2.setEmployee_name("Garrett Winters");
		emp2.setEmployee_age(63);
		emp2.setEmployee_salary(170750);
		emp2.setProfile_image("");

		Employee emp3 = new Employee();
		emp3.setId(3);
		emp3.setEmployee_name("Ashton Cox");
		emp3.setEmployee_age(66);
		emp3.setEmployee_salary(86000);
		emp3.setProfile_image("");

		Employee emp4 = new Employee();
		emp4.setId(4);
		emp4.setEmployee_name("Cedric Kelly");
		emp4.setEmployee_age(22);
		emp4.setEmployee_salary(433060);
		emp4.setProfile_image("");

		Employee emp5 = new Employee();
		emp5.setId(5);
		emp5.setEmployee_name("Airi Satou");
		emp5.setEmployee_age(33);
		emp5.setEmployee_salary(162700);
		emp5.setProfile_image("");

		list.add(emp1);
		list.add(emp2);
		list.add(emp3);
		list.add(emp4);
		list.add(emp5);
		return list;
	}
	
	@Test
	public void getAllEmployees() throws Exception {
		EmployeeListVO employeeListVO = getEmployeeListVO();
		when(restTemplate.getForObject(API_BASE_URL + "/employees", EmployeeListVO.class)).thenReturn(employeeListVO);
		List<Employee> res = service.getAllEmployees();
		Assertions.assertEquals(res.size(), 5);
	}

	@Test
	public void getEmployeesByNameSearch() throws Exception {
		EmployeeListVO employeeListVO = getEmployeeListVO();
		when(restTemplate.getForObject(API_BASE_URL + "/employees", EmployeeListVO.class)).thenReturn(employeeListVO);
		List<Employee> res = service.getEmployeesByNameSearch("Tiger");
		Assertions.assertEquals(res.size(), 1);
		Assertions.assertEquals(res.get(0).getEmployee_name(), "Tiger Nixon");
	}

	@Test
	public void getEmployeeById() throws Exception {
		Employee emp1 = new Employee();
		emp1.setId(1);
		emp1.setEmployee_name("Tiger Nixon");
		emp1.setEmployee_age(61);
		emp1.setEmployee_salary(320800);
		emp1.setProfile_image("");
		
		EmployeeDataVO empDataVO = new EmployeeDataVO();
		empDataVO.setStatus("success");
		empDataVO.setData(emp1);
		
		when(restTemplate.getForObject(API_BASE_URL + "/employee/" + 1, EmployeeDataVO.class)).thenReturn(empDataVO);
		Employee res = service.getEmployeeById("1");
		Assertions.assertEquals(res.getEmployee_name(), "Tiger Nixon");
	}

	@Test
	public void getHighestSalaryOfEmployees() throws Exception {
		EmployeeListVO employeeListVO = getEmployeeListVO();
		when(restTemplate.getForObject(API_BASE_URL + "/employees", EmployeeListVO.class)).thenReturn(employeeListVO);
		Integer res = service.getHighestSalaryOfEmployees();
		Assertions.assertEquals(res, 433060);
	}

	@Test
	public void getTopTenHighestEarningEmployeeNames() throws Exception {
		EmployeeListVO employeeListVO = getEmployeeListVO();
		when(restTemplate.getForObject(API_BASE_URL + "/employees", EmployeeListVO.class)).thenReturn(employeeListVO);
		List<String> res = service.getTopTenHighestEarningEmployeeNames();
		Assertions.assertEquals(res.size(), 5);
		Assertions.assertEquals(res.get(0), "Cedric Kelly");//as test case is only for 5
	}

	@Test
	public void createEmployee() throws Exception {
		Employee empReq = new Employee();
		empReq.setEmployee_name("George Nixon");
		empReq.setEmployee_age(61);
		empReq.setEmployee_salary(320800);
		empReq.setProfile_image("");

		EmployeeDataVO empDataVO = new EmployeeDataVO();
		empDataVO.setStatus("success");
		empDataVO.setData(empReq);
		
		when(restTemplate.postForObject(API_BASE_URL + "/create", empReq,
				EmployeeDataVO.class)).thenReturn(empDataVO);
		String res = service.createEmployee(empReq);
		Assertions.assertEquals(res, "success");
	}

	@Test
	public void deleteEmployeeById() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> body = new HashMap<>();
		body.put("status", "success");
		ResponseEntity<Map<String, String>> resEntity= new ResponseEntity<Map<String,String>>(body, HttpStatus.OK); 
		when(restTemplate.exchange(API_BASE_URL + "/delete/" + 1, HttpMethod.DELETE, entity,
				new ParameterizedTypeReference<Map<String, String>>() {
				})).thenReturn(resEntity);
		String res = service.deleteEmployeeById("1");
		Assertions.assertEquals(res, "success");
	}

}
