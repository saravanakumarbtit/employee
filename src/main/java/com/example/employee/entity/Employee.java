package com.example.employee.entity;

import javax.persistence.*;

import org.springframework.lang.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Employee is the main entity used as the DB schema
 * @author bsaravanan
 *
 */
@Entity
@Data
@NoArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	
	@NonNull
	private String name;
	
	private String role;
	
	private String address;
	
	private int salary;

}
