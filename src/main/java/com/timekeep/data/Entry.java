package com.timekeep.data;

import java.io.Serializable;

public class Entry implements Serializable {
  public final StrictDate date;
  public final StrictTime start;
  public final StrictTime end;

  public Entry(StrictDate date, StrictTime start, StrictTime end) {
    this.date = date;
    this.start = start;
    this.end = end;
  }

  public Entry() {
    this(null, null, null);
  }

  public boolean equals(Object o) {
    return o instanceof Entry &&
        date.equals(((Entry) o).date) &&
        start.equals(((Entry) o).start) &&
        end.equals(((Entry) o).end);
  }
}
