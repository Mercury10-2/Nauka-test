package com.nauka.repository;

import com.nauka.domain.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Long> {

    Department findByDeptName(String deptName);
}
