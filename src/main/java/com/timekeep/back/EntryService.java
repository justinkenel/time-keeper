package com.timekeep.back;

import com.timekeep.data.Entry;

public class EntryService {
  private static final StorageService.MultiStorageAccess multiStorageAccess;
  private static final StorageService.StorageObjectListConverter<Entry> storageObjectListConverter;

  static {
    String dbName = PropertiesService.getEntryDataName();
    multiStorageAccess = StorageService.buildMultiStorageAccess(dbName);
    storageObjectListConverter = StorageService.buildStorageObjectListConverter(Entry.class);
  }

  public static void store(String name, Iterable<Entry> entryList) {
    StorageService.StorageObjectList storageObjectList = storageObjectListConverter.convertToStorageObjectList(entryList);
    multiStorageAccess.store(name, storageObjectList);
  }

  public static Iterable<Entry> retrieve(String name) {
    StorageService.StorageObjectList storageObjectList = multiStorageAccess.retrieve(name);
    return storageObjectListConverter.convertFromStorageObjectList(storageObjectList);
  }

  public static void clear() {
    multiStorageAccess.clear();
  }
}
