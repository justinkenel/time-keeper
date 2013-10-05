package com.timekeep.back;

import com.timekeep.data.NamedItem;

import java.util.ArrayList;

public class ObjectPersistenceService<T extends NamedItem> {
  private final StorageService.StorageAccess storageAccess;
  private final StorageService.StorageObjectConverter<T> storageObjectConverter;

  public ObjectPersistenceService(StorageService.StorageAccess storageAccess,
                                  StorageService.StorageObjectConverter storageObjectConverter) {
    this.storageAccess = storageAccess;
    this.storageObjectConverter = storageObjectConverter;
  }

  public static <T extends NamedItem> ObjectPersistenceService<T> build(Class<T> type, String dbName) {
    final StorageService.StorageAccess storageAccess = StorageService.buildStorageAccess(dbName);
    final StorageService.StorageObjectConverter<T> storageObjectConverter = StorageService.buildStorageObjectConverter(type);

    return new ObjectPersistenceService<T>(storageAccess, storageObjectConverter);
  }

  public void store(T objectToStore) {
    StorageService.StorageObject convertedObject = storageObjectConverter.convertToStorageObject(objectToStore);
    storageAccess.store(objectToStore.name, convertedObject);
  }

  public T retrieve(String name) {
    StorageService.StorageObject storedObject = storageAccess.retrieve(name);
    return storageObjectConverter.convertFromStorageObject(storedObject);
  }

  public Iterable<T> getAll() {
    StorageService.StorageObjectList list = storageAccess.retrieveAll();
    ArrayList<T> convertedObjectList = new ArrayList(list.getSize());

    for (StorageService.StorageObject object : list) {
      T convertedObject = storageObjectConverter.convertFromStorageObject(object);
      convertedObjectList.add(convertedObject);
    }

    return convertedObjectList;
  }
}
