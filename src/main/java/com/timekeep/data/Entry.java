package com.timekeep.data;

import java.io.Serializable;

public class Entry implements Serializable {
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
}
