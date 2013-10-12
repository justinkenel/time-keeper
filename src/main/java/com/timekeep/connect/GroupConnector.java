package com.timekeep.connect;

import com.timekeep.back.DateService;
import com.timekeep.back.GroupService;
import com.timekeep.data.Entry;
import com.timekeep.data.Group;
import com.timekeep.data.StrictDate;
import com.timekeep.data.StrictTime;
import com.timekeep.front.GroupSelectionPresenter;

public class GroupConnector {
  private String name;

  private GroupConnector(String name) {
    this.name = name;
  }

  public static GroupConnector connector() {
    return connector("");
  }

  public static GroupConnector connector(String name) {
    return new GroupConnector(name);
  }

  public GroupConnector setName(String name) {
    this.name = name;
    return this;
  }

  public Group createAndStore() {
    Group group = new Group(name);
    GroupService.store(group);
    GroupSelectionPresenter.addGroup(group);
    return group;
  }

  public Entry addStartEntry() {
    StrictDate date = DateService.today();
    StrictTime time = DateService.now();
    Entry newEntry = new Entry(date, time, DateService.INVALID_TIME, "", DateService.INVALID_TIME);

    return newEntry;
  }
}
