package com.timekeep.back;

import com.timekeep.data.EmployeeType;

public class PersistentServices {
  public static final ObjectPersistenceService<EmployeeType> employeeTypeService;

  static {
    employeeTypeService = ObjectPersistenceService.build(EmployeeType.class,
        PropertiesService.getEmployeeTypeDataName());
  }
}
