package com.timekeep.facade;

import com.timekeep.back.DateService;
import com.timekeep.back.EmployeeService;
import com.timekeep.back.EntryService;
import com.timekeep.back.GroupService;
import com.timekeep.data.*;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GroupFacadeTest {
  private static void clear() {
    GroupService.clear();
    EntryService.clear();
    EmployeeService.clear();
  }

  @BeforeClass
  public static void beforeClass() {
    clear();

    Group group1 = new Group("group1");
    Group group2 = new Group("group2");

    GroupService.store(group1);
    GroupService.store(group2);

    Employee employee1 = new Employee("employee1", "group1", "type1");
    Employee employee2 = new Employee("employee2", "group2", "type1");
    Employee employee3 = new Employee("employee3", "group1", "type1");

    EmployeeService.storeEmployee(employee1);
    EmployeeService.storeEmployee(employee2);
    EmployeeService.storeEmployee(employee3);
  }

  @AfterClass
  public static void after() {
    clear();
  }

  @Test
  public void testAddEntry() {
    StrictDate date = new StrictDate(1, 2, 3);
    StrictTime time = new StrictTime(4, 5);
    Entry newEntry = new Entry(date, time, DateService.INVALID_TIME, "", DateService.INVALID_TIME);

    GroupFacade.facade("group1").addEntry(newEntry);

    List<Entry> employee1EntryList = EntryService.retrieve("employee1");
    List<Entry> employee2EntryList = EntryService.retrieve("employee2");
    List<Entry> employee3EntryList = EntryService.retrieve("employee3");

    List<Entry> expected = new ArrayList<Entry>();
    expected.add(newEntry);

    Assert.assertEquals(expected, employee1EntryList);
    Assert.assertTrue(employee2EntryList.isEmpty());
    Assert.assertEquals(expected, employee3EntryList);
  }

  @Test
  public void testGetEmployees() {
    Iterable<EmployeeFacade> employeeFacadeList = GroupFacade.facade("group1").getEmployees();
    List<String> employeeNames = new ArrayList<String>();

    for (EmployeeFacade facade : employeeFacadeList) {
      employeeNames.add(facade.getName());
    }

    Assert.assertEquals(2, employeeNames.size());

    Assert.assertTrue(employeeNames.contains("employee1"));
    Assert.assertTrue(employeeNames.contains("employee3"));
  }
}
