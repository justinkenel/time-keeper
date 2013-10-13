package com.timekeep.back;

import com.timekeep.data.Group;
import org.junit.rules.ExternalResource;

import java.util.ArrayList;
import java.util.List;

public class GroupHelper extends ExternalResource {
  @Override
  public void before() {
    GroupService.clear();
    employeeHelper.before();
  }

  @Override
  public void after() {
    GroupService.clear();
    employeeHelper.after();
  }

  public final EmployeeHelper employeeHelper = new EmployeeHelper();

  private static int NUMBER_GROUPS_CREATED = 0;

  public GroupBuilder builder() {
    String name = "group-" + NUMBER_GROUPS_CREATED++;
    return new GroupBuilder(name, new ArrayList<EmployeeHelper.EmployeeBuilder>());
  }

  public GroupBuilder groupWithNEmployees(int n) {
    GroupBuilder groupBuilder = builder();

    for (int i = 0; i < n; ++i) {
      groupBuilder.addEmployee();
    }

    return groupBuilder;
  }

  public class GroupBuilder {
    private String name;
    private List<EmployeeHelper.EmployeeBuilder> employeeBuilderList;

    public GroupBuilder(String name, List<EmployeeHelper.EmployeeBuilder> employeeBuilderList) {
      this.name = name;
      this.employeeBuilderList = employeeBuilderList;
    }

    public GroupBuilder setName(String name) {
      this.name = name;
      return this;
    }

    public GroupBuilder addEmployeeBuilder(EmployeeHelper.EmployeeBuilder employeeBuilder) {
      employeeBuilderList.add(employeeBuilder);
      return this;
    }

    public GroupBuilder addEmployee() {
      EmployeeHelper.EmployeeBuilder employeeBuilder = employeeHelper.builder();
      return this;
    }

    public Group build() {
      return new Group(name);
    }

    public Group buildAndSave() {
      Group group = build();
      GroupService.store(group);

      int employeeNumber = 0;

      for (EmployeeHelper.EmployeeBuilder employeeBuilder : employeeBuilderList) {
        employeeBuilder.setGroup(name).setName(name + "-employee-" + employeeNumber++).buildAndSave();
      }

      return group;
    }
  }
}
