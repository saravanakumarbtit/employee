package com.example.employee.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.employee.entity.Employee;


/**
 * EmployeeRepository is used to perform CRUD operations on Employee entity
 * @author bsaravanan
 *
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long>{

}
