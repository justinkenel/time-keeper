package com.timekeep.front;

import com.timekeep.back.GroupService;
import com.timekeep.data.Group;
import com.timekeep.data.NamedItem;
import com.timekeep.front.util.ComponentFillLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupSelectionPresenter {
  private final static ItemListPresenter groupListPresenter;
  private final static JButton createGroupButton;

  public static JPanel view;

  static {
    groupListPresenter = ItemListPresenter.build(new ItemListPresenter.ItemSelectionHandler<Group>() {
      @Override
      public void selectItem(Group g) {
        System.out.println("Selected Group: " + g.name);
        SelectedItemPresenter.presentGroup(g);
      }
    });
    createGroupButton = new JButton("Create");

    createGroupButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        SelectedItemPresenter.presentNewGroup();
      }
    });

    view = new JPanel();
    view.add(createGroupButton);
    view.add(groupListPresenter.view);

    view.setLayout(ComponentFillLayout.verticalBuilder().
        addCalculatedComponent(groupListPresenter.view).
        addGivenComponent(createGroupButton).
        build());

    groupListPresenter.setItems(GroupService.getGroups());
  }

  public static void clearSelection() {
    groupListPresenter.clearSelection();
  }

  public static void addGroup(Group group) {
    groupListPresenter.setItems(GroupService.getGroups());
  }
}
