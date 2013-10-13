package com.timekeep.summary;

import com.timekeep.back.*;
import com.timekeep.data.*;
import com.timekeep.facade.EmployeeFacade;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PayrollSummaryTest {
  public static GroupHelper groupHelper = new GroupHelper();
  public static EmployeeHelper employeeHelper = groupHelper.employeeHelper;

  private static Group group1;
  private static Group group2;

  private static Entry entry1;
  private static Entry entry2;

  private static Employee employee1;
  private static Employee employee2;
  private static Employee employee3;
  private static Employee employee4;
  private static Employee employee5;

  private static StrictDate date1 = new StrictDate(1, 2, 3);
  private static StrictDate date2 = new StrictDate(1, 2, 4);
  private static StrictDate date3 = new StrictDate(1, 2, 5);
  private static StrictDate date4 = new StrictDate(1, 2, 6);
  private static Entry entry3;

  @BeforeClass
  public static void beforeClass() {
    clear();

    entry1 = new Entry(date1, new StrictTime(1, 2), new StrictTime(3, 4),
        "jobsite", new StrictTime(5, 6));

    group1 = groupHelper.builder().setName("group-1").buildAndSave();

    employee1 = employeeHelper.builder().setGroup(group1.name).setName("employee-1").buildAndSave();
    employee2 = employeeHelper.builder().setGroup(group1.name).setName("employee-2").buildAndSave();
    employee3 = employeeHelper.builder().setGroup(group1.name).setName("employee-3").buildAndSave();

    EmployeeFacade.facade(employee1.name).addEntry(entry1);
    EmployeeFacade.facade(employee2.name).addEntry(entry1);
    EmployeeFacade.facade(employee3.name).addEntry(entry1);

    entry2 = new Entry(date2, new StrictTime(7, 8), new StrictTime(9, 10),
        "jobsite", new StrictTime(11, 12));

    entry3 = new Entry(date4, new StrictTime(7, 8), new StrictTime(9, 10),
        "jobsite", new StrictTime(11, 12));

    group2 = groupHelper.builder().setName("group-2").buildAndSave();

    employee4 = employeeHelper.builder().setGroup(group2.name).setName("employee-4").buildAndSave();
    employee5 = employeeHelper.builder().setGroup(group2.name).setName("employee-5").buildAndSave();

    EmployeeFacade.facade(employee4.name).addEntry(entry1);
    EmployeeFacade.facade(employee5.name).addEntry(entry1);

    EmployeeFacade.facade(employee4.name).addEntry(entry2);
    EmployeeFacade.facade(employee5.name).addEntry(entry2);

    EmployeeFacade.facade(employee4.name).addEntry(entry3);
    EmployeeFacade.facade(employee5.name).addEntry(entry3);
  }

  @AfterClass
  public static void afterClass() {
    clear();
  }

  @Test
  public void testBuildPayrollSummary() {
    PayrollSummary payrollSummary = PayrollSummary.build(date1, date3);

    Assert.assertEquals(2, payrollSummary.groupSummaryList.size());

    PayrollSummary.GroupSummary groupSummary1 = payrollSummary.groupSummaryList.get(0);
    Assert.assertEquals(3, groupSummary1.employeeSummaryList.size());

    PayrollSummary.EmployeeSummary employeeSummary1 = groupSummary1.employeeSummaryList.get(0);
    Assert.assertEquals(1, employeeSummary1.entryList.size());
    Assert.assertEquals(employee1, employeeSummary1.employee);
    Assert.assertEquals(entry1, employeeSummary1.entryList.get(0));

    PayrollSummary.GroupSummary groupSummary2 = payrollSummary.groupSummaryList.get(1);
    Assert.assertEquals(2, groupSummary2.employeeSummaryList.size());

    PayrollSummary.EmployeeSummary employeeSummary4 = groupSummary2.employeeSummaryList.get(0);
    Assert.assertEquals(2, employeeSummary4.entryList.size());
    Assert.assertEquals(employee4, employeeSummary4.employee);
    Assert.assertEquals(entry1, employeeSummary4.entryList.get(1));
    Assert.assertEquals(entry2, employeeSummary4.entryList.get(0));
  }

  @Test
  public void testBuildForDate_EntryExistsForDate() {
    PayrollSummary payrollSummary = PayrollSummary.build(date1, date3);

    PayrollSummary payrollSummaryForDate = payrollSummary.buildForDate(date1);

    Assert.assertEquals(2, payrollSummaryForDate.groupSummaryList.size());

    PayrollSummary.GroupSummary groupSummary1 = payrollSummaryForDate.groupSummaryList.get(0);
    Assert.assertEquals(3, groupSummary1.employeeSummaryList.size());

    PayrollSummary.EmployeeSummary employeeSummary1 = groupSummary1.employeeSummaryList.get(0);
    Assert.assertEquals(1, employeeSummary1.entryList.size());
    Assert.assertEquals(employee1, employeeSummary1.employee);
    Assert.assertEquals(entry1, employeeSummary1.entryList.get(0));

    PayrollSummary.GroupSummary groupSummary2 = payrollSummaryForDate.groupSummaryList.get(1);
    Assert.assertEquals(2, groupSummary2.employeeSummaryList.size());

    PayrollSummary.EmployeeSummary employeeSummary4 = groupSummary2.employeeSummaryList.get(0);
    Assert.assertEquals(1, employeeSummary4.entryList.size());
    Assert.assertEquals(employee4, employeeSummary4.employee);
    Assert.assertEquals(entry1, employeeSummary4.entryList.get(0));
  }

  @Test
  public void testBuildForDate_EntryMissingForDate() {
    PayrollSummary payrollSummary = PayrollSummary.build(date1, date3);

    PayrollSummary payrollSummaryForDate = payrollSummary.buildForDate(date2);

    Assert.assertEquals(2, payrollSummaryForDate.groupSummaryList.size());

    PayrollSummary.GroupSummary groupSummary1 = payrollSummaryForDate.groupSummaryList.get(0);
    Assert.assertEquals(3, groupSummary1.employeeSummaryList.size());

    PayrollSummary.EmployeeSummary employeeSummary1 = groupSummary1.employeeSummaryList.get(0);
    Assert.assertTrue(employeeSummary1.entryList.isEmpty());
    Assert.assertEquals(employee1, employeeSummary1.employee);

    PayrollSummary.GroupSummary groupSummary2 = payrollSummaryForDate.groupSummaryList.get(1);
    Assert.assertEquals(2, groupSummary2.employeeSummaryList.size());

    PayrollSummary.EmployeeSummary employeeSummary4 = groupSummary2.employeeSummaryList.get(0);
    Assert.assertEquals(1, employeeSummary4.entryList.size());
    Assert.assertEquals(employee4, employeeSummary4.employee);
    Assert.assertEquals(entry2, employeeSummary4.entryList.get(0));
  }

  private static void clear() {
    EmployeeService.clear();
    GroupService.clear();
    EntryService.clear();
  }
}
