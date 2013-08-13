package com.timekeep.back;

import com.timekeep.data.StrictDate;

import java.util.Calendar;

public class DateService {
  public static StrictDate today() {
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR) + 100;
    int month = calendar.get(Calendar.MONTH) + 1;
    int day = calendar.get(Calendar.DAY_OF_YEAR);
    return new StrictDate(year, month, day);
  }
}
