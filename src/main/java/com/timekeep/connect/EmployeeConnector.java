package com.timekeep.connect;

import com.timekeep.back.DateService;
import com.timekeep.back.EmployeeService;
import com.timekeep.back.EntryService;
import com.timekeep.back.RateService;
import com.timekeep.data.*;
import com.timekeep.front.EmployeeSelectionPresenter;
import com.timekeep.front.SelectedItemPresenter;

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

  public Entry addStartEntry() {
    StrictDate date = DateService.today();
    StrictTime time = DateService.now();

    List<Entry> entryList = EntryService.retrieve(name);
    Entry newEntry = new Entry(date, time, DateService.INVALID, "");
    entryList.add(0, newEntry);
    EntryService.store(name, entryList);

    Employee employee = EmployeeService.getEmployee(name);
    SelectedItemPresenter.presentEmployee(employee);
    return newEntry;
  }

  public Entry endEntry() {
    StrictTime time = DateService.now();

    List<Entry> entryList = EntryService.retrieve(name);
    Entry oldEntry = entryList.get(0);
    Entry newEntry = new Entry(oldEntry.date, oldEntry.start, time, oldEntry.jobsite);
    entryList.set(0, newEntry);
    EntryService.store(name, entryList);

    Employee employee = EmployeeService.getEmployee(name);
    SelectedItemPresenter.presentEmployee(employee);
    return newEntry;
  }
}
