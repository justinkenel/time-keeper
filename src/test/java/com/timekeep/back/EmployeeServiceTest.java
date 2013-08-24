package com.timekeep.back;

import com.timekeep.data.Employee;
import com.timekeep.data.Entry;
import com.timekeep.data.Rate;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class EmployeeServiceTest {
  @BeforeClass
  public static void before() {
    EmployeeService.clear();
  }

  @After
  public void after() {
    EmployeeService.clear();
  }

  @Test
  public void testRoundTrip() {
    Employee employee = new Employee("name", "group");
    EmployeeService.storeEmployee(employee);

    Employee retrievedEmployee = EmployeeService.getEmployee("name");

    Assert.assertEquals(employee, retrievedEmployee);
    Assert.assertNotSame(employee, retrievedEmployee);
  }

  @Test
  public void testGetEmployees() {
    Employee employee1 = new Employee("name1", "group1");
    Employee employee2 = new Employee("name2", "group2");
    Employee employee3 = new Employee("name3", "group3");

    EmployeeService.storeEmployee(employee1);
    EmployeeService.storeEmployee(employee2);
    EmployeeService.storeEmployee(employee3);

    Iterable<Employee> employees = EmployeeService.getEmployees();

    ArrayList<Employee> expected = new ArrayList<Employee>();

    expected.add(employee1);
    expected.add(employee2);
    expected.add(employee3);

    Assert.assertEquals(expected, employees);
  }
}
