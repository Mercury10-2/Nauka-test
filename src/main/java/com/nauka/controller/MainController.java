package com.nauka.controller;

import com.nauka.domain.Calendar;
import com.nauka.domain.Department;
import com.nauka.domain.Employee;
import com.nauka.repository.CalendarRepository;
import com.nauka.repository.DepartmentRepository;
import com.nauka.repository.EmployeeRepository;
import com.nauka.service.CalendarService;
import com.nauka.service.FakeDataGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    private DepartmentRepository departmentRepository;
    private EmployeeRepository employeeRepository;
    private CalendarRepository calendarRepository;
    private FakeDataGenerator fakeDataGenerator;
    private CalendarService calendarService;

    public MainController(DepartmentRepository departmentRepository,
                          EmployeeRepository employeeRepository,
                          CalendarRepository calendarRepository,
                          FakeDataGenerator fakeDataGenerator,
                          CalendarService calendarService) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.calendarRepository = calendarRepository;
        this.fakeDataGenerator = fakeDataGenerator;
        this.calendarService = calendarService;
    }

    @GetMapping("/")
    public String starter(Map<String, Object> model) {
        Iterable<Department> departments = departmentRepository.findAll();

        //  Creates test data if databases are empty
        if (!departments.iterator().hasNext()) {
            fakeDataGenerator.generateData();
            departments = departmentRepository.findAll();
        }

        //  Selects default department for the view
        Department department = departments.iterator().next();

        Iterable<Employee> employees = employeeRepository.findAllByDepartment(department);

        Iterable<Calendar> calendar = calendarRepository.findAll();

        //  Creates calendar entities if there are none
        if (!calendar.iterator().hasNext()) {
            calendarService.initializeCalendar();
            calendar = calendarRepository.findAll();
        }

        //  Gets weekend schedule for January
        Calendar month = calendarRepository.findByMonthName("Январь");
        boolean[] days = month.getDays();

        //  Generates dates for January
        int[] dates = calendarService.getDates(days);

        //  Generates fake monthly time stats for employees
        Map<Long, List<String>> employeeTimeSchedule = calendarService.generateEmployeeStats(days);

        model.put("chosenDept", department);
        model.put("departments", departments);
        model.put("employees", employees);
        model.put("months", calendar);
        model.put("chosenMonth", month);
        model.put("days", days);
        model.put("dates", dates);
        model.put("empStats", employeeTimeSchedule);
        return "table";
    }

    @PostMapping("/")
    public String chooseDept(
            @RequestParam(value = "selectedDepartment", required = false) String deptName,
            @RequestParam(value = "selectedMonth", required = false) String selectedMonth,
            Map<String, Object> model) {
        Iterable<Department> departments = departmentRepository.findAll();
        Department department = departmentRepository.findByDeptName(deptName);
        Iterable<Employee> employees = employeeRepository.findAllByDepartment(department);
        Iterable<Calendar> calendar = calendarRepository.findAll();

        //  Gets weekend schedule for the month
        Calendar month = calendarRepository.findByMonthName(selectedMonth);
        boolean[] days = month.getDays();

        //  Generates dates for the month
        int[] dates = calendarService.getDates(days);

        //  Generates fake monthly time stats for employees
        Map<Long, List<String>> employeeTimeSchedule = calendarService.generateEmployeeStats(days);

        model.put("chosenDept", department);
        model.put("departments", departments);
        model.put("employees", employees);
        model.put("months", calendar);
        model.put("chosenMonth", month);
        model.put("days", days);
        model.put("dates", dates);
        model.put("empStats", employeeTimeSchedule);
        return "table";
    }
}
