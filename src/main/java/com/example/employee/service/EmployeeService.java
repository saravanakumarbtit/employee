package com.example.employee.service;

import java.util.List;

import com.example.employee.entity.Employee;
import com.example.employee.vo.EmployeeVo;



/**
 * EmployeeService is an interface that contains all the abstract methods to handle logical calculations
 * @author bsaravanan
 *
 */
public interface EmployeeService {

	/**
	 * getAllEmployee is used to get all the employee details
	 * @return
	 */
	public List<Employee> getAllEmployee();

	/**
	 * delete method is used to delete a record based on employee id
	 * @param id
	 * @return
	 */
	public Employee delete(long id);

	/**
	 * save method is used to insert a new employee record
	 * @param employee
	 * @return
	 */
	Employee save(EmployeeVo employee);

	/**
	 * update method is used to do partial update
	 * @param employee
	 * @return
	 */
	Employee update(EmployeeVo employee);
}
