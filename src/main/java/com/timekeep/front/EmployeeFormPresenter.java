package com.timekeep.front;

import com.timekeep.back.GroupService;
import com.timekeep.data.Group;
import com.timekeep.front.util.FillComponent;
import com.timekeep.front.util.FormFillLayout;

import javax.swing.*;

public class EmployeeFormPresenter {
  public static JPanel view;

  private static JTextField nameField;
  private static JTextField rateField;
  private static JComboBox groupField;

  static {
    JButton button = new JButton("Create");

    view = FillComponent.formBuilder(button).
        addInput("Name", nameField).
        addInput("Rate", rateField).
        addInput("Group", groupField).
        build();
  }

  public static void presentForm() {
    nameField.setText("");
    rateField.setText("");

    DefaultComboBoxModel model = new DefaultComboBoxModel();
    for(Group group : GroupService.getGroups()) {
      model.addElement(group.name);
    }

    groupField.setModel(model);
  }
}
