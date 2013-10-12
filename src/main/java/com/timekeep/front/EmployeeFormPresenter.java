package com.timekeep.front;

import com.timekeep.back.*;
import com.timekeep.connect.EmployeeConnector;
import com.timekeep.data.*;
import com.timekeep.front.util.FillComponent;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class EmployeeFormPresenter {
  public final JPanel view;
  private final JTextField nameField;
  private final JTextField rateField;
  private final JComboBox groupField;
  private final JComboBox typeField;
  private final JButton button;
  private final EditableRowTablePresenter entryTable;
  private final ClockPresenter clockPresenter;
  private final String employeeName;

  private EmployeeFormPresenter(JPanel view, JTextField nameField, JTextField rateField, JComboBox groupField,
                                JComboBox typeField,
                                JButton button, EditableRowTablePresenter entryTable, ClockPresenter clockPresenter,
                                String employeeName) {
    this.view = view;
    this.rateField = rateField;
    this.nameField = nameField;
    this.groupField = groupField;
    this.typeField = typeField;
    this.button = button;
    this.entryTable = entryTable;
    this.clockPresenter = clockPresenter;
    this.employeeName = employeeName;
  }

  public static EmployeeFormPresenter buildEmployeeCreateFormPresenter() {
    JButton button = new JButton("Create");

    final JTextField nameField = new JTextField();
    final JTextField rateField = new JTextField();
    final JComboBox groupField = new JComboBox();
    final JComboBox typeField = new JComboBox();

    DefaultComboBoxModel model = new DefaultComboBoxModel();
    for (Group group : GroupService.getGroups()) {
      model.addElement(group.name);
    }
    groupField.setModel(model);

    DefaultComboBoxModel typeModel = new DefaultComboBoxModel();
    for (EmployeeType employeeType : PersistentServices.employeeTypeService.getAll()) {
      typeModel.addElement(employeeType.name);
    }
    typeField.setModel(typeModel);


    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String rateString = rateField.getText();
        /*String[] parts = rateString.split("\\.");
        int rateValue = Integer.parseInt(parts[0]) * 100;
        if (parts.length == 2) {
          rateValue += Integer.parseInt(parts[1]) % 100;
        } */

        int rateValue = RateService.stringToRate(rateString);

        EmployeeConnector.connector(nameField.getText()).
            setGroup((String) groupField.getSelectedItem()).
            setRate(rateValue).
            setType((String) typeField.getSelectedItem()).
            createAndStore();
      }
    });

    JPanel view = FillComponent.formBuilder(button).
        addInput("Name", nameField).
        addInput("Rate", rateField).
        addInput("Group", groupField).
        addInput("Type", typeField).
        build();

    return new EmployeeFormPresenter(view, nameField, rateField, groupField, typeField, button, null, null, null);
  }

  public static EmployeeFormPresenter buildEmployeeDisplayFormPresenter(Employee employee) {
    final JTextField nameField = new JTextField();
    final JTextField rateField = new JTextField();
    final JComboBox groupField = new JComboBox();
    final JComboBox typeField = new JComboBox();

    nameField.setText(employee.name);

    Iterable<Rate> rateList = RateService.retrieve(employee.name);
    Rate rate = rateList.iterator().next();
    rateField.setText(RateService.rateToString(rate.rate));

    DefaultComboBoxModel model = new DefaultComboBoxModel();
    model.addElement(employee.group);
    groupField.setModel(model);

    DefaultComboBoxModel typeModel = new DefaultComboBoxModel();
    typeModel.addElement(employee.type);
    typeField.setModel(typeModel);

    nameField.setEnabled(false);
    rateField.setEnabled(false);
    groupField.setEnabled(false);
    typeField.setEnabled(false);

    EditableRowTablePresenter entryTable = EditableRowTablePresenter.<Entry>builder().
        addHeader("Date").
        addHeader("Start").
        addHeader("End").
        addHeader("Jobsite").
        addHeader("Drive").
        setEntityToRowConverter(new EditableRowTablePresenter.EntityToRowConverter<Entry>() {
          @Override
          public Vector<String> convertToRow(Entry entity) {
            Vector<String> vector = new Vector<>();
            vector.add(DateService.standardString(entity.date));
            vector.add(DateService.standardString(entity.start));
            vector.add(DateService.standardString(entity.end));
            vector.add(entity.jobsite);
            vector.add(DateService.standardString(entity.drive));
            return vector;
          }
        }).
        setRowCellEditorCreator(new EditableRowTablePresenter.RowCellEditorCreator<Entry>() {
          @Override
          public TableCellEditor[] getRowCellEditors() {
            TableCellEditor[] editors = new TableCellEditor[5];

            JComboBox<String> jobsiteSelect = new JComboBox<String>();
            for (Jobsite jobsite : JobsiteService.getJobsites()) {
              jobsiteSelect.addItem(jobsite.name);
            }
            editors[3] = new DefaultCellEditor(jobsiteSelect);

            return editors;
          }
        }).
        setRowChangeHandler(new EditableRowTablePresenter.RowChangeHandler<Entry>() {
          @Override
          public void handleRowChange(int index, Vector<String> row) {
            System.out.println("Save Entry");

            String name = nameField.getText();

            final StrictDate date = DateService.date(row.get(0));
            final StrictTime start = DateService.time(row.get(1));
            final StrictTime end = DateService.time(row.get(2));
            final String jobsite = row.get(3);
            final StrictTime drive = DateService.time(row.get(4));

            final Entry entry = new Entry(date, start, end, jobsite, drive);

            List<Entry> entries = EntryService.retrieve(name);
            entries.set(index, entry);

            EntryService.store(name, entries);
          }
        }).setValues(EntryService.retrieve(employee.name)).build();

    ClockPresenter clock = ClockPresenter.buildEmployeeClock();
    clock.setClockTarget(employee.name);

    JPanel form = FillComponent.displayFormBuilder().
        addInput("Name", nameField).
        addInput("Rate", rateField).
        addInput("Group", groupField).
        addInput("Type", typeField).
        build();

    JPanel view = FillComponent.verticalFillBuilder().
        addGivenComponent(form).
        addGivenComponent(clock.view).
        addCalculatedComponent(entryTable.view).
        build();

    return new EmployeeFormPresenter(view, nameField, rateField, groupField, typeField, null, entryTable, clock, employee.name);
  }
}
