package com.paypal.bfs.test.employeeserv;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.domain.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.exception.EmployeeNotFoundException;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;


@RunWith(SpringRunner.class)
public class EmployeeServiceTest {
	
	@InjectMocks
	private EmployeeService employeeService; 
	
	@Mock
	private EmployeeRepository employeeRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Test when employeeGetById is called, then return the employee by id 
	 */	
	@Test
	public void whenEmployeeGetById_thenReturnEmployee() {		
		EmployeeEntity employeeEntity = new EmployeeEntity(100, "Venkat", "Kondoju", LocalDate.now());	
		Employee employee = new Employee();
		employee.setId(100);
		employee.setFirstName("Venkat");
		employee.setLastName("Kondoju");
		employee.setDateOfBirth("08/05/2020");
		Mockito.when(modelMapper.map(employeeEntity, Employee.class)).thenReturn(employee);	
		Mockito.when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employeeEntity));
		Employee employeeFound = employeeService.employeeGetById(employee.getId());	
		assertThat(employeeFound.getId()).isEqualTo(employee.getId());
	}
	

	/**
	 * Test when employeeGetById is called, then throw the "EmployeeNotFoundException" exception 
	 */
	@Test
	public void whenEmployeeGetById_thenThrowEmployeeNotFoundException() {		
		EmployeeEntity employeeEntity = new EmployeeEntity(100, "Venkat", "Kondoju", LocalDate.now());	
		Employee employee = new Employee();
		employee.setId(100);
		employee.setFirstName("Venkat");
		employee.setLastName("Kondoju");
		employee.setDateOfBirth("08/05/2020");
		Mockito.when(modelMapper.map(employeeEntity, Employee.class)).thenReturn(employee);
		Mockito.when(employeeRepository.findById(100)).thenThrow(new EmployeeNotFoundException(employee.getId()));		
		Boolean exceptionFound = false;
		try{			
			employeeService.employeeGetById(employee.getId());	
		}
		catch(EmployeeNotFoundException ex) {
			exceptionFound = true;
		}		
		assertThat(exceptionFound).isTrue();	
	}
}
