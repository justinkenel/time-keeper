package com.timekeep.back;

import com.timekeep.data.Entry;
import com.timekeep.data.Rate;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class EntryServiceTest {
  @BeforeClass
  public static void beforeClass() throws Exception {
    EntryService.clear();
  }

  @After
  public void after() throws Exception {
    EntryService.clear();
  }

  @Test
  public void testRoundTrip() throws Exception {
    Entry entry1 = new Entry(DateService.date(1, 2, 3), DateService.time(10, 11), DateService.time(20,21));
    Entry entry2 = new Entry(DateService.date(4, 5, 6), DateService.time(30, 31), DateService.time(40,41));

    ArrayList<Entry> list = new ArrayList<Entry>();
    list.add(entry1);
    list.add(entry1);

    EntryService.store("name", list);

    Iterable<Entry> stored = EntryService.retrieve("name");
    Assert.assertEquals(list, stored);
  }
}
