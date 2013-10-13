package com.timekeep.back;

import com.timekeep.data.Employee;
import org.junit.rules.ExternalResource;

public class EmployeeHelper extends ExternalResource {
  @Override
  public void before() {
    EmployeeService.clear();
  }

  @Override
  public void after() {
    EmployeeService.clear();
  }

  private static int NUMBER_EMPLOYEES_CREATED = 0;

  public EmployeeBuilder builder() {
    String name = "employee-" + NUMBER_EMPLOYEES_CREATED++;
    return new EmployeeBuilder(name, "", "employee-type");
  }

  public static class EmployeeBuilder {
    private String name;
    private String group;
    private String type;

    private EmployeeBuilder(String name, String group, String type) {
      this.name = name;
      this.group = group;
      this.type = type;
    }

    public Employee build() {
      return new Employee(name, group, type);
    }

    public Employee buildAndSave() {
      Employee employee = build();
      EmployeeService.storeEmployee(employee);
      return employee;
    }

    public EmployeeBuilder setName(String name) {
      this.name = name;
      return this;
    }

    public EmployeeBuilder setGroup(String group) {
      this.group = group;
      return this;
    }

    public EmployeeBuilder setType(String type) {
      this.type = type;
      return this;
    }
  }
}
