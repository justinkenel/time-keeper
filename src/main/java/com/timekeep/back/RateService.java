package com.timekeep.back;

import com.timekeep.data.Rate;

public class RateService {
  private static final StorageService.MultiStorageAccess multiStorageAccess;
  private static final StorageService.StorageObjectListConverter<Rate> storageObjectListConverter;

  static {
    String dbName = PropertiesService.getRateDataName();
    multiStorageAccess = StorageService.buildMultiStorageAccess(dbName);
    storageObjectListConverter = StorageService.buildStorageObjectListConverter(Rate.class);
  }

  public static void store(String name, Iterable<Rate> rateList) {
    StorageService.StorageObjectList storageObjectList = storageObjectListConverter.convertToStorageObjectList(rateList);
    multiStorageAccess.store(name, storageObjectList);
  }

  public static Iterable<Rate> retrieve(String name) {
    StorageService.StorageObjectList storageObjectList = multiStorageAccess.retrieve(name);
    return storageObjectListConverter.convertFromStorageObjectList(storageObjectList);
  }

  public static void clear() {
    multiStorageAccess.clear();
  }

  public static String rateToString(int rate) {
    int cents = rate % 100;
    int dollars = rate / 100;

    return dollars + "." + (cents < 10 ? "0" + cents : cents);
  }

  public static int stringToRate(String rate) {
    String[] split = rate.split("\\.");
    return Integer.parseInt(split[0]) * 100 + Integer.parseInt(split[1]);
  }
}
