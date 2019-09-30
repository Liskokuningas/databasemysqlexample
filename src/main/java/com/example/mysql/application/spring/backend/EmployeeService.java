package com.example.mysql.application.spring.backend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmployeeService {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Employee> findAll() {    	
    	try {
			return jdbcTemplate.query("SELECT firstname, lastname, email, title FROM employees",
			        (rs, rowNum) -> new Employee(rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"), rs.getString("title")));
		} catch (Exception e) {
			return new ArrayList<Employee>();	
		}		
    }
    
    public List<Employee> findByEmail(String email) {    	
    	try {
			return jdbcTemplate.query("SELECT firstname, lastname, email, title FROM employees WHERE email = ?",
					new Object[]{email},
			        (rs, rowNum) -> new Employee(rs.getString("firstname"), rs.getString("lastname"), rs.getString("email"), rs.getString("title")));
		} catch (Exception e) {
			return new ArrayList<Employee>();	
		}    	
    }
    
    public int saveEmployee(Employee employee) {
    	List<Employee> employees = this.findByEmail(employee.getEmail());
    	if ( employees.size() > 0 ) {
    		return updateEmployee(employee);
    	} else {
    		return insertEmployee(employee);
    	}
    	
    }
    
    private int updateEmployee(Employee employee) {    	
    	try {
			return jdbcTemplate.update("UPDATE employees SET lastname = ?, firstname = ? WHERE email = ?",
					employee.getLastname(), employee.getFirstname(), employee.getEmail());
		} catch (Exception e) {
			return 0;
		}
    }
    
    private int insertEmployee(Employee employee) {
    	try {
			return jdbcTemplate.update("INSERT INTO employees VALUES (?, ?, ?, ?, ?)",
					employee.getFirstname(), employee.getLastname(), employee.getTitle(), employee.getEmail(), "");
		} catch (Exception e) {
			return 0;
		}    	
    }
    
    public int deleteEmployee(Employee employee) {
    	try {
    		return jdbcTemplate.update("DELETE FROM employees WHERE email = ?",
    				employee.getEmail());
    	} catch (Exception e) {
			return 0;
		}
    }
}
