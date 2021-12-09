package com.example.employee;

import static org.junit.Assert.assertFalse;
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
@DataJpaTest(properties = {"test-application.properties"})
class TestEmployeeRepository {

	@Resource
	EmployeeRepository employeeRepository;

	@Test
	@Order(1)
	void testGetAll() {		
		List<Employee> sampleList = new ArrayList<>();
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

		List<Employee> actualResult = getAllEmployeeList();
		actualResult.forEach(System.out::print);
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
		Optional<Employee> fetchedData = employeeRepository.findById(newRecord.getId());
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
		long deleteId = getAllEmployeeList().get(0).getId();
		employeeRepository.deleteById(deleteId);
		assertFalse(employeeRepository.existsById(deleteId));
	}

	/**
	 * getAllEmployeeList method returns list of all employee details from DB
	 * @return List of Employee Object
	 */
	List<Employee> getAllEmployeeList(){
		List<Employee> sampleList = new ArrayList<>();
		employeeRepository.findAll().spliterator().forEachRemaining(sampleList::add);
		return sampleList;
	}
}
