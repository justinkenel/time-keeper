package com.timekeep.connect;

import com.timekeep.back.DateService;
import com.timekeep.back.EmployeeService;
import com.timekeep.data.Employee;
import com.timekeep.data.Entry;
import com.timekeep.data.Rate;
import com.timekeep.data.StrictDate;
import com.timekeep.front.EmployeeSelectionPresenter;

import java.util.LinkedList;
import java.util.List;

public class EmployeeConnector {
  private String name;
  private int rate;
  private String group;

  private EmployeeConnector(String name, int rate, String group) {
    this.name = name;
    this.rate = rate;
    this.group = group;
  }

  public static EmployeeConnector connector(String name) {
    return new EmployeeConnector(name, 0, "");
  }

  public EmployeeConnector setRate(int rate) {
    this.rate = rate;
    return this;
  }

  public EmployeeConnector setGroup(String group) {
    this.group = group;
    return this;
  }

  public Employee createAndStore() {
    Rate firstRate = new Rate(DateService.today(), rate);
    Rate[] rateList = new Rate[] {firstRate};
    Employee employee = new Employee(name, group, new Entry[0], rateList);
    EmployeeService.storeEmployee(employee);
    EmployeeSelectionPresenter.addEmployee(employee);
    return employee;
  }
}