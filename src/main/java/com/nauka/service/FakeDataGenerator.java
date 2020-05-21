package com.nauka.service;

import com.nauka.domain.Department;
import com.nauka.domain.Employee;
import com.nauka.repository.DepartmentRepository;
import com.nauka.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class FakeDataGenerator {

    private DepartmentRepository departmentRepository;
    private EmployeeRepository employeeRepository;


    public FakeDataGenerator(DepartmentRepository departmentRepository,
                             EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public void generateData() {
        Department department = new Department("Отдел закупок");
        departmentRepository.save(department);
        employeeRepository.save(new Employee(department, "Иван Иванов", "менеджер"));
        employeeRepository.save(new Employee(department, "Пётр Петров", "логист"));
        employeeRepository.save(new Employee(department, "Степан Степанов", "аналитик"));
        department = new Department("Отдел продаж");
        departmentRepository.save(department);
        employeeRepository.save(new Employee(department, "Катя Катина", "менеджер"));
        employeeRepository.save(new Employee(department, "Ольга Ольгина", "менеджер"));
        employeeRepository.save(new Employee(department, "Женя Женина", "менеджер"));
        employeeRepository.save(new Employee(department, "Анжелика Ангел", "менеджер"));
        department = new Department("Отдел маркетинга");
        departmentRepository.save(department);
        employeeRepository.save(new Employee(department, "Валентин Валентинов", "менеджер"));
        employeeRepository.save(new Employee(department, "Николай Николаев", "маркетолог"));
        employeeRepository.save(new Employee(department, "Антон Антонов", "аналитик"));
    }
}