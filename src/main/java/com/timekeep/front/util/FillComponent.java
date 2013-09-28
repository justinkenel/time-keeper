package com.timekeep.front.util;

import javax.swing.*;
import java.awt.*;

public class FillComponent {
  public static class FormComponentBuilder {
    private FormFillLayout.FormFillBuilder layoutBuilder;
    private JPanel panel;

    private FormComponentBuilder(FormFillLayout.FormFillBuilder layoutBuilder, JPanel panel) {
      this.layoutBuilder = layoutBuilder;
      this.panel = panel;
    }

    public FormComponentBuilder addInput(String label, Component input) {
      JLabel jLabel = new JLabel(label);
      layoutBuilder.addInput(jLabel, input);

      panel.add(jLabel);
      panel.add(input);

      return this;
    }

    public JPanel build() {
      panel.setLayout(layoutBuilder.build());
      return panel;
    }
  }

  public static FormComponentBuilder formBuilder(Component button) {
    JPanel panel = new JPanel();
    panel.add(button);
    return new FormComponentBuilder(FormFillLayout.builder(button), panel);
  }

  public static FormComponentBuilder displayFormBuilder() {
    JPanel panel = new JPanel();
    return new FormComponentBuilder(FormFillLayout.builder(), panel);
  }

  public static class FillComponentBuilder {
    private ComponentFillLayout.ComponentFillLayoutBuilder layoutBuilder;
    private JPanel panel;

    private FillComponentBuilder(ComponentFillLayout.ComponentFillLayoutBuilder layoutBuilder, JPanel panel) {
      this.layoutBuilder = layoutBuilder;
      this.panel = panel;
    }

    public FillComponentBuilder addCalculatedComponent(Component component) {
      layoutBuilder.addCalculatedComponent(component);
      panel.add(component);
      return this;
    }

    public FillComponentBuilder addGivenComponent(Component component) {
      layoutBuilder.addGivenComponent(component);
      panel.add(component);
      return this;
    }

    public FillComponentBuilder setJPanelName(String name) {
      panel.setName(name);
      return this;
    }

    public JPanel build() {
      panel.setLayout(layoutBuilder.build());
      return panel;
    }
  }

  public static FillComponentBuilder horizontalFillBuilder() {
    return new FillComponentBuilder(ComponentFillLayout.horizontalBuilder(), new JPanel());
  }

  public static FillComponentBuilder verticalFillBuilder() {
    return new FillComponentBuilder(ComponentFillLayout.verticalBuilder(), new JPanel());
  }
}
