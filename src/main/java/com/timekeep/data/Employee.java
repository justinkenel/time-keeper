package com.timekeep.data;

import sun.org.mozilla.javascript.internal.ScriptRuntime;

public class Employee extends NamedItem {
  public final String group;

  public Employee() {
    this(null, null);
  }

  public Employee(String name, String group) {
    super(name);
    this.group = group;
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof Employee &&
        name.equals(((Employee) o).name) &&
        group.equals(((Employee) o).group);
  }

  @Override
  public String toString() {
    return "[Employee name="+name+" group="+group+"]";
  }
}
