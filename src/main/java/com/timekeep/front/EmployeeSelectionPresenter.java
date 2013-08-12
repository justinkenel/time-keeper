package com.timekeep.front;

import com.timekeep.front.util.ComponentFillLayout;
import com.timekeep.front.util.FillComponent;

import javax.swing.*;

public class EmployeeSelectionPresenter {
  private final static EmployeeListPresenter employeeListPresenter;
  private final static JButton createEmployeeButton;

  public static JPanel view;

  static {
    employeeListPresenter = EmployeeListPresenter.build();
    createEmployeeButton = new JButton("Create");

    view = FillComponent.verticalFillBuilder().
        addCalculatedComponent(employeeListPresenter.view).
        addGivenComponent(createEmployeeButton).
        build();
  }
}
