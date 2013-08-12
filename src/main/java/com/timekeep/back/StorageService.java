package com.timekeep.back;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class StorageService {
  private static DB db;

  static {
    String dbFileName = PropertiesService.getDatabaseFileName();

    File settingsDbFile = new File(dbFileName);
    db = DBMaker.newFileDB(settingsDbFile).
        closeOnJvmShutdown().
        make();
  }

  public static StorageObject buildStorageObject(Object[] values) {
    return new StorageObject(values);
  }

  public static StorageAccess buildStorageAccess(String name) {
    Map<String, StorageObject> storageMap = db.getTreeMap(name);
    return new StorageAccess(storageMap);
  }

  public static <T> StorageObjectConverter<T> buildStorageObjectConverter(Class<T> objectClass) {
    final Constructor<T> constructor;

    try {
      constructor = objectClass.getConstructor();
    } catch(NoSuchMethodException e) {
      throw new IllegalArgumentException("No default constructor defined", e);
    }

    final Field[] fields = objectClass.getFields();

    for(Field field : fields) {
      field.setAccessible(true);
    }

    return new StorageObjectConverter<T>(fields, constructor);
  }

  /**
   * A StorageAccess object is used to provide access to a specific database, indicated by the name
   */
  public static class StorageAccess {
    private final Map<String, StorageObject> map;

    private StorageAccess(Map<String, StorageObject> map) {
      this.map = map;
    }

    public void store(String identifier, StorageObject storageObject) {
      map.put(identifier, storageObject);
      db.commit();
    }

    public StorageObject retrieve(String identifier) {
      return map.get(identifier);
    }

    public StorageObjectList retrieveAll() {
      Collection<StorageObject> values = map.values();
      ArrayList valuesList = new ArrayList(values);
      return new StorageObjectList(valuesList);
    }

    public void clear() {
      map.clear();
      db.commit();
    }
  }

  public static class StorageObjectList implements Iterable<StorageObject> {
    private ArrayList<StorageObject> objectList;

    public StorageObjectList(ArrayList<StorageObject> objectList) {
      this.objectList = objectList;
    }

    public int getSize() {
      return objectList.size();
    }

    public Iterator<StorageObject> iterator() {
      return objectList.iterator();
    }

    public StorageObject get(int index) {
      return objectList.get(index);
    }
  }

  /**
   * A StorageObjectConverter is used to convert objects of a given class to StorageObject objects and vice versa
   */
  public static class StorageObjectConverter <T> {
    private final Field[] fieldList;
    private final Constructor<T> objectConstructor;

    private StorageObjectConverter(Field[] fieldList, Constructor<T> objectConstructor) {
      this.fieldList = fieldList;
      this.objectConstructor = objectConstructor;
    }

    public StorageObject convertToStorageObject(T object) {
      ArrayList<Object> listOfValues = new ArrayList(fieldList.length);
      for(Field field : fieldList) {
        final Object value;
        try {
          value = field.get(object);
        } catch(IllegalAccessException e) {
          throw new IllegalArgumentException("Unable to create StorageObject from Object", e);
        }

        listOfValues.add(value);
      }

      Object[] fields = listOfValues.toArray(new Object[fieldList.length]);
      return new StorageObject(fields);
    }

    public T convertFromStorageObject(StorageObject storageObject) {
      final T object;
      try {
         object = objectConstructor.newInstance();
      } catch (InstantiationException|IllegalAccessException|InvocationTargetException e) {
        throw new IllegalStateException("Unable to allocate Object for StorageObject", e);
      }

      int index = 0;
      for(Field field : fieldList) {
        try {
          field.set(object, storageObject.get(index ++));
        } catch(IllegalAccessException e) {
          throw new IllegalArgumentException("Unable to populate Object from StorageObject", e);
        }
      }

      return object;
    }
  }

  /**
   * A StorageObject object is used to represent data in an array format in a database
   *
   * POJOs should be broken down into StorageObjects for persistence, and reconstituted for retrieval
   */
  public static class StorageObject implements Serializable {
    private Object[] values;

    private StorageObject(Object[] values) {
      this.values = values;
    }

    public Object get(int index) {
      return values[index];
    }
  }
}
