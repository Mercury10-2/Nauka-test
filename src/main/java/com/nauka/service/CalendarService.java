package com.nauka.service;

import com.nauka.domain.Calendar;
import com.nauka.domain.Employee;
import com.nauka.repository.CalendarRepository;
import com.nauka.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class CalendarService {

    private CalendarRepository calendarRepository;
    private EmployeeRepository employeeRepository;

    public CalendarService(CalendarRepository calendarRepository, EmployeeRepository employeeRepository) {
        this.calendarRepository = calendarRepository;
        this.employeeRepository = employeeRepository;
    }

    public void initializeCalendar() {
        int currentYear = Year.now().getValue();
        String[] months = new String[] {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
                "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        boolean[] days;
        String month;

        //  for each month...
        for (int i = 0; i < 12; i++) {

            //  Calculates number of days in particular month
            days = new boolean[YearMonth.of(currentYear, i + 1).lengthOfMonth()];

            //  Ensures that string representation of month consists of two symbols
            month = String.valueOf(i + 1);
            month = month.length() < 2 ? "0" + month : month;

            setWeekends(days, String.valueOf(currentYear + "-" + month + "-" + "01"));
            setHolidays(days, months[i]);
            calendarRepository.save(new Calendar(months[i], days, getDates(days)));
        }
    }

    private void setWeekends(boolean[] days, String initialDate) {
        LocalDate date = LocalDate.parse(initialDate);
        String value;
        for (int day = 0; day < days.length; day++) {
            value = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            if (value.equalsIgnoreCase("Saturday") || value.equalsIgnoreCase("Sunday"))
                days[day] = true;
            date = date.plusDays(1);
        }
    }

    private void setHolidays(boolean[] days, String month) {
        if (month.equalsIgnoreCase("Январь"))
            for (int i = 0; i < 8; i++)
                days[i] = true;
        else if (month.equalsIgnoreCase("Февраль"))
            days[23] = true;
        else if (month.equalsIgnoreCase("Март"))
            days[8] = true;
        else if (month.equalsIgnoreCase("Май")) {
            for (int i = 0; i < 5; i++)
                days[i] = true;
            days[10] = true;
        }
        else if (month.equalsIgnoreCase("Июнь")) {
            days[10] = true;
            days[11] = true;
        }
        else if (month.equalsIgnoreCase("Ноябрь")) {
            days[2] = true;
            days[3] = true;
        }
        else if (month.equalsIgnoreCase("Декабрь"))
            days[30] = true;
    }

    public Map<Long, List<String>> generateEmployeeStats(boolean[] days) {
        Map<Long, List<String>> employeeTimeSchedule = new HashMap<>();
        List<String> list;
        Iterable<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            list = new ArrayList<>(days.length + 1);
            for (boolean b : days) {
                if (!b) {
                    list.add("Я");
                }
                else {
                    list.add("В");
                }
            }

            //  Random vacations generation
            if (Math.random() < 0.3)
                vacationsAndTripsGenerator(list, "ОТ");

            //  Random business trip generation
            if (Math.random() < 0.3)
                vacationsAndTripsGenerator(list, "К");

            //  Collects employee monthly statistics
            getStats(list);

            employeeTimeSchedule.put(employee.getEmpId(), list);
        }
        return employeeTimeSchedule;
    }

    public int[] getDates(boolean[] days) {
        int[] dates = new int[days.length];
        for (int i = 0; i < dates.length; i++)
            dates[i] = i + 1;
        return dates;
    }

    private void vacationsAndTripsGenerator(List<String> list, String type) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).equals("В") && list.get(i).equals("Я")) {
                if (Math.random() < 0.3) {
                    for (int j = i; j < list.size(); j++) {
                        if (list.get(j).equals("Я"))
                            list.set(j, type);
                        else
                            break;
                    }
                    break;
                }
            }
        }
    }

    private void getStats(List<String> list) {
        Map<String, Integer> stats = new HashMap<>();
        StringBuilder sb = new StringBuilder();

        for (String str : list) {
            if (!str.equals("В")) {
                if (!stats.containsKey(str))
                    stats.put(str, 1);
                else
                    stats.put(str, stats.get(str) + 1);
            }
        }

        for (Map.Entry<String, Integer> entry : stats.entrySet())
            sb.append(entry.getKey()).append("(").append(entry.getValue()).append(") ");

        list.add(sb.toString());
    }
}
