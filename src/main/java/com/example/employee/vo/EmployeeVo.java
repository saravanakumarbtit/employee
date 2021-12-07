package com.example.employee.vo;

import lombok.Data;

/**
 * EmployeeVo acts as an Value object for Employee entity
 * @author bsaravanan
 *
 */
@Data
public class EmployeeVo {
	private Long id;
	
	private String name;
	
	private String role;
	
	private String address;
	
	private int salary;
}
