package com.example.employee.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;
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
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public List<Employee> getAllEmployeeList() {
		return employeeService.getAllEmployee();
	}

	/**
	 * saveEmployeeData is used to save employee date
	 * @param employee
	 * @return Employee entity
	 */
	@RequestMapping(value = "/", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> saveEmployeeData(@RequestBody EmployeeVo employee) {
		try {
			Employee result = employeeService.save(employee);	
			return ResponseEntity.status(HttpStatus.OK)
					.body(Objects.nonNull(result) ? result : "Record already Present");
		}catch(Exception exp) {
			//there is a possibility of NullpointerException if the name field is null
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(exp.getMessage());
		}
	}

	/**
	 * deleteEmployee is used to delete employee details based on id value
	 * @param id
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteEmployee(@RequestParam(name="id") long id) {
		try {
			Employee result = employeeService.delete(id);
			return ResponseEntity.status(HttpStatus.OK)
					.body(Objects.nonNull(result)?result:"No Data to delete");
		}catch(Exception exp) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(exp.getMessage());
		}
	}

	/**
	 * updateEmployee is used to update employee details
	 * @param employee
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/", method=RequestMethod.PUT)
	public ResponseEntity<Object> updateEmployee(@RequestBody EmployeeVo employee) {
		try {
			Employee result = employeeService.update(employee);
			return ResponseEntity.status(HttpStatus.OK)
					.body(Objects.nonNull(result) ? result : "There is not data with the given employee id");
		}catch(Exception exp) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(exp.getMessage());
		}
	}

	/**
	 * ping method is a test method
	 * @return String
	 */
	@RequestMapping(value="/ping", method=RequestMethod.GET)
	public String ping() {
		return "EmployeeController working";
	}

}
