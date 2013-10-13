package com.timekeep.summary;

import com.timekeep.back.DateService;
import com.timekeep.back.EmployeeService;
import com.timekeep.back.GroupService;
import com.timekeep.data.*;
import com.timekeep.facade.EmployeeFacade;
import com.timekeep.facade.GroupFacade;

import java.util.ArrayList;
import java.util.List;

public class PayrollSummary {
  public static class EmployeeSummary {
    public final Employee employee;
    public final List<Entry> entryList;

    private EmployeeSummary(Employee employee, List<Entry> entryList) {
      this.employee = employee;
      this.entryList = entryList;
    }

    public EmployeeSummary getEmployeeSummaryForDate(StrictDate date) {
      List<Entry> entryListForDate = new ArrayList<Entry>();
      for (Entry entry : entryList) {
        if (entry.date.equals(date)) {
          entryListForDate.add(entry);
        }
      }
      return new EmployeeSummary(employee, entryListForDate);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof EmployeeSummary)) return false;

      EmployeeSummary that = (EmployeeSummary) o;

      if (employee != null ? !employee.equals(that.employee) : that.employee != null) return false;
      if (entryList != null ? !entryList.equals(that.entryList) : that.entryList != null) return false;

      return true;
    }

    @Override
    public int hashCode() {
      int result = employee != null ? employee.hashCode() : 0;
      result = 31 * result + (entryList != null ? entryList.hashCode() : 0);
      return result;
    }

    @Override
    public String toString() {
      return "EmployeeSummary{" +
          "employee=" + employee +
          ", entryList=" + entryList +
          '}';
    }
  }

  public static class GroupSummary {
    public final Group group;
    public final List<EmployeeSummary> employeeSummaryList;

    private GroupSummary(Group group, List<EmployeeSummary> employeeSummaryList) {
      this.group = group;
      this.employeeSummaryList = employeeSummaryList;
    }

    public GroupSummary getGroupSummaryForDate(StrictDate date) {
      List<EmployeeSummary> employeeSummaryListForDate = new ArrayList<EmployeeSummary>();

      for (EmployeeSummary employeeSummary : employeeSummaryList) {
        EmployeeSummary employeeSummaryForDate = employeeSummary.getEmployeeSummaryForDate(date);
        employeeSummaryListForDate.add(employeeSummaryForDate);
      }

      return new GroupSummary(group, employeeSummaryListForDate);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof GroupSummary)) return false;

      GroupSummary that = (GroupSummary) o;

      if (employeeSummaryList != null ? !employeeSummaryList.equals(that.employeeSummaryList) : that.employeeSummaryList != null)
        return false;
      if (group != null ? !group.equals(that.group) : that.group != null) return false;

      return true;
    }

    @Override
    public int hashCode() {
      int result = group != null ? group.hashCode() : 0;
      result = 31 * result + (employeeSummaryList != null ? employeeSummaryList.hashCode() : 0);
      return result;
    }

    @Override
    public String toString() {
      return "GroupSummary{" +
          "group=" + group +
          ", employeeSummaryList=" + employeeSummaryList +
          '}';
    }
  }

  public final List<GroupSummary> groupSummaryList;

  private PayrollSummary(List<GroupSummary> groupSummaryList) {
    this.groupSummaryList = groupSummaryList;
  }

  private static EmployeeSummary buildEmployeeSummary(EmployeeFacade facade, Entry startEntry, Entry endEntry) {
    Employee employee = EmployeeService.getEmployee(facade.getName());

    List<Entry> entryList = facade.getEntries();
    List<Entry> summaryEntryList = new ArrayList<Entry>();

    for (Entry entry : entryList) {
      if (entry.between(startEntry, endEntry)) {
        summaryEntryList.add(entry);
      }
    }

    return new EmployeeSummary(employee, summaryEntryList);
  }

  private static GroupSummary buildGroupSummary(GroupFacade facade, Entry startEntry, Entry endEntry) {
    Group group = GroupService.load(facade.getName());

    Iterable<EmployeeFacade> employeeFacadeList = facade.getEmployees();
    List<EmployeeSummary> employeeSummaryList = new ArrayList<EmployeeSummary>();

    for (EmployeeFacade employeeFacade : employeeFacadeList) {
      EmployeeSummary employeeSummary = buildEmployeeSummary(employeeFacade, startEntry, endEntry);
      employeeSummaryList.add(employeeSummary);
    }

    return new GroupSummary(group, employeeSummaryList);
  }

  public static PayrollSummary build(StrictDate start, StrictDate end) {
    List<GroupSummary> groupSummaryList = new ArrayList<GroupSummary>();
    Iterable<Group> groupList = GroupService.getGroups();

    StrictTime zeroTime = DateService.ZERO_TIME;
    Entry startEntry = new Entry(start, zeroTime, zeroTime, "", zeroTime);
    Entry endEntry = new Entry(end, zeroTime, zeroTime, "", zeroTime);

    for (Group group : groupList) {
      GroupFacade groupFacade = GroupFacade.facade(group.name);
      GroupSummary groupSummary = buildGroupSummary(groupFacade, startEntry, endEntry);
      groupSummaryList.add(groupSummary);
    }

    return new PayrollSummary(groupSummaryList);
  }

  public PayrollSummary buildForDate(StrictDate date) {
    List<GroupSummary> groupSummaryListForDate = new ArrayList<GroupSummary>();
    for (GroupSummary groupSummary : groupSummaryList) {
      groupSummaryListForDate.add(groupSummary.getGroupSummaryForDate(date));
    }
    return new PayrollSummary(groupSummaryListForDate);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PayrollSummary)) return false;

    PayrollSummary that = (PayrollSummary) o;

    if (groupSummaryList != null ? !groupSummaryList.equals(that.groupSummaryList) : that.groupSummaryList != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    return groupSummaryList != null ? groupSummaryList.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "PayrollSummary{" +
        "groupSummaryList=" + groupSummaryList +
        '}';
  }
}
