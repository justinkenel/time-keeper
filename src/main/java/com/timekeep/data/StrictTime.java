package com.timekeep.data;

import java.io.Serializable;

public class StrictTime implements Serializable {
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
}
