package com.timekeep.facade;

import com.timekeep.back.EmployeeService;
import com.timekeep.data.Employee;
import com.timekeep.data.Entry;
import com.timekeep.data.StrictTime;

import java.util.ArrayList;
import java.util.List;

public class GroupFacade {
  private String name;

  private GroupFacade(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public List<EmployeeFacade> getEmployees() {
    List<EmployeeFacade> employeeList = new ArrayList<EmployeeFacade>();

    for (Employee employee : EmployeeService.getEmployees()) {
      if (name.equals(employee.group)) {
        EmployeeFacade employeeFacade = EmployeeFacade.facade(employee.name);
        employeeList.add(employeeFacade);
      }
    }

    return employeeList;
  }

  public Entry addEntry(Entry entry) {
    Iterable<EmployeeFacade> employeeList = getEmployees();

    for (EmployeeFacade facade : employeeList) {
      facade.addEntry(entry);
    }

    return entry;
  }

  public void endEntry(StrictTime endTime) {
    Iterable<EmployeeFacade> employeeList = getEmployees();

    for (EmployeeFacade facade : employeeList) {
      facade.endEntry(endTime);
    }
  }

  public static GroupFacade facade(String name) {
    return new GroupFacade(name);
  }
}
