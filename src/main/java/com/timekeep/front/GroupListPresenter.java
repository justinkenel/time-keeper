package com.timekeep.front;

import com.timekeep.data.Group;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GroupListPresenter {
  public final JList view;
  private final DefaultListModel model;
  private final GroupSelectionHandler handler;

  public GroupListPresenter(JList view, DefaultListModel model, GroupSelectionHandler handler) {
    this.view = view;
    this.model = model;
    this.handler = handler;
  }

  public static GroupListPresenter build(GroupSelectionHandler handler) {
    DefaultListModel model = new DefaultListModel();
    JList view = new JList(model);
    view.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    return new GroupListPresenter(view, model, handler);
  }

  public void addGroup(Group group) {
    model.addElement(group.name);
  }

  public void setGroups(Iterable<Group> groups) {
    model.clear();
    int size = 0;
    for(Group group : groups) {
      model.addElement(group.name);
      size ++;
    }

    final Group[] groupArray = new Group[size];
    int i = 0;
    for(Group group : groups) {
      groupArray[i++] = group;
    }

    for(ListSelectionListener listener : view.getListSelectionListeners()) {
      view.removeListSelectionListener(listener);
    }

    view.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        System.out.println("Selection: " + view.getSelectedIndex());
        if(view.getSelectedIndex() >= 0) { handler.selectGroup(groupArray[view.getSelectedIndex()]); }
      }
    });
  }

  public void clearSelection() {
    view.clearSelection();
  }

  public void clear() {
    model.clear();
  }

  public static interface GroupSelectionHandler {
    public void selectGroup(Group group);
  }
}
