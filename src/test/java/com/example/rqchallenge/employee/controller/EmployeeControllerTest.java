package com.example.rqchallenge.employee.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.rqchallenge.employees.controller.EmployeeController;
import com.example.rqchallenge.employees.entity.Employee;
import com.example.rqchallenge.employees.service.IEmployeeService;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

	@MockBean
	private IEmployeeService service;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getAllEmployeesSuccess() throws Exception {
		List<Employee> list = getEmployeesList();
		when(service.getAllEmployees()).thenReturn(list);
		this.mockMvc.perform(get("/")).andExpect(status().isOk());
	}

	@Test
	public void getEmployeesByNameSearchSuccess() throws Exception {
		List<Employee> list = getEmployeesList();
		when(service.getEmployeesByNameSearch("Tiger")).thenReturn(list);
		this.mockMvc.perform(get("/search/Tiger")).andExpect(status().isOk());
	}
	
	@Test
	public void getEmployeeByIdSuccess() throws Exception {
		Employee emp5 = new Employee();
		emp5.setId(5);
		emp5.setEmployee_name("Airi Satou");
		emp5.setEmployee_age(33);
		emp5.setEmployee_salary(162700);

		when(service.getEmployeeById("5")).thenReturn(emp5);
		this.mockMvc.perform(get("/5")).andExpect(status().isOk());
	}

	@Test
	public void getHighestSalaryOfEmployees() throws Exception {
		when(service.getHighestSalaryOfEmployees()).thenReturn(725000);
		this.mockMvc.perform(get("/highestSalary")).andExpect(status().isOk());
	}

	@Test
	public void getTopTenHighestEarningEmployeeNames() throws Exception {
		List<String> list = new ArrayList<>();
		list.add("Tiger Nixon");
		list.add("Garrett Winters");
		list.add("Ashton Cox");
		list.add("Cedric Kelly");
		list.add("Airi Satou");
		list.add("Tiger Nixon2");
		list.add("Garrett Winters2");
		list.add("Ashton Cox2");
		list.add("Cedric Kelly2");
		list.add("Airi Satou2");

		when(service.getTopTenHighestEarningEmployeeNames()).thenReturn(list);
		this.mockMvc.perform(get("/topTenHighestEarningEmployeeNames")).andExpect(status().isOk());
	}

	@Test
	public void createEmployeeSuccess() throws Exception {
		when(service.createEmployee(new Employee())).thenReturn("success");
		mockMvc.perform(MockMvcRequestBuilders.post("/").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isCreated());
	}
	
	List<Employee> getEmployeesList() {
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
}
