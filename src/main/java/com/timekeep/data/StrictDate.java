package com.timekeep.data;

import java.io.Serializable;

public class StrictDate implements Serializable, Comparable<StrictDate> {
  public final int year;
  public final int month;
  public final int day;

  public StrictDate(int year, int month, int day) {
    this.year = year;
    this.month = month;
    this.day = day;
  }

  @Override
  public boolean equals(Object o) {
    return (o instanceof StrictDate) &&
        ((StrictDate) o).year == year &&
        ((StrictDate) o).month == month &&
        ((StrictDate) o).day == day;
  }

  @Override
  public int compareTo(StrictDate strictDate) {
    int yearDifference = year - strictDate.year;

    if (yearDifference != 0) {
      return yearDifference;
    }

    int hourDifference = month - strictDate.month;
    return hourDifference != 0 ? hourDifference : day - strictDate.day;
  }
}
