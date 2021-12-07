package com.example.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.employee.controller.EmployeeController;
import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.service.EmployeeService;
import com.example.employee.vo.EmployeeVo;

@RunWith(SpringRunner.class)
@SpringBootTest
class EmployeeApplicationTests {

	@InjectMocks
	EmployeeController employeeController;
	
	@Mock
	EmployeeService employeeService;
	
	@MockBean 
	EmployeeRepository employeeRepository;
	
	@Test
	void getAll() {
		Mockito.when(employeeService.getAllEmployee()).thenReturn(null);
		List<Employee> actualResult = employeeController.getAllEmployeeList();
		assertEquals(null, actualResult);
		
		List<Employee> sampleList = new ArrayList<Employee>();
		Employee employee = new Employee();
		employee.setName("saravanan");
		sampleList.add(employee);
		Mockito.when(employeeService.getAllEmployee()).thenReturn(sampleList);
		actualResult = employeeController.getAllEmployeeList();
		assertEquals(sampleList, actualResult);
	}
	
	@Test
	void testSave() {
		EmployeeVo employeeVo = new EmployeeVo();
		employeeVo.setName("saravanan");
		employeeVo.setAddress("test");
		employeeVo.setRole("sse");
		
		Employee employee = new Employee();
		employee.setName("saravanan");
		employee.setAddress("test");
		employee.setRole("sse");
		employee.setId(1L);
		
		Mockito.when(employeeService.save(any())).thenReturn(employee);
		ResponseEntity<Object> actual = employeeController.saveEmployeeData(employeeVo);
		ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.OK).body(employee);
		assertEquals(expected, actual);
		
		Mockito.when(employeeService.save(any())).thenReturn(null);
		ResponseEntity<Object> actualOne = employeeController.saveEmployeeData(employeeVo);
		ResponseEntity<Object> expectedOne = ResponseEntity.status(HttpStatus.OK).body("Record already Present");
		assertEquals(expectedOne, actualOne);
		
		Mockito.when(employeeService.save(any())).thenThrow(NullPointerException.class);
		ResponseEntity<Object> actualTwo = employeeController.saveEmployeeData(employeeVo);
		ResponseEntity<Object> expectedTwo = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		assertEquals(expectedTwo, actualTwo);
	}
	
	@Test
	void testDelete() {
		Employee employee = new Employee();
		employee.setName("saravanan");
		employee.setAddress("test");
		employee.setRole("sse");
		employee.setId(1L);
		Mockito.when(employeeService.delete(anyLong())).thenReturn(employee);
		ResponseEntity<Object> actual = employeeController.deleteEmployee(1L);
		ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.OK).body(employee);
		assertEquals(expected, actual);
		
		Mockito.when(employeeService.delete(anyLong())).thenReturn(null);
		ResponseEntity<Object> actualOne = employeeController.deleteEmployee(1L);
		ResponseEntity<Object> expectedOne = ResponseEntity.status(HttpStatus.OK).body("No Data to delete");
		assertEquals(expectedOne, actualOne);
		
		Mockito.when(employeeService.delete(anyLong())).thenThrow(NullPointerException.class);
		ResponseEntity<Object> actualTwo = employeeController.deleteEmployee(1L);
		ResponseEntity<Object> expectedTwo = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		assertEquals(expectedTwo, actualTwo);
	}

	@Test
	void testUpdate() {
		EmployeeVo employeeVo = new EmployeeVo();
		employeeVo.setName("saravanan");
		employeeVo.setAddress("test");
		employeeVo.setRole("sse");
		employeeVo.setId(1L);
		Employee employee = new Employee();
		employee.setName("saravanan");
		employee.setAddress("test");
		employee.setRole("sse");
		employee.setId(1L);
		Mockito.when(employeeService.update(employeeVo)).thenReturn(employee);
		ResponseEntity<Object> actual = employeeController.updateEmployee(employeeVo);
		ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.OK).body(employee);
		assertEquals(expected, actual);
		
		Mockito.when(employeeService.update(employeeVo)).thenReturn(null);
		ResponseEntity<Object> actualOne = employeeController.updateEmployee(employeeVo);
		ResponseEntity<Object> expectedOne = ResponseEntity.status(HttpStatus.OK)
				.body("There is not data with the given employee id");
		assertEquals(expectedOne, actualOne);
		
		Mockito.when(employeeService.update(employeeVo)).thenThrow(NullPointerException.class);
		ResponseEntity<Object> actualTwo = employeeController.updateEmployee(employeeVo);
		ResponseEntity<Object> expectedTwo = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		assertEquals(expectedTwo, actualTwo);
	}
	
	@Test
	void testPing() {
		String actual = employeeController.ping();
		String expected = "EmployeeController working";
		assertEquals(expected, actual);
	}
}
