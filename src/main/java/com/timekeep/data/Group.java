package com.timekeep.data;

public class Group extends NamedItem {
  public Group() {
    this(null);
  }

  public Group(String name) {
    super(name);
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof Group &&
        name.equals(((Group) o).name);
  }

  @Override
  public String toString() {
    return "[Group name="+name+"]";
  }
}
