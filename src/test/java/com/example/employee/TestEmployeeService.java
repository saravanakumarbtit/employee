package com.example.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.service.EmployeeServiceImpl;
import com.example.employee.vo.EmployeeVo;

@RunWith(SpringRunner.class)
@DataJpaTest
class TestEmployeeService {
	
	@InjectMocks
	EmployeeServiceImpl employeeService;

	@Mock
	EmployeeRepository employeeRepository;
	
	@Test
	void testGetAll() {		
		List<Employee> sampleList = new ArrayList<Employee>();
		Employee employee = new Employee();
		employee.setName("saravanan");
		employee.setAddress("test");
		employee.setRole("sse");
		employeeRepository.save(employee);
		employeeRepository.findAll().forEach(System.out::print);
		
		sampleList.add(employee);
		Mockito.when(employeeRepository.findAll()).thenReturn(sampleList);
		List<Employee> actualResult = employeeService.getAllEmployee();
		assertEquals(sampleList, actualResult);
	}
	
	@Test
	void testSave() {
		EmployeeVo employeeVo = getEmployeeVo();
		Employee employee = getEmployee();
		
		Mockito.when(employeeRepository.save(any())).thenReturn(employee);
		Employee actualResult = employeeService.save(employeeVo);
		assertEquals(employee, actualResult);
		
		employeeVo.setId(1L);
		actualResult = employeeService.save(employeeVo);
		assertEquals(employee, actualResult);
		
		Mockito.when(employeeRepository.existsById(anyLong())).thenReturn(true);
		actualResult = employeeService.save(employeeVo);
		assertEquals(null, actualResult);
	}
	
	@Test
	void testUpdate() {
		EmployeeVo employeeVo = getEmployeeVo();
		Employee employee = getEmployee();
		
		employeeVo.setId(1L);
		Employee actualResult = employeeService.update(employeeVo);
		assertEquals(null, actualResult);
		
		Mockito.when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
		Mockito.when(employeeRepository.save(any())).thenReturn(employee);
		actualResult = employeeService.update(employeeVo);
		assertEquals(employee, actualResult);
	}
	
	@Test
	void testDelete() {
		Employee employee = new Employee();
		employee.setName("saravanan");
		employee.setAddress("test");
		employee.setRole("sse");
		employee.setId(1L);
		
		Employee actualResult = employeeService.delete(1L);
		assertEquals(null, actualResult);
		
		Mockito.when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
		actualResult = employeeService.delete(1L);
		assertEquals(employee, actualResult);
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
