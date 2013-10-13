package com.timekeep.data;

import java.io.Serializable;

public class Entry implements Serializable, Comparable<Entry> {
  public final StrictDate date;
  public final StrictTime start;
  public final StrictTime end;

  public final String jobsite;

  public final StrictTime drive;

  public Entry(StrictDate date, StrictTime start, StrictTime end, String jobsite, StrictTime drive) {
    this.date = date;
    this.start = start;
    this.end = end;
    this.jobsite = jobsite;
    this.drive = drive;
  }

  public Entry() {
    this(null, null, null, null, null);
  }

  public boolean equals(Object o) {
    return o instanceof Entry &&
        date.equals(((Entry) o).date) &&
        start.equals(((Entry) o).start) &&
        end.equals(((Entry) o).end) &&
        jobsite.equals(((Entry) o).jobsite) &&
        drive.equals(((Entry) o).drive);
  }

  @Override
  public int compareTo(Entry entry) {
    int dateDifference = date.compareTo(entry.date);

    if (dateDifference != 0) {
      return dateDifference;
    }

    int startDifference = start.compareTo(entry.start);

    return startDifference != 0 ? startDifference : end.compareTo(entry.end);
  }


  public boolean between(Entry before, Entry after) {
    return before.compareTo(this) <= 0 && this.compareTo(after) <= 0;
  }
}
