package com.timekeep.front;

import com.timekeep.data.NamedItem;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ItemListPresenter<T extends NamedItem> {
  public final JList view;
  private final DefaultListModel model;
  private final ItemSelectionHandler<T> handler;

  public ItemListPresenter(JList view, DefaultListModel model, ItemSelectionHandler<T> handler) {
    this.view = view;
    this.model = model;
    this.handler = handler;
  }

  public static <K extends NamedItem> ItemListPresenter build(ItemSelectionHandler<K> handler) {
    DefaultListModel model = new DefaultListModel();
    JList view = new JList(model);
    view.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    return new ItemListPresenter<K>(view, model, handler);
  }

  public void setItems(Iterable<T> items) {
    model.clear();
    int size = 0;
    for(T group : items) {
      model.addElement(group.name);
      size ++;
    }

    final T[] itemArray = (T[]) new NamedItem[size];
    int i = 0;
    for(T item : items) {
      itemArray[i++] = item;
    }

    for(ListSelectionListener listener : view.getListSelectionListeners()) {
      view.removeListSelectionListener(listener);
    }

    view.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        System.out.println("Selection: " + view.getSelectedIndex());
        if(view.getSelectedIndex() >= 0) { handler.selectItem(itemArray[view.getSelectedIndex()]); }
      }
    });
  }

  public void clearSelection() {
    view.clearSelection();
  }

  public void clear() {
    model.clear();
  }

  public static interface ItemSelectionHandler<T extends NamedItem> {
    public void selectItem(T group);
  }
}
