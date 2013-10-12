package com.timekeep.front;

import com.timekeep.connect.EmployeeConnector;
import com.timekeep.connect.GroupConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClockPresenter {
  public final JPanel view;

  private final JButton clockIn;
  private final JButton clockOut;

  private final ClockBehavior clockBehavior;

  private enum ClockState {DISABLE_CLOCK_IN, DISABLE_CLOCK_OUT, DISABLE_NONE}

  ;

  private static interface ClockBehavior {
    void clockIn(String target);

    void clockOut(String target);

    ClockState getClockState(String target);
  }

  private static class EmployeeClockBehavior implements ClockBehavior {
    @Override
    public void clockIn(String target) {
      EmployeeConnector.connector(target).addStartEntry();
    }

    @Override
    public void clockOut(String target) {
      EmployeeConnector.connector(target).endEntry();
    }

    @Override
    public ClockState getClockState(String target) {
      return ClockState.DISABLE_NONE;
    }
  }

  private static class GroupClockBehavior implements ClockBehavior {
    @Override
    public void clockIn(String target) {
      GroupConnector.connector(target).addStartEntry();
    }

    @Override
    public void clockOut(String target) {
      GroupConnector.connector(target).endEntry();
    }

    @Override
    public ClockState getClockState(String target) {
      return ClockState.DISABLE_NONE;
    }
  }

  public static ClockPresenter buildEmployeeClock() {
    ClockBehavior employeeClockBehavior = new EmployeeClockBehavior();
    return buildClock(employeeClockBehavior);
  }

  public static ClockPresenter buildGroupClock() {
    ClockBehavior groupClockBehavior = new GroupClockBehavior();
    return buildClock(groupClockBehavior);
  }

  private static ClockPresenter buildClock(ClockBehavior clockBehavior) {
    JPanel view = new JPanel();

    JButton clockIn = new JButton("Clock In");
    JButton clockOut = new JButton("Clock Out");

    view.setLayout(new FlowLayout());

    view.add(clockIn);
    view.add(clockOut);

    return new ClockPresenter(view, clockIn, clockOut, clockBehavior);
  }

  private ClockPresenter(JPanel view, JButton clockIn, JButton clockOut, ClockBehavior clockBehavior) {
    this.view = view;
    this.clockIn = clockIn;
    this.clockOut = clockOut;
    this.clockBehavior = clockBehavior;
  }

  private void updateButtons(ClockState clockState) {
    switch (clockState) {
      case DISABLE_CLOCK_IN:
        clockIn.setEnabled(false);
        clockOut.setEnabled(true);
        break;
      case DISABLE_CLOCK_OUT:
        clockIn.setEnabled(true);
        clockOut.setEnabled(false);
        break;
      case DISABLE_NONE:
        clockIn.setEnabled(true);
        clockOut.setEnabled(true);
        break;
    }
  }

  public void setClockTarget(final String target) {
    removeAllListeners(clockIn);
    removeAllListeners(clockOut);

    clockIn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        clockBehavior.clockIn(target);
        updateButtons(clockBehavior.getClockState(target));
      }
    });

    clockOut.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        clockBehavior.clockOut(target);
        updateButtons(clockBehavior.getClockState(target));
      }
    });
  }

  private void removeAllListeners(JButton button) {
    for (ActionListener listener : button.getActionListeners()) {
      button.removeActionListener(listener);
    }
  }
}
