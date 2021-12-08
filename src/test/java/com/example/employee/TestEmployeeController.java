package com.example.employee;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.employee.controller.EmployeeController;
import com.example.employee.entity.Employee;
import com.example.employee.service.EmployeeService;
import com.example.employee.vo.EmployeeVo;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
class TestEmployeeController {

	@MockBean
	EmployeeService employeeService;
	
	@InjectMocks
	EmployeeController employeeController;
	
	@Autowired
	private MockMvc mvc;

	@Before
	public void setup() throws Exception{
		mvc = MockMvcBuilders.standaloneSetup(employeeController).build();
	}
	
	@Test
	public void getAllEmployeesApi() throws Exception {
		Mockito.when(employeeService.getAllEmployee()).thenReturn(null);
		mvc.perform(MockMvcRequestBuilders
				.get("/employee"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(new String()));
		
		List<Employee> sampleList = new ArrayList<Employee>();
		Employee employee = new Employee();
		employee.setName("saravanan");
		sampleList.add(employee);
		Mockito.when(employeeService.getAllEmployee()).thenReturn(sampleList);
		mvc.perform(MockMvcRequestBuilders
				.get("/employee"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(new ObjectMapper().writeValueAsString(sampleList)));
	}
	
	@Test
	public void testPing() throws Exception {
		mvc.perform(MockMvcRequestBuilders
				.get("/ping"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string("EmployeeController working"));
	}
	
	@Test
	public void saveEmployeesApi() throws Exception {
		EmployeeVo employeeVo = getEmployeeVo();
		Employee employee = getEmployee();
		
		Mockito.when(employeeService.save(any())).thenReturn(null);
		mvc.perform(MockMvcRequestBuilders
				.post("/employee").content(new ObjectMapper().writeValueAsString(employeeVo))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string("Record already Present"));
		
		Mockito.when(employeeService.save(any())).thenReturn(employee);
		mvc.perform(MockMvcRequestBuilders
				.post("/employee").content(new ObjectMapper().writeValueAsString(employeeVo))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(new ObjectMapper().writeValueAsString(employee)));
	}
	
	
	@Test
	public void updateEmployeesApi() throws Exception {
		EmployeeVo employeeVo = getEmployeeVo();
		Employee employee = getEmployee();
		
		Mockito.when(employeeService.update(any())).thenReturn(null);
		mvc.perform(MockMvcRequestBuilders
				.put("/employee").content(new ObjectMapper().writeValueAsString(employeeVo))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string("There is not data with the given employee id"));
		
		Mockito.when(employeeService.update(any())).thenReturn(employee);
		mvc.perform(MockMvcRequestBuilders
				.put("/employee").content(new ObjectMapper().writeValueAsString(employeeVo))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(new ObjectMapper().writeValueAsString(employee)));
	}
	
	@Test
	public void deleteEmployeesApi() throws Exception {
		Employee employee = getEmployee();

		Mockito.when(employeeService.delete(anyLong())).thenReturn(null);
		mvc.perform(MockMvcRequestBuilders
				.delete("/employee").param("id", String.valueOf(1L))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string("No Data to delete"));
		
		Mockito.when(employeeService.delete(anyLong())).thenReturn(employee);
		mvc.perform(MockMvcRequestBuilders
				.delete("/employee").param("id", String.valueOf(1L))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(new ObjectMapper().writeValueAsString(employee)));
	}
	
	EmployeeVo getEmployeeVo() {
		EmployeeVo employeeVo = new EmployeeVo();
		employeeVo.setName("saravanan");
		employeeVo.setAddress("test");
		employeeVo.setRole("sse");
		return employeeVo;
	}
	
	Employee getEmployee() {
		Employee employee = new Employee();
		employee.setName("saravanan");
		employee.setAddress("test");
		employee.setRole("sse");
		employee.setId(1L);
		return employee;
	}
}
