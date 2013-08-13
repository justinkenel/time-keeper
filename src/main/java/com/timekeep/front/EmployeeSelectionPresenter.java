package com.timekeep.front;

import com.timekeep.back.EmployeeService;
import com.timekeep.data.Employee;
import com.timekeep.data.NamedItem;
import com.timekeep.front.util.ComponentFillLayout;
import com.timekeep.front.util.FillComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeSelectionPresenter {
  private final static ItemListPresenter<Employee> employeeListPresenter;
  private final static JButton createEmployeeButton;

  public static JPanel view;

  static {
    employeeListPresenter = ItemListPresenter.build(new ItemListPresenter.ItemSelectionHandler<Employee>() {
      @Override
      public void selectItem(Employee employee) {
        SelectedItemPresenter.presentEmployee(employee);
      }
    });

    createEmployeeButton = new JButton("Create");

    createEmployeeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SelectedItemPresenter.presentNewEmployee();
      }
    });

    employeeListPresenter.setItems(EmployeeService.getEmployees());

    view = FillComponent.verticalFillBuilder().
        addCalculatedComponent(employeeListPresenter.view).
        addGivenComponent(createEmployeeButton).
        build();
  }

  public static void addEmployee(Employee employee) {
    employeeListPresenter.setItems(EmployeeService.getEmployees());
  }

  public static void clearSelection() {
    employeeListPresenter.clearSelection();
  }
}
