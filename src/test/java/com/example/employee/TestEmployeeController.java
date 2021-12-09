package com.example.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.employee.controller.EmployeeController;
import com.example.employee.entity.Employee;
import com.example.employee.response.EmployeeResponse;
import com.example.employee.service.EmployeeService;
import com.example.employee.vo.EmployeeVo;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EmployeeController.class)
class TestEmployeeController {

	@MockBean
	EmployeeService employeeService;

	@InjectMocks
	EmployeeController employeeController;

	@Autowired
	private MockMvc mvc;

	@Test
	public void getAllEmployeesApi() throws Exception {
		List<Employee> sampleList = new ArrayList<Employee>();
		Employee employee = new Employee();
		employee.setName("saravanan");
		sampleList.add(employee);
		Mockito.when(employeeService.getAllEmployee()).thenReturn(sampleList);
		EmployeeResponse response = new EmployeeResponse(200, "Successfully retrieved all data", sampleList);
		mvc.perform(MockMvcRequestBuilders
				.get("/employee"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(new ObjectMapper().writeValueAsString(response)));
	}

	@Test
	public void testPing() throws Exception {
		mvc.perform(MockMvcRequestBuilders
				.get("/employee/ping"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string("EmployeeController working"));
	}

	@Test
	public void saveEmployeesApi() throws Exception {
		EmployeeVo employeeVo = getEmployeeVo();
		Employee employee = getEmployee();

		Mockito.when(employeeService.save(any())).thenReturn(null);
		EmployeeResponse response = new EmployeeResponse(200, "Record already Present", employeeVo);
		mvc.perform(MockMvcRequestBuilders
				.post("/employee").content(new ObjectMapper().writeValueAsString(employeeVo))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(new ObjectMapper().writeValueAsString(response)));

		Mockito.when(employeeService.save(any())).thenReturn(employee);
		EmployeeResponse responseOne = new EmployeeResponse(200, "Record inserted successfully", employee);
		mvc.perform(MockMvcRequestBuilders
				.post("/employee").content(new ObjectMapper().writeValueAsString(employeeVo))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(new ObjectMapper().writeValueAsString(responseOne)));

		//Checking controller validation when we send empty values in EmployeeVo
		EmployeeVo employeeOneVo = new EmployeeVo();
		EmployeeResponse responsethree = new EmployeeResponse(400, 
				"[Address, Name, Role, Salary(>0)] - Mentioned fields cannot be empty", employeeOneVo);
		MockHttpServletResponse outputOne = mvc.perform(MockMvcRequestBuilders
				.post("/employee").content(new ObjectMapper().writeValueAsString(employeeOneVo))
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		assertEquals(new ObjectMapper().writeValueAsString(responsethree), outputOne.getContentAsString());
	}


	@Test
	public void updateEmployeesApi() throws Exception {
		EmployeeVo employeeVo = getEmployeeVo();
		employeeVo.setId(1L);
		Employee employee = getEmployee();

		Mockito.when(employeeService.update(any())).thenReturn(null);
		EmployeeResponse response = new EmployeeResponse(200, "No Data to update, for the given employee id", employeeVo);
		mvc.perform(MockMvcRequestBuilders
				.put("/employee").content(new ObjectMapper().writeValueAsString(employeeVo))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(new ObjectMapper().writeValueAsString(response)));

		Mockito.when(employeeService.update(any())).thenReturn(employee);
		EmployeeResponse responseOne = new EmployeeResponse(200, "Record Updated Successfully", employee);
		mvc.perform(MockMvcRequestBuilders
				.put("/employee").content(new ObjectMapper().writeValueAsString(employeeVo))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(new ObjectMapper().writeValueAsString(responseOne)));

		//Checking ControllerAdvice by throwing an exception
		EmployeeResponse responsetwo = new EmployeeResponse(500, "checking", null);
		Mockito.when(employeeService.update(any())).thenThrow(new NullPointerException("checking"));
		MockHttpServletResponse output = mvc.perform(MockMvcRequestBuilders
				.put("/employee").content(new ObjectMapper().writeValueAsString(employeeVo))
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		assertEquals(500, output.getStatus());
		assertEquals(new ObjectMapper().writeValueAsString(responsetwo), output.getContentAsString());

		//Checking controller validation when we send empty values in EmployeeVo
		EmployeeVo employeeOneVo = new EmployeeVo();
		EmployeeResponse responsethree = new EmployeeResponse(400, 
				"[Id, Address, Name, Role, Salary(>0)] - Mentioned fields cannot be empty", employeeOneVo);
		MockHttpServletResponse outputOne = mvc.perform(MockMvcRequestBuilders
				.put("/employee").content(new ObjectMapper().writeValueAsString(employeeOneVo))
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		assertEquals(new ObjectMapper().writeValueAsString(responsethree), outputOne.getContentAsString());
	}

	@Test
	public void deleteEmployeesApi() throws Exception {
		Employee employee = getEmployee();

		Mockito.when(employeeService.delete(anyLong())).thenReturn(null);
		EmployeeResponse response = new EmployeeResponse(200, "No Data to delete", 3);
		mvc.perform(MockMvcRequestBuilders
				.delete("/employee/3").contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(new ObjectMapper().writeValueAsString(response)));

		Mockito.when(employeeService.delete(anyLong())).thenReturn(employee);
		EmployeeResponse responseOne = new EmployeeResponse(200, "Record deleted successfully", employee);
		mvc.perform(MockMvcRequestBuilders
				.delete("/employee/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(new ObjectMapper().writeValueAsString(responseOne)));


	}

	EmployeeVo getEmployeeVo() {
		EmployeeVo employeeVo = new EmployeeVo();
		employeeVo.setName("saravanan");
		employeeVo.setAddress("test");
		employeeVo.setRole("sse");
		employeeVo.setSalary(100);
		return employeeVo;
	}

	Employee getEmployee() {
		Employee employee = new Employee();
		employee.setName("saravanan");
		employee.setAddress("test");
		employee.setRole("sse");
		employee.setSalary(100);
		employee.setId(1L);
		return employee;
	}
}
