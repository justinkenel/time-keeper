package com.timekeep.data;

import java.io.Serializable;

public class Jobsite implements Serializable {
  public final String name;
  public final String state;

  public Jobsite(String name, String state) {
    this.name = name;
    this.state = state;
  }

  public Jobsite() {
    this(null, null);
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof Jobsite &&
        ((Jobsite) o).name.equals(name) &&
        ((Jobsite) o).state.equals(state);
  }

  public static Jobsite EMPTY_JOBSITE = new Jobsite("", "");
}
