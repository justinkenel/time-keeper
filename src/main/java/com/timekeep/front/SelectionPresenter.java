package com.timekeep.front;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SelectionPresenter {
  public static final JTabbedPane view;

  static {
    view = new JTabbedPane();

    view.add("Employees", EmployeeSelectionPresenter.view);
    view.add("Groups", GroupSelectionPresenter.view);

    view.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        GroupSelectionPresenter.clearSelection();
        EmployeeSelectionPresenter.clearSelection();
        SelectedItemPresenter.presentNothing();
      }
    });
  }
}
