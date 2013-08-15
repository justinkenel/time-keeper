package com.timekeep.back;

import com.timekeep.data.Rate;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class RateServiceTest {
  @BeforeClass
  public static void beforeClass() throws Exception {
    RateService.clear();
  }

  @After
  public void after() throws Exception {
    RateService.clear();
  }

  @Test
  public void testRoundTrip() throws Exception {
    Rate rate1 = new Rate(DateService.date(1,2,3), 10);
    Rate rate2 = new Rate(DateService.date(4,5,6), 20);

    ArrayList<Rate> list = new ArrayList<Rate>();
    list.add(rate1);
    list.add(rate2);

    RateService.store("name", list);

    Iterable<Rate> stored = RateService.retrieve("name");
    Assert.assertEquals(list, stored);
  }
}
