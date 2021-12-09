package com.example.employee.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.entity.Employee;
import com.example.employee.response.EmployeeResponse;
import com.example.employee.service.EmployeeService;
import com.example.employee.vo.EmployeeVo;


/**
 * EmployeeController contains all the endpoints to perform CRUD operations in Employee entity
 * @author bsaravanan
 * @version 1.0
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	/**
	 * getAllEmployeeList is used to get list of employee details
	 * @return List of Employees
	 */
	@GetMapping()
	public EmployeeResponse getAllEmployeeList() {
		return new EmployeeResponse(HttpStatus.OK.value(),  
				"Successfully retrieved all data", employeeService.getAllEmployee());
	}

	/**
	 * saveEmployeeData is used to save employee date
	 * @param employee
	 * @return EmployeeResponse
	 */
	@PostMapping()
	@ResponseBody
	public EmployeeResponse saveEmployeeData(@RequestBody EmployeeVo employee) {
		List<String> missingFieldsList = new ArrayList<>();
		if(checkRequestData(employee, "insert", missingFieldsList)) {
			Employee result = employeeService.save(employee);	
			EmployeeResponse response = new EmployeeResponse();
			response.setData(Objects.nonNull(result) ? result : employee);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage(Objects.nonNull(result) ? "Record inserted successfully" 
					: "Record already Present");
			return response;
		}
		return badRequest(missingFieldsList, employee);
	}
	
	/**
	 * updateEmployee is used to update employee details
	 * @param employee
	 * @return EmployeeResponse
	 */
	@PutMapping()
	@ResponseBody
	public EmployeeResponse updateEmployee(@RequestBody EmployeeVo employee) {
		List<String> missingFieldsList = new ArrayList<>();
		if(checkRequestData(employee, "update", missingFieldsList)) {
			Employee result = employeeService.update(employee);
			EmployeeResponse response = new EmployeeResponse();
			response.setData(Objects.nonNull(result) ? result: employee);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage(Objects.nonNull(result) ? "Record Updated Successfully" 
					: "No Data to update, for the given employee id");
			return response;
		}
		return badRequest(missingFieldsList, employee);
	}

	/**
	 * deleteEmployee is used to delete employee details based on id value
	 * @param id
	 * @return ResponseEntity
	 */
	@DeleteMapping("/{id}")
	public EmployeeResponse deleteEmployee(@PathVariable("id") long id) {
		Employee result = employeeService.delete(id);

		EmployeeResponse response = new EmployeeResponse();
		response.setData(Objects.nonNull(result) ? result : id);
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage(Objects.nonNull(result) ? "Record deleted successfully" 
				: "No Data to delete");
		return response;
	}


	/**
	 * ping method is a test method
	 * @return String
	 */
	@GetMapping("/ping")
	public String ping() {
		return "EmployeeController working";
	}

	/**
	 * badRequest method returns 400 response
	 * @param missingFieldsList
	 * @param employee
	 * @return EmployeeResponse
	 */
	private EmployeeResponse badRequest(List<String> missingFieldsList, EmployeeVo employee) {
		EmployeeResponse response = new EmployeeResponse();
		response.setData(employee);
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage(missingFieldsList.toString() + " - Mentioned fields cannot be empty");
		return response;
	}

	/**
	 * checkRequestData is used to check missing fields in the input
	 * @param employee
	 * @param methodName
	 * @param missingFieldsList
	 * @return a list containing the missing fields
	 */
	private boolean checkRequestData(EmployeeVo employee, String methodName, List<String> missingFieldsList) {
		if("update".equals(methodName) && !Objects.nonNull(employee.getId())) {
			missingFieldsList.add("Id");
		}
		if(StringUtils.isBlank(employee.getAddress())) {
			missingFieldsList.add("Address");
		}
		if(StringUtils.isBlank(employee.getName())) {
			missingFieldsList.add("Name");
		}
		if(StringUtils.isBlank(employee.getRole())) {
			missingFieldsList.add("Role");
		}
		if(employee.getSalary() <= 0) {
			missingFieldsList.add("Salary(>0)");
		}
		return missingFieldsList.size() > 0 ? false : true;
	}

}

