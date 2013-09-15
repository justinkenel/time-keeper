package com.timekeep.front;

import com.timekeep.connect.GroupConnector;
import com.timekeep.data.Group;
import com.timekeep.front.util.FillComponent;
import com.timekeep.front.util.SingleFillLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupFormPresenter {
  public final static JPanel view;

  private final static JPanel form;

  private final static JTextField groupNameField;
  private final static JButton submitButton;

  private final static JPanel modificationContainer;

  static {

    groupNameField = new JTextField();
    submitButton = new JButton("Create");

    form = FillComponent.formBuilder(submitButton).addInput("Name", groupNameField).build();

    submitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String name = groupNameField.getText();
        GroupConnector.connector(name).createAndStore();
      }
    });

    modificationContainer = SingleFillLayout.componentFillPanel();

    view = FillComponent.verticalFillBuilder().
        addGivenComponent(form).
        addCalculatedComponent(modificationContainer).
        build();

    view.setName("GroupFormPresenter");
  }

  public static void presentGroup(Group group) {
    groupNameField.setText(group.name);
    groupNameField.setEnabled(false);
    submitButton.setVisible(false);

    modificationContainer.removeAll();
    modificationContainer.add(GroupModificationPresenter.build(group.name).view);
  }

  public static void presentForm() {
    groupNameField.setText("");
    groupNameField.setEnabled(true);
    submitButton.setVisible(true);

    modificationContainer.removeAll();
  }
}
