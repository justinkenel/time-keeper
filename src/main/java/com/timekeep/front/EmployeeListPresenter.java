package com.timekeep.front;

import com.timekeep.data.Employee;

import javax.swing.*;

public class EmployeeListPresenter {
  public final JList view;
  private final DefaultListModel<String> model;

  private EmployeeListPresenter(JList view, DefaultListModel<String> model) {
    this.view = view;
    this.model = model;
  }

  public static EmployeeListPresenter build() {
    DefaultListModel<String> model = new DefaultListModel<String>();
    JList view = new JList();
    return new EmployeeListPresenter(view, model);
  }

  public void addEmployee(Employee employee) {
    model.addElement(employee.name);
  }

  public void addEmployees(Iterable<Employee> employeeList) {
    for(Employee employee : employeeList) {
      model.addElement(employee.name);
    }
  }

  public void clear() {
    model.clear();
  }
}
