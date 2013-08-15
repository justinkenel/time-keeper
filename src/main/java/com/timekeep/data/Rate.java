package com.timekeep.data;

import java.io.Serializable;

public class Rate implements Serializable {
  public final StrictDate start;

  /* important to know that this is 100 * actual rate */
  public final int rate;

  public Rate() {
    this(null, 0);
  }

  public Rate(StrictDate start, int rate) {
    this.start = start;
    this.rate = rate;
  }

  public boolean equals(Object o) {
    return (o instanceof Rate) &&
        start.equals(((Rate) o).start) &&
        rate == ((Rate) o).rate;
  }
}
