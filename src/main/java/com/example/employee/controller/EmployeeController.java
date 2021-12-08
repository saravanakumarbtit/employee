package com.example.employee.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.entity.Employee;
import com.example.employee.service.EmployeeService;
import com.example.employee.vo.EmployeeVo;


/**
 * EmployeeController contains all the endpoints to perform CRUD operations in Employee entity
 * @author bsaravanan
 * @version 1.0
 */
@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	/**
	 * getAllEmployeeList is used to get list of employee details
	 * @return List of Employees
	 */
	@GetMapping("/employee")
	public List<Employee> getAllEmployeeList() {
		return employeeService.getAllEmployee();
	}

	/**
	 * saveEmployeeData is used to save employee date
	 * @param employee
	 * @return Employee entity
	 */
	@PostMapping("/employee")
	@ResponseBody
	public ResponseEntity<Object> saveEmployeeData(@RequestBody EmployeeVo employee) {
			Employee result = employeeService.save(employee);	
			return ResponseEntity.status(HttpStatus.OK)
					.body(Objects.nonNull(result) ? result : "Record already Present");
	}

	/**
	 * deleteEmployee is used to delete employee details based on id value
	 * @param id
	 * @return ResponseEntity
	 */
	@DeleteMapping("/employee")
	public ResponseEntity<Object> deleteEmployee(@RequestParam(name="id") long id) {
			Employee result = employeeService.delete(id);
			return ResponseEntity.status(HttpStatus.OK)
					.body(Objects.nonNull(result)?result:"No Data to delete");
	}

	/**
	 * updateEmployee is used to update employee details
	 * @param employee
	 * @return ResponseEntity
	 */
	@PutMapping("/employee")
	public ResponseEntity<Object> updateEmployee(@RequestBody EmployeeVo employee) {
			Employee result = employeeService.update(employee);
			return ResponseEntity.status(HttpStatus.OK)
					.body(Objects.nonNull(result) ? result : "There is not data with the given employee id");
	}

	/**
	 * ping method is a test method
	 * @return String
	 */
	@GetMapping("/ping")
	public String ping() {
		return "EmployeeController working";
	}

}
