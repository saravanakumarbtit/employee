package com.example.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
class TestEmployeeRepository {
	
	@Resource
	EmployeeRepository employeeRepository;
	
	List<Employee> sampleList = new ArrayList<>();
	
	@Test
	@Order(1)
	void testGetAll() {		
		Employee e1 = new Employee();
		e1.setAddress("CHENNAI");
		e1.setId(1L);
		e1.setName("SARAVANAN");
		e1.setRole("SSE");
		e1.setSalary(10000);
		
		Employee e2 = new Employee();
		e2.setAddress("HYDRABAD");
		e2.setId(2L);
		e2.setName("BALAKUMAR");
		e2.setRole("CEO");
		e2.setSalary(20000);
		sampleList.add(e1);
		sampleList.add(e2);
		
		Iterable<Employee> actualResult = employeeRepository.findAll();
		actualResult.forEach(System.out::println);
		assertEquals(sampleList, actualResult);
	}
	
	@Test
	@Order(2)
	void testSave() {
		Employee employee = new Employee();
		employee.setName("rocky");
		employee.setAddress("test");
		employee.setRole("test");
		Employee newRecord = employeeRepository.save(employee);
		sampleList.add(newRecord);
		Optional<Employee> fetchedData = employeeRepository.findById(3L);
		assertEquals(newRecord, fetchedData.get());
	}
	
	@Test
	@Order(3)
	void testUpdate() {
		
		Employee employee = new Employee();
		employee.setName("Rocky balboa");
		employee.setAddress("test");
		employee.setRole("test");
		employee.setSalary(10000);
		Employee insertedRecord = employeeRepository.save(employee);

		insertedRecord.setName("Rocky Smash");
		Employee newlyUpdatedRecord = employeeRepository.save(insertedRecord);
		assertEquals("Rocky Smash", newlyUpdatedRecord.getName());
		assertEquals(insertedRecord.getId(), newlyUpdatedRecord.getId());
	}
	
	@Test
	void testDelete() {
		long counter = employeeRepository.count();
		Optional<Employee> record = employeeRepository.findById(1L);
		if(record.isPresent()) {
			employeeRepository.deleteById(record.get().getId());
		}
		long last = employeeRepository.count();
		assertEquals(last, counter - 1);
	}
}
