package com.nauka.repository;

import com.nauka.domain.Calendar;
import org.springframework.data.repository.CrudRepository;

public interface CalendarRepository extends CrudRepository<Calendar, Long> {

    Calendar findByMonthName(String monthName);
}
