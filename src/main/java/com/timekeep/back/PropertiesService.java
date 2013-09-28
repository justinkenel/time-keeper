package com.timekeep.back;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesService {
  private static final String PROPERTIES_FILE_PATH = "settings";

  private static final File propertiesFile;
  private static final Properties properties;

  static {
    propertiesFile = new File(PROPERTIES_FILE_PATH);

    if (!propertiesFile.exists()) {
      createSettingsFile(propertiesFile);
    }

    properties = new Properties();

    try (FileInputStream inputStream = new FileInputStream(propertiesFile)) {
      properties.load(inputStream);
    } catch (IOException e) {
      throw new IllegalStateException("Unable to load properties file", e);
    }
  }

  private static void createSettingsFile(File file) {
    try {
      file.createNewFile();
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to create properties file", e);
    }
  }

  public static String getDatabaseFileName() {
    return get("database.file.name", "timekeep.db");
  }

  public static String getEmployeeDatabaseName() {
    return get("employee.database.name", "employee");
  }

  public static String getJobsiteDatabaseName() {
    return get("employee.jobsite.database.name", "jobsite");
  }

  public static String getRateDataName() {
    return get("employee.rate.database.name", "rate");
  }

  public static String getEntryDataName() {
    return get("employee.entry.database.name", "entry");
  }

  public static String getGroupDatabaseName() {
    return get("group.database.name", "group");
  }

  private static String get(String key, String defaultValue) {
    String value = (String) properties.get(key);
    return value == null ? defaultValue : value;
  }
}
