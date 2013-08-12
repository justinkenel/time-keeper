package com.timekeep.front.util;

import java.awt.*;
import java.awt.List;
import java.util.*;

public class FormFillLayout implements LayoutManager {
  final Component[] labels;
  final Component[] inputs;

  final Component button;

  private FormFillLayout(Component[] labels, Component[] inputs, Component button) {
    this.labels = labels;
    this.inputs = inputs;
    this.button = button;
  }

  @Override
  public void layoutContainer(Container parent) {
    int width = parent.getWidth();

    int yPosition = 5;
    int halfWidth = width/2;

    for(int i=0; i<labels.length; ++i) {
      Component label = labels[i];
      Component input = inputs[i];

      label.setLocation(5, yPosition);
      input.setLocation(halfWidth, yPosition);

      Dimension labelSize = label.getPreferredSize();
      Dimension inputSize = input.getPreferredSize();

      input.setSize(halfWidth, inputSize.height);
      label.setSize(labelSize);

      yPosition += Math.max(labelSize.height, inputSize.height) + 5;
    }

    Dimension buttonSize = button.getPreferredSize();
    button.setSize(buttonSize);
    button.setLocation(halfWidth - buttonSize.width/2, yPosition);
  }

  public static FormFillBuilder builder(Component button) {
    return new FormFillBuilder(new LinkedList<Component>(), new LinkedList<Component>(), button);
  }

  public static class FormFillBuilder {
    private final java.util.List<Component> componentList;
    private final java.util.List<Component> labelList;
    private final Component button;

    private FormFillBuilder(java.util.List<Component> componentList, java.util.List<Component> labelList, Component button) {
      this.componentList = componentList;
      this.labelList = labelList;
      this.button = button;
    }

    public FormFillBuilder addInput(Component label, Component component) {
      this.labelList.add(label);
      this.componentList.add(component);
      return this;
    }

    public FormFillLayout build() {
      return new FormFillLayout(labelList.toArray(new Component[componentList.size()]),
          componentList.toArray(new Component[componentList.size()]), button);
    }
  }

  @Override public void addLayoutComponent(String name, Component comp) {}

  @Override public void removeLayoutComponent(Component comp) {}

  @Override public Dimension preferredLayoutSize(Container parent) { return null; }

  @Override
  public Dimension minimumLayoutSize(Container parent) { return null; }
}