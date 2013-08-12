package com.timekeep.data;

public class Rate {
  public final StrictDate start;

  /* important to know that this is 100 * actual rate */
  public final int rate;

  public Rate(StrictDate start, int rate) {
    this.start = start;
    this.rate = rate;
  }
}
