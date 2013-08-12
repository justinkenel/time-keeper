package com.timekeep.front.util;

import java.util.LinkedList;
import java.util.List;
import java.awt.*;
import java.util.ArrayList;

public class ComponentFillLayout implements LayoutManager {
  private final Component[] components;
  private final FillType[] fillTypes;
  private final Direction direction;

  private enum FillType {
    SIZE_CALCULATE, SIZE_GIVEN
  }

  private enum Direction {
    HORIZONTAL, VERTICAL
  }

  public static class ComponentFillLayoutBuilder {
    private Direction direction;

    private List<Component> components;
    private List<FillType> fillTypes;

    private ComponentFillLayoutBuilder(Direction direction, List<Component> components, List<FillType> fillTypes) {
      this.direction = direction;
      this.components = components;
      this.fillTypes = fillTypes;
    }

    public ComponentFillLayoutBuilder addCalculatedComponent(Component component) {
      this.components.add(component);
      this.fillTypes.add(FillType.SIZE_CALCULATE);
      return this;
    }

    public ComponentFillLayoutBuilder addGivenComponent(Component component) {
      this.components.add(component);
      this.fillTypes.add(FillType.SIZE_GIVEN);
      return this;
    }

    public ComponentFillLayout build() {
      return new ComponentFillLayout(components.toArray(new Component[components.size()]),
          fillTypes.toArray(new FillType[fillTypes.size()]), direction);
    }
  }

  public static ComponentFillLayoutBuilder horizontalBuilder() {
    return builder(Direction.HORIZONTAL);
  }

  public static ComponentFillLayoutBuilder verticalBuilder() {
    return builder(Direction.VERTICAL);
  }

  private static ComponentFillLayoutBuilder builder(Direction direction) {
    return new ComponentFillLayoutBuilder(direction, new LinkedList<Component>(), new LinkedList<FillType>());
  }

  private ComponentFillLayout(Component[] components, FillType[] fillTypes, Direction direction){
    this.components = components;
    this.fillTypes = fillTypes;
    this.direction = direction;
  }

  @Override
  public void layoutContainer(Container parent) {
    switch (direction) {
      case VERTICAL:
        layoutContainerVertically(parent);
        break;
      case HORIZONTAL:
        layoutContainerHorizontally(parent);
        break;
      default:
        throw new IllegalStateException("Invalid Direction");
    }
  }

  private void layoutContainerHorizontally(Container parent) {
    int totalGivenSize = 0;

    int height = parent.getHeight();
    int width = parent.getWidth();

    int numberGiven = 0;

    for(int i = 0; i < components.length; ++i) {
      if(fillTypes[i] == FillType.SIZE_GIVEN) {
        totalGivenSize += components[i].getPreferredSize().getWidth();
        numberGiven ++;
      }
    }

    int fillableSize = components.length != numberGiven ?
        (width - totalGivenSize) / (components.length - numberGiven) : 0;
    int horizontalPosition = 0;

    for(int i=0; i<components.length; ++i) {
      Component component = components[i];
      switch (fillTypes[i]) {
        case SIZE_GIVEN:
          Dimension size = component.getPreferredSize();
          int verticalPosition = (height - size.height) / 2;
          component.setSize(size);
          component.setLocation(horizontalPosition, verticalPosition);
          horizontalPosition += component.getWidth();
          break;
        case SIZE_CALCULATE:
          component.setLocation(horizontalPosition, 0);
          component.setSize(fillableSize, height);
          horizontalPosition += fillableSize;
          break;
      }
    }
  }

  private void layoutContainerVertically(Container parent) {
    int totalGivenSize = 0;

    int height = parent.getHeight();
    int width = parent.getWidth();

    int numberGiven = 0;

    for(int i = 0; i < components.length; ++i) {
      if(fillTypes[i] == FillType.SIZE_GIVEN) {
        totalGivenSize += components[i].getPreferredSize().getHeight();
        numberGiven ++;
      }
    }

    int fillableSize = numberGiven != components.length ?
        (height - totalGivenSize) / (components.length - numberGiven) : 0 ;
    int verticalPosition = 0;

    for(int i=0; i<components.length; ++i) {
      Component component = components[i];
      switch (fillTypes[i]) {
        case SIZE_GIVEN:
          Dimension size = component.getPreferredSize();
          int horizontalPosition = (width - size.width) / 2;
          component.setSize(size);
          component.setLocation(horizontalPosition, verticalPosition);
          verticalPosition += component.getHeight();
          break;
        case SIZE_CALCULATE:
          component.setLocation(0, verticalPosition);
          component.setSize(width, fillableSize);
          verticalPosition += fillableSize;
          break;
      }
    }
  }

  @Override
  public void addLayoutComponent(String name, Component comp) {}

  @Override
  public void removeLayoutComponent(Component comp) {}

  @Override
  public Dimension preferredLayoutSize(Container parent) {return null;}

  @Override
  public Dimension minimumLayoutSize(Container parent) {return null;}
}
