package com.timekeep.back;

import com.timekeep.data.Employee;

import java.util.ArrayList;

public class EmployeeService {
  private static final StorageService.StorageAccess storageAccess;
  private static final StorageService.StorageObjectConverter<Employee> storageObjectConverter;

  static {
    final String employeeDbName = PropertiesService.getEmployeeDatabaseName();
    storageAccess = StorageService.buildStorageAccess(employeeDbName);
    storageObjectConverter = StorageService.buildStorageObjectConverter(Employee.class);
  }

  public static void storeEmployee(Employee employee) {
    StorageService.StorageObject storageObject = storageObjectConverter.convertToStorageObject(employee);
    storageAccess.store(employee.name, storageObject);
  }

  public static Employee getEmployee(String name) {
    StorageService.StorageObject storageObject = storageAccess.retrieve(name);
    return storageObjectConverter.convertFromStorageObject(storageObject);
  }

  public static Iterable<Employee> getEmployees() {
    StorageService.StorageObjectList list = storageAccess.retrieveAll();
    ArrayList<Employee> employeeList = new ArrayList(list.getSize());

    for(StorageService.StorageObject object : list) {
      Employee employee = storageObjectConverter.convertFromStorageObject(object);
      employeeList.add(employee);
    }

    return employeeList;
  }

  /**
   * Only for use during tests
   */
  public static void clear() {
    storageAccess.clear();
  }
}
