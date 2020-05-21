package com.nauka.repository;

import com.nauka.domain.Department;
import com.nauka.domain.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    List<Employee> findAllByDepartment(Department department);
}
