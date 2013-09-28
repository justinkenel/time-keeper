package com.timekeep.front.menu;

import com.timekeep.back.JobsiteService;
import com.timekeep.data.Jobsite;

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

    editMenu.add(jobsiteItem);

    view.add(editMenu);
  }
}
