package com.timekeep.back;

import com.timekeep.data.Jobsite;

import java.util.ArrayList;
import java.util.List;

public class JobsiteService {
  private static final StorageService.StorageAccess storageAccess;
  private static final StorageService.StorageObjectConverter<Jobsite> storageObjectConverter;

  static {
    storageAccess = StorageService.buildStorageAccess(PropertiesService.getJobsiteDatabaseName());
    storageObjectConverter = StorageService.buildStorageObjectConverter(Jobsite.class);
  }

  public static List<Jobsite> getJobsites() {

    StorageService.StorageObjectList list = storageAccess.retrieveAll();
    ArrayList<Jobsite> jobsiteList = new ArrayList(list.getSize());

    for (StorageService.StorageObject object : list) {
      Jobsite jobsite = storageObjectConverter.convertFromStorageObject(object);
      jobsiteList.add(jobsite);
    }

    return jobsiteList;
  }

  public static void storeJobsite(Jobsite jobsite) {
    StorageService.StorageObject storageObject = storageObjectConverter.convertToStorageObject(jobsite);
    storageAccess.store(jobsite.name, storageObject);
  }

  public static Jobsite getJobsite(String name) {
    StorageService.StorageObject storageObject = storageAccess.retrieve(name);
    return storageObjectConverter.convertFromStorageObject(storageObject);
  }
}
