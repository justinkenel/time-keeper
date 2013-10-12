package com.timekeep.back;

import java.util.List;

public class ObjectListPersistenceService<T> {
  private final StorageService.MultiStorageAccess multiStorageAccess;
  private final StorageService.StorageObjectListConverter<T> storageObjectListConverter;

  private ObjectListPersistenceService(StorageService.MultiStorageAccess multiStorageAccess,
                                       StorageService.StorageObjectListConverter<T> storageObjectListConverter) {
    this.multiStorageAccess = multiStorageAccess;
    this.storageObjectListConverter = storageObjectListConverter;
  }

  public static <T> ObjectListPersistenceService<T> build(Class<T> type, String dbName) {
    final StorageService.MultiStorageAccess multiStorageAccess =
        StorageService.buildMultiStorageAccess(dbName);
    final StorageService.StorageObjectListConverter<T> storageObjectListConverter =
        StorageService.buildStorageObjectListConverter(type);

    return new ObjectListPersistenceService<T>(multiStorageAccess, storageObjectListConverter);
  }

  public void store(String name, Iterable<T> itemList) {
    StorageService.StorageObjectList storageObjectList =
        storageObjectListConverter.convertToStorageObjectList(itemList);
    multiStorageAccess.store(name, storageObjectList);
  }

  public List<T> retrieve(String name) {
    StorageService.StorageObjectList storageObjectList = multiStorageAccess.retrieve(name);
    return storageObjectListConverter.convertFromStorageObjectList(storageObjectList);
  }

  public void clear() {
    multiStorageAccess.clear();
  }
}
