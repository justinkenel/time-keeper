package com.timekeep.facade;

import com.timekeep.back.EntryService;
import com.timekeep.data.Entry;

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

  public static EmployeeFacade facade(String name) {
    return new EmployeeFacade(name);
  }
}
