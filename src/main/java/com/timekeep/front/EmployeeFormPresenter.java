package com.timekeep.front;

import com.timekeep.back.GroupService;
import com.timekeep.connect.EmployeeConnector;
import com.timekeep.data.Employee;
import com.timekeep.data.Group;
import com.timekeep.front.util.FillComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeFormPresenter {
  public static JPanel view;

  private static JTextField nameField;
  private static JTextField rateField;
  private static JComboBox groupField;

  private static JButton button;

  static {
    button = new JButton("Create");

    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String rateString = rateField.getText();
        String[] parts = rateString.split("\\.");
        int rateValue = Integer.parseInt(parts[0]) * 100;
        if(parts.length == 2) {
          rateValue += Integer.parseInt(parts[1]) % 100;
        }

        EmployeeConnector.connector(nameField.getText()).
            setGroup((String) groupField.getSelectedItem()).
            setRate(rateValue).
            createAndStore();
      }
    });

    nameField = new JTextField();
    rateField = new JTextField();
    groupField = new JComboBox();

    view = FillComponent.formBuilder(button).
        addInput("Name", nameField).
        addInput("Rate", rateField).
        addInput("Group", groupField).
        build();
  }

  public static void presentEmployee(Employee employee) {
    nameField.setText(employee.name);
    rateField.setText(String.valueOf(employee.rateList[employee.rateList.length-1].rate));

    DefaultComboBoxModel model = new DefaultComboBoxModel();
    model.addElement(employee.group);
    groupField.setModel(model);

    nameField.setEnabled(false);
    rateField.setEnabled(false);
    groupField.setEnabled(false);

    button.setVisible(false);
  }

  public static void presentForm() {
    nameField.setText("");
    rateField.setText("");

    DefaultComboBoxModel model = new DefaultComboBoxModel();
    for(Group group : GroupService.getGroups()) {
      model.addElement(group.name);
    }

    groupField.setModel(model);

    nameField.setEnabled(true);
    rateField.setEnabled(true);
    groupField.setEnabled(true);

    button.setVisible(true);
  }
}
