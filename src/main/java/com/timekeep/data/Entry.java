package com.timekeep.data;

public class Entry {
  public final StrictDate date;
  public final StrictTime start;
  public final StrictTime end;

  public Entry(StrictDate date, StrictTime start, StrictTime end) {
    this.date = date;
    this.start = start;
    this.end = end;
  }
}
