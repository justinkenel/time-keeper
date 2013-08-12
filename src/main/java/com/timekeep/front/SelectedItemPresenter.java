package com.timekeep.front;

import com.timekeep.data.Employee;
import com.timekeep.data.Group;
import com.timekeep.front.util.ComponentFillLayout;

import javax.swing.*;
import java.awt.*;

public class SelectedItemPresenter {
  public static final JPanel view;

  static {
    view = new JPanel();
  }

  public static void presentNewGroup() {
    GroupFormPresenter.presentForm();
    showView(GroupFormPresenter.view);
  }

  public static void presentGroup(Group group) {
    GroupFormPresenter.presentGroup(group);
    showView(GroupFormPresenter.view);
  }

  public static void presentNewEmployee() {
    EmployeeFormPresenter.presentForm();
    showView(EmployeeFormPresenter.view);
  }

  public static void presentEmployee(Employee employee) {

  }

  private static void showView(Component component) {
    view.removeAll();
    view.add(component);
    view.setLayout(ComponentFillLayout.verticalBuilder().addCalculatedComponent(component).build());
    PrimaryPresenter.refresh();
  }

  public static void presentNothing() {
    view.removeAll();
    PrimaryPresenter.refresh();
  }
}
