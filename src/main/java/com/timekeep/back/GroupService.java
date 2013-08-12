package com.timekeep.back;

import com.timekeep.data.Employee;
import com.timekeep.data.Group;

import java.util.ArrayList;

public class GroupService {
  private static final StorageService.StorageAccess storageAccess;
  private static final StorageService.StorageObjectConverter<Group> storageObjectConverter;

  static {
    final String groupDbName = PropertiesService.getGroupDatabaseName();
    storageAccess = StorageService.buildStorageAccess(groupDbName);
    storageObjectConverter = StorageService.buildStorageObjectConverter(Group.class);
  }

  public static void store(Group group) {
    StorageService.StorageObject storageObject = storageObjectConverter.convertToStorageObject(group);
    storageAccess.store(group.name, storageObject);
  }

  public static Group load(String name) {
    StorageService.StorageObject storageObject = storageAccess.retrieve(name);
    return storageObjectConverter.convertFromStorageObject(storageObject);
  }

  public static Iterable<Group> getGroups() {
    StorageService.StorageObjectList list = storageAccess.retrieveAll();
    ArrayList<Group> groupList = new ArrayList<>(list.getSize());

    for(StorageService.StorageObject storageObject : list) {
      Group group = storageObjectConverter.convertFromStorageObject(storageObject);
      groupList.add(group);
    }

    return groupList;
  }

  public static void clear() {
    storageAccess.clear();
  }
}
