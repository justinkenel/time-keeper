package com.timekeep.connect;

import com.timekeep.back.DateService;
import com.timekeep.back.EmployeeService;
import com.timekeep.back.EntryService;
import com.timekeep.back.RateService;
import com.timekeep.data.Employee;
import com.timekeep.data.Entry;
import com.timekeep.data.Rate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EmployeeConnectorTest {
  @After
  public void after() {
    EmployeeService.clear();
    RateService.clear();
    EntryService.clear();
  }

  @Test
  public void testCreateAndStore() {
    Employee employee = EmployeeConnector.connector("employee-name").
        setGroup("group-name").
        setType("type-name").
        setRate(100).
        createAndStore();

    Employee expected = new Employee("employee-name", "group-name", "type-name");

    Assert.assertEquals(expected, employee);

    List<Rate> expectedRateList = new ArrayList();
    expectedRateList.add(new Rate(DateService.today(), 100));

    Assert.assertEquals(expectedRateList, RateService.retrieve("employee-name"));
    Assert.assertEquals(expected, EmployeeService.getEmployee("employee-name"));

    Iterable<Entry> entryList = EntryService.retrieve("employee-name");
    Assert.assertFalse(entryList.iterator().hasNext());
  }
}
