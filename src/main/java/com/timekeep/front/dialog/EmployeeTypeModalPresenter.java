package com.timekeep.front.dialog;

import com.timekeep.back.PersistentServices;
import com.timekeep.data.EmployeeType;
import com.timekeep.data.NamedItem;
import com.timekeep.front.ItemListPresenter;
import com.timekeep.front.util.FillComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeTypeModalPresenter {
  public final JDialog view;

  private EmployeeTypeModalPresenter(JDialog view) {
    this.view = view;
  }

  public static EmployeeTypeModalPresenter build(JFrame parent) {
    JDialog view = new JDialog(parent);

    view.setTitle("Employee Types");
    view.setModal(true);

    ItemListPresenter<EmployeeType> listPresenter = buildListPresenter();

    JPanel panel = FillComponent.verticalFillBuilder().
        addCalculatedComponent(listPresenter.view).
        addGivenComponent(employeeTypeCreateForm(listPresenter)).
        build();

    view.add(panel);
    view.setSize(200, 400);

    int parentWidth = parent.getWidth();
    int parentHeight = parent.getHeight();

    view.setLocation(parentWidth / 2 - 100, parentHeight / 2 - 200);

    return new EmployeeTypeModalPresenter(view);
  }

  private static JPanel employeeTypeCreateForm(final ItemListPresenter<EmployeeType> itemListPresenter) {
    final JPanel view;

    final JTextField typeField = new JTextField();
    final JButton button = new JButton("Create");

    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String type = typeField.getText();

        EmployeeType employeeType = new EmployeeType(type);
        PersistentServices.employeeTypeService.store(employeeType);

        itemListPresenter.setItems(PersistentServices.employeeTypeService.getAll());
      }
    });

    view = FillComponent.formBuilder(button).addInput("Type", typeField).build();

    return view;
  }

  private static ItemListPresenter<EmployeeType> buildListPresenter() {
    Iterable<EmployeeType> employeeTypeList = PersistentServices.employeeTypeService.getAll();

    ItemListPresenter<EmployeeType> itemListPresenter =
        ItemListPresenter.build(new ItemListPresenter.ItemSelectionHandler<NamedItem>() {
          @Override
          public void selectItem(NamedItem item) {
          }
        });

    itemListPresenter.setItems(employeeTypeList);

    return itemListPresenter;
  }
}
