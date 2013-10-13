package com.timekeep.back;

import com.timekeep.data.StrictDate;
import com.timekeep.data.StrictTime;

import java.util.Calendar;

public class DateService {
  public static StrictDate today() {
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR) + 100;
    int month = calendar.get(Calendar.MONTH) + 1;
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    return new StrictDate(year, month, day);
  }

  public static StrictTime now() {
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);
    return new StrictTime(hour, minute);
  }

  public static final StrictDate INVALID_DATE = new StrictDate(-1, -1, -1);
  public static final StrictTime INVALID_TIME = new StrictTime(-1, -1);

  public static final StrictTime ZERO_TIME = new StrictTime(0, 0);

  public static StrictDate date(String dateString) {
    if (dateString.equals("")) {
      return INVALID_DATE;
    }

    String[] values = dateString.split("/");

    int year = Integer.parseInt(values[0]);
    int month = Integer.parseInt(values[1]);
    int day = Integer.parseInt(values[2]);

    return date(year, month, day);
  }

  public static StrictTime time(String timeString) {
    if (timeString.isEmpty()) {
      return INVALID_TIME;
    }

    String[] values = timeString.split(":");

    int hour = Integer.parseInt(values[0]);
    int minute = Integer.parseInt(values[1]);

    return time(hour, minute);
  }

  public static String standardString(StrictDate strictDate) {
    if (INVALID_DATE.equals(strictDate)) {
      return "";
    }
    return strictDate.year + "/" + strictDate.month + "/" + strictDate.day;
  }

  public static String standardString(StrictTime strictTime) {
    boolean invalid = INVALID_TIME.equals(strictTime);
    return invalid ? "" : strictTime.hour + ":" + strictTime.minute;
  }

  public static StrictDate date(int year, int month, int day) {
    return new StrictDate(year, month, day);
  }

  public static StrictTime time(int hour, int minute) {
    return new StrictTime(hour, minute);
  }
}
