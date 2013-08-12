package com.timekeep.front;

import com.timekeep.front.util.ComponentFillLayout;

import javax.swing.*;

public class PrimaryPresenter {
  private static JFrame primaryView;

  static {
    primaryView = new JFrame("Time Management");
    primaryView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    primaryView.setLayout(ComponentFillLayout.horizontalBuilder().
        addCalculatedComponent(SelectionPresenter.view).
        addCalculatedComponent(SelectedItemPresenter.view).
        build());

    primaryView.setSize(640, 480);

    primaryView.add(SelectionPresenter.view);
    primaryView.add(SelectedItemPresenter.view);
  }

  public static void show() {
    primaryView.setVisible(true);
  }

  public static void refresh() {
    primaryView.setContentPane(primaryView.getContentPane());
  }

  public static void main(String[]args) {
    show();
  }
}