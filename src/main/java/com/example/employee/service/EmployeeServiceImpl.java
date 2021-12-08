package com.example.employee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.vo.EmployeeVo;


/**
 * EmployeeServiceImpl contains all the implementations for abstract methods present inside EmployeeService
 * @author bsaravanan
 */
@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getAllEmployee() {
		List<Employee> resultList = new ArrayList<Employee>();
		employeeRepository.findAll().spliterator().forEachRemaining(resultList::add);
		return resultList;
	}

	@Override
	public Employee delete(long id) {
		Optional<Employee> deleterecord = employeeRepository.findById(id);
		if(deleterecord.isPresent()) {
			employeeRepository.delete(deleterecord.get());
			return deleterecord.get();
		}
		return null;
	}

	@Override
	public Employee update(EmployeeVo employee) {
		Optional<Employee> sample = employeeRepository.findById(employee.getId());
		if(sample.isPresent()) {
			Employee updateRecord = sample.get();
			updateRecord.setAddress(employee.getAddress());
			updateRecord.setName(employee.getName());
			updateRecord.setRole(employee.getRole());
			updateRecord.setSalary(employee.getSalary());			
			return employeeRepository.save(updateRecord);	
		}
		return null;
	}

	@Override
	public Employee save(EmployeeVo employee) {
			Employee saveRecord = new Employee();
			saveRecord.setName(employee.getName());
			saveRecord.setAddress(employee.getAddress());
			saveRecord.setRole(employee.getRole());
			saveRecord.setSalary(employee.getSalary());
			if(employee.getId() != null) {
				boolean recordFound = employeeRepository.existsById(employee.getId());
				return recordFound ? null : employeeRepository.save(saveRecord); 
			}
			return employeeRepository.save(saveRecord);
	}
}
