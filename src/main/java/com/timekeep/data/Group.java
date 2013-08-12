package com.timekeep.data;

public class Group {
  public final String name;

  public Group() {
    this(null);
  }

  public Group(String name) {
    this.name = name;
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
