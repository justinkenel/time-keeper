package com.timekeep.front.util;

import javax.swing.*;
import java.awt.*;

public class SingleFillLayout implements LayoutManager {
  @Override
  public void addLayoutComponent(String name, Component comp) {
  }

  @Override
  public void removeLayoutComponent(Component comp) {
  }

  @Override
  public Dimension preferredLayoutSize(Container parent) {
    return null;
  }

  @Override
  public Dimension minimumLayoutSize(Container parent) {
    return null;
  }

  @Override
  public void layoutContainer(Container parent) {
    Component[] components = parent.getComponents();
    if (components.length > 1) {
      throw new IllegalArgumentException("Expect exactly one component");
    }
    if (components.length == 1) {
      components[0].setSize(parent.getSize());
    }
  }

  public static JPanel componentFillPanel() {
    JPanel panel = new JPanel(new SingleFillLayout());
    return panel;
  }
}
