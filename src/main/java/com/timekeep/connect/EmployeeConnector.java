package com.timekeep.connect;

import com.timekeep.back.DateService;
import com.timekeep.back.EmployeeService;
import com.timekeep.back.EntryService;
import com.timekeep.back.RateService;
import com.timekeep.data.Employee;
import com.timekeep.data.Entry;
import com.timekeep.data.Rate;
import com.timekeep.front.EmployeeSelectionPresenter;

import java.util.ArrayList;
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
    List<Rate> rateList = new ArrayList(1);
    rateList.add(firstRate);
    Employee employee = new Employee(name, group);

    EmployeeService.storeEmployee(employee);
    RateService.store(name, rateList);
    EntryService.store(name, new ArrayList<Entry>());

    EmployeeSelectionPresenter.addEmployee(employee);

    return employee;
  }
}
