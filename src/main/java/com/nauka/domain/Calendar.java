package com.nauka.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long monthId;

    private String monthName;
    private boolean[] days;
    private int[] dates;

    public Calendar() {
    }

    public Calendar(String monthName, boolean[] days, int[] dates) {
        this.monthName = monthName;
        this.days = days;
        this.dates = dates;
    }

    public Long getMonthId() {
        return monthId;
    }

    public void setMonthId(Long monthId) {
        this.monthId = monthId;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public boolean[] getDays() {
        return days;
    }

    public void setDays(boolean[] days) {
        this.days = days;
    }

    public int[] getDates() {
        return dates;
    }

    public void setDates(int[] dates) {
        this.dates = dates;
    }
}
