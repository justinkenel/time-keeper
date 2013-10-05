package com.timekeep.data;

public class Employee extends NamedItem {
  public final String group;
  public final String type;

  public Employee() {
    this(null, null, null);
  }

  public Employee(String name, String group, String type) {
    super(name);
    this.group = group;
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof Employee &&
        name.equals(((Employee) o).name) &&
        group.equals(((Employee) o).group) &&
        type.equals(((Employee) o).type);
  }

  @Override
  public String toString() {
    return "[Employee name=" + name + " group=" + group + " type=" + type + "]";
  }
}
