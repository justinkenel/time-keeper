package com.timekeep.front.menu;

import com.timekeep.back.JobsiteService;
import com.timekeep.data.Jobsite;
import com.timekeep.front.PrimaryPresenter;
import com.timekeep.front.dialog.EmployeeTypeModalPresenter;
import com.timekeep.front.dialog.JobsiteModalPresenter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBarPresenter {
  public static final JMenuBar view;

  static {
    view = new JMenuBar();

    final JMenu editMenu = new JMenu("File");

    final JMenuItem jobsiteItem = new JMenuItem("New Jobsite");

    jobsiteItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        final String name = JOptionPane.showInputDialog("Enter Jobsite Name");

        if (name == null || name.isEmpty()) {
          return;
        }

        final String state = JOptionPane.showInputDialog("Enter Jobsite State");

        if (state == null || state.isEmpty()) {
          return;
        }

        final Jobsite jobsite = new Jobsite(name, state);

        JobsiteService.storeJobsite(jobsite);
      }
    });

    final JMenuItem jobsiteListItem = new JMenuItem("Jobsites");
    jobsiteListItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JobsiteModalPresenter presenter = JobsiteModalPresenter.build(PrimaryPresenter.primaryView);
        presenter.view.setVisible(true);
      }
    });

    final JMenuItem employeeTypeListItem = new JMenuItem("Types");
    employeeTypeListItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        EmployeeTypeModalPresenter presenter = EmployeeTypeModalPresenter.build(PrimaryPresenter.primaryView);
        presenter.view.setVisible(true);
      }
    });

    editMenu.add(jobsiteListItem);
    editMenu.add(employeeTypeListItem);

    view.add(editMenu);
  }
}
