package com.springboot.employeeManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ManagementApplication {

	private static final Logger log = LoggerFactory.getLogger(ManagementApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ManagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadDatabase(EmployeeRepository repository) {
		return (args) -> {

			repository.save(new Employee("Avantika", "Manager", 1000));
			repository.save(new Employee("Anuradha", "Attender", 1000));
			repository.save(new Employee("Chandu", "Attender", 14000));
			repository.save(new Employee("Jhon", "Attender", 10500));

			log.info("All Employees");
			log.info("-------------------------------");
			for (Employee employee : repository.findAll()) {
				log.info(employee.toString());
			}

			Employee employee = repository.findById(1L);
			log.info("Employee found with findById(1L):");
			log.info("--------------------------------");
			log.info(employee.toString());
		};
	}

}