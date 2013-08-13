package com.timekeep.connect;

import com.timekeep.back.GroupService;
import com.timekeep.data.Group;
import com.timekeep.front.GroupSelectionPresenter;
import com.timekeep.front.PrimaryPresenter;

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
    //PrimaryPresenter.refresh();
    return group;
  }
}
