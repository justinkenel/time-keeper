package com.timekeep.back;

import com.timekeep.data.Employee;
import com.timekeep.data.Entry;
import com.timekeep.data.Group;
import com.timekeep.data.Rate;
import junit.framework.Assert;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class GroupServiceTest {
    @BeforeClass
    public static void before() {
      GroupService.clear();
    }

    @After
    public void after() {
      GroupService.clear();
    }

    @Test
    public void testRoundTrip() {
      Group group = new Group("group");

      GroupService.store(group);
      Group retrievedGroup = GroupService.load("group");

      Assert.assertEquals(group, retrievedGroup);
      Assert.assertNotSame(group, retrievedGroup);
    }

    @Test
    public void testGetGroups() {

      Group group1 = new Group("group1");
      Group group2 = new Group("group2");
      Group group3 = new Group("group3");

      GroupService.store(group1);
      GroupService.store(group2);
      GroupService.store(group3);

      Iterable<Group> groups = GroupService.getGroups();
      ArrayList<Group> expected = new ArrayList<Group>();

      expected.add(group1);
      expected.add(group2);
      expected.add(group3);

      Assert.assertEquals(expected, groups);
    }
}
