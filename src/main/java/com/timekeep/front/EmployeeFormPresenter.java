package com.timekeep.front;

import com.timekeep.back.DateService;
import com.timekeep.back.EntryService;
import com.timekeep.back.GroupService;
import com.timekeep.back.RateService;
import com.timekeep.connect.EmployeeConnector;
import com.timekeep.data.Employee;
import com.timekeep.data.Entry;
import com.timekeep.data.Group;
import com.timekeep.data.Rate;
import com.timekeep.front.util.FillComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class EmployeeFormPresenter {
  public static JPanel view;

  private static JTextField nameField;
  private static JTextField rateField;
  private static JComboBox groupField;

  private static JButton button;

  private static EditableRowTablePresenter<Entry> entryTable;

  private static ClockPresenter clock;

  static {
    button = new JButton("Create");

    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String rateString = rateField.getText();
        String[] parts = rateString.split("\\.");
        int rateValue = Integer.parseInt(parts[0]) * 100;
        if (parts.length == 2) {
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

    JPanel form = FillComponent.formBuilder(button).
        addInput("Name", nameField).
        addInput("Rate", rateField).
        addInput("Group", groupField).
        build();

    view = form;

    entryTable = EditableRowTablePresenter.<Entry>builder().
        addHeader("Date").
        addHeader("Start").
        addHeader("End").
        setEntityToRowConverter(new EditableRowTablePresenter.EntityToRowConverter<Entry>() {
          @Override
          public Vector<String> convertToRow(Entry entity) {
            //return new String[]{DateService.standardString(entity.date)};
            Vector<String> vector = new Vector<>();
            vector.add(DateService.standardString(entity.date));
            vector.add(DateService.standardString(entity.start));
            vector.add(DateService.standardString(entity.end));
            return vector;
          }
        }).build();

    //view = FillComponent.horizontalFillBuilder().addCalculatedComponent(form).build();

    clock = ClockPresenter.build();

    view = FillComponent.verticalFillBuilder().
        addGivenComponent(form).
        addGivenComponent(clock.view).
        addCalculatedComponent(entryTable.view).
        build();

    //view = entryTable.view;
  }

  public static void presentEmployee(Employee employee) {
    nameField.setText(employee.name);

    Iterable<Rate> rateList = RateService.retrieve(employee.name);
    Rate rate = rateList.iterator().next();
    rateField.setText(String.valueOf(rate.rate));

    DefaultComboBoxModel model = new DefaultComboBoxModel();
    model.addElement(employee.group);
    groupField.setModel(model);

    nameField.setEnabled(false);
    rateField.setEnabled(false);
    groupField.setEnabled(false);

    Iterable<Entry> entryList = EntryService.retrieve(employee.name);
    entryTable.setValues(entryList);

    entryTable.view.setVisible(true);

    clock.view.setVisible(true);
    clock.setClockTarget(employee.name);

    button.setVisible(false);
  }

  public static void presentForm() {
    nameField.setText("");
    rateField.setText("");

    DefaultComboBoxModel model = new DefaultComboBoxModel();
    for (Group group : GroupService.getGroups()) {
      model.addElement(group.name);
    }

    groupField.setModel(model);

    nameField.setEnabled(true);
    rateField.setEnabled(true);
    groupField.setEnabled(true);

    button.setVisible(true);

    clock.view.setVisible(false);
    entryTable.view.setVisible(false);
  }
}
