package com.timekeep.data;

import sun.org.mozilla.javascript.internal.ScriptRuntime;

public class Employee extends NamedItem {
  public final String group;
  public final Entry[] entryList;
  public final Rate[] rateList;

  public Employee() {
    this(null, null, null, null);
  }

  public Employee(String name, String group, Entry[] entryList, Rate[] rateList) {
    super(name);
    this.group = group;
    this.entryList = entryList;
    this.rateList = rateList;
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof Employee &&
        name.equals(((Employee) o).name) &&
        group.equals(((Employee) o).group) &&
        entryList.equals(((Employee) o).entryList) &&
        rateList.equals(((Employee) o).rateList);
  }

  @Override
  public String toString() {
    return "[Employee name="+name+" group="+group+" entryList="+entryList+" rateList="+rateList+"]";
  }
}
