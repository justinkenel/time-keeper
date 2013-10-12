package com.timekeep.data;

import java.io.Serializable;

public class StrictTime implements Serializable, Comparable<StrictTime> {
  public final int hour;
  public final int minute;

  public StrictTime(int hour, int minute) {
    this.hour = hour;
    this.minute = minute;
  }

  public boolean equals(Object o) {
    return (o instanceof StrictTime) &&
        ((StrictTime) o).hour == hour &&
        ((StrictTime) o).minute == minute;
  }

  @Override
  public int compareTo(StrictTime strictTime) {
    int hourDifference = hour - strictTime.hour;
    return hourDifference != 0 ? hourDifference : minute - strictTime.minute;
  }
}
