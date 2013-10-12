package com.timekeep.facade;

import com.timekeep.back.EntryService;
import com.timekeep.data.Entry;
import com.timekeep.data.StrictTime;

import java.util.List;

public class EmployeeFacade {
  private final String name;

  private EmployeeFacade(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public Entry addEntry(Entry entry) {
    List<Entry> entryList = EntryService.retrieve(name);
    entryList.add(0, entry);
    EntryService.store(name, entryList);
    return entry;
  }

  public Entry endEntry(StrictTime endTime) {
    List<Entry> entryList = EntryService.retrieve(name);

    Entry oldEntry = entryList.get(0);
    Entry newEntry = new Entry(oldEntry.date, oldEntry.start, endTime, oldEntry.jobsite, oldEntry.drive);
    entryList.set(0, newEntry);
    EntryService.store(name, entryList);

    return newEntry;
  }

  public static EmployeeFacade facade(String name) {
    return new EmployeeFacade(name);
  }
}
