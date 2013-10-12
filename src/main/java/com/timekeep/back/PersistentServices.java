package com.timekeep.back;

import com.timekeep.data.EmployeeType;
import com.timekeep.data.Entry;

public class PersistentServices {
  public static final ObjectPersistenceService<EmployeeType> employeeTypeService;
  public static final ObjectListPersistenceService<Entry> groupEntryService;


  static {
    employeeTypeService = ObjectPersistenceService.build(EmployeeType.class,
        PropertiesService.getEmployeeTypeDataName());

    groupEntryService = ObjectListPersistenceService.build(Entry.class,
        PropertiesService.getGroupEntryDataName());
  }
}
