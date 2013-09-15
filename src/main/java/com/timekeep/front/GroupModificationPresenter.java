package com.timekeep.front;

import com.timekeep.back.EmployeeService;
import com.timekeep.data.Employee;
import com.timekeep.front.util.FillComponent;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class GroupModificationPresenter {
  public final JPanel view;

  private GroupModificationPresenter(JPanel view) {
    this.view = view;
  }

  public static GroupModificationPresenter build(final String groupName) {
    Iterable<Employee> employeeList = EmployeeService.getEmployees();

    final DefaultListModel groupEmployeeModel = new DefaultListModel();
    final DefaultListModel nonGroupEmployeeModel = new DefaultListModel();

    for (Employee employee : employeeList) {
      if (employee.group.equals(groupName)) {
        groupEmployeeModel.addElement(employee.name);
      } else {
        nonGroupEmployeeModel.addElement(employee.name);
      }
    }

    final JList<String> groupEmployeeList = new JList<String>(groupEmployeeModel);
    final JList<String> nonGroupEmployeeList = new JList<String>(nonGroupEmployeeModel);

    groupEmployeeList.setDragEnabled(true);
    nonGroupEmployeeList.setDragEnabled(true);

    groupEmployeeList.setTransferHandler(new TransferHandler() {
      @Override
      public boolean canImport(TransferSupport support) {
        return support.isDataFlavorSupported(DataFlavor.stringFlavor);
      }

      @Override
      public boolean importData(TransferSupport support) {
        Transferable transferable = support.getTransferable();

        final String employeeName;
        try {
          employeeName = (String) transferable.getTransferData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
          return false;
        }

        System.out.println("importData");

        Employee newEmployee = new Employee(employeeName, groupName);
        EmployeeService.storeEmployee(newEmployee);

        refresh(employeeName, groupEmployeeModel, nonGroupEmployeeModel);

        return true;
      }
    });

    JScrollPane groupEmployeeScrollPane = new JScrollPane(groupEmployeeList);
    JScrollPane nonGroupEmployeeScrollPane = new JScrollPane(nonGroupEmployeeList);

    groupEmployeeScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    nonGroupEmployeeScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    final JPanel panes = FillComponent.horizontalFillBuilder().
        addCalculatedComponent(nonGroupEmployeeScrollPane).
        addCalculatedComponent(groupEmployeeScrollPane).
        setJPanelName("GroupModificationPresenter").
        build();

    final JLabel instructions = new JLabel("Drag from the left to add employees to the group");

    final JPanel view = FillComponent.verticalFillBuilder().
        addGivenComponent(instructions).
        addCalculatedComponent(panes).
        build();

    return new GroupModificationPresenter(view);
  }

  private static void refresh(String name, DefaultListModel<String> groupEmployeeList, DefaultListModel<String> nonGroupEmployeeList) {
    groupEmployeeList.addElement(name);
    Object[] nonGroupEmployeesArray = nonGroupEmployeeList.toArray();
    nonGroupEmployeeList.removeAllElements();

    for (Object nonGroupEmployee : nonGroupEmployeesArray) {
      if (!name.equals(nonGroupEmployee)) {
        nonGroupEmployeeList.addElement((String) nonGroupEmployee);
      }
    }
  }
}
