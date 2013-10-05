package com.timekeep.data;

public class EmployeeType extends NamedItem {
  public EmployeeType(String typeName) {
    super(typeName);
  }

  public EmployeeType() {
    this(null);
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof EmployeeType &&
        name.equals(((EmployeeType) o).name);
  }
}
