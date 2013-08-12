package com.timekeep.front;

import com.timekeep.back.GroupService;
import com.timekeep.data.Group;
import com.timekeep.front.util.ComponentFillLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupSelectionPresenter {
  private final static GroupListPresenter groupListPresenter;
  private final static JButton createGroupButton;

  public static JPanel view;

  static {
    groupListPresenter = GroupListPresenter.build(new GroupListPresenter.GroupSelectionHandler() {
      @Override
      public void selectGroup(Group g) {
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

    groupListPresenter.setGroups(GroupService.getGroups());
  }

  public static void clearSelection() {
    groupListPresenter.clearSelection();
  }

  public static void addGroup(Group group) {
    groupListPresenter.addGroup(group);
  }
}
