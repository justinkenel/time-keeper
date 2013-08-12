package com.timekeep.front;

import com.timekeep.connect.GroupConnector;
import com.timekeep.data.Group;
import com.timekeep.front.util.FillComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupFormPresenter {
  public final static JPanel view;

  private final static JTextField groupNameField;
  private final static JButton submitButton;

  static {

    groupNameField = new JTextField();
    submitButton = new JButton("Create");

    view = FillComponent.formBuilder(submitButton).addInput("Name", groupNameField).build();

    submitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String name = groupNameField.getText();
        GroupConnector.connector(name).createAndStore();
      }
    });
  }

  public static void presentGroup(Group group) {
    groupNameField.setText(group.name);
    groupNameField.setEnabled(false);
    submitButton.setVisible(false);
  }

  public static void presentForm() {
    groupNameField.setText("");
    groupNameField.setEnabled(true);
    submitButton.setVisible(true);
  }
}
