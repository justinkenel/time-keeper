package com.timekeep.front;

import com.timekeep.connect.EmployeeConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClockPresenter {
  public final JPanel view;

  private final JButton clockIn;
  private final JButton clockOut;

  public static ClockPresenter build() {
    JPanel view = new JPanel();

    JButton clockIn = new JButton("Clock In");
    JButton clockOut = new JButton("Clock Out");

    view.setLayout(new FlowLayout());

    view.add(clockIn);
    view.add(clockOut);

    return new ClockPresenter(view, clockIn, clockOut);
  }

  private ClockPresenter(JPanel view, JButton clockIn, JButton clockOut) {
    this.view = view;
    this.clockIn = clockIn;
    this.clockOut = clockOut;
  }

  public void setClockTarget(final String employeeName) {
    removeAllListeners(clockIn);
    removeAllListeners(clockOut);

    clockIn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        clockIn.setEnabled(false);
        clockOut.setEnabled(true);
        EmployeeConnector.connector(employeeName).addStartEntry();
      }
    });

    clockOut.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        clockIn.setEnabled(true);
        clockOut.setEnabled(false);
        EmployeeConnector.connector(employeeName).endEntry();
      }
    });
  }

  private void removeAllListeners(JButton button) {
    for (ActionListener listener : button.getActionListeners()) {
      button.removeActionListener(listener);
    }
  }
}
