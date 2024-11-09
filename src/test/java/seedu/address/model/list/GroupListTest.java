package seedu.address.model.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.group.Group;

public class GroupListTest {
    private GroupList groupList;

    @BeforeEach
    void setUp() {
        groupList = new GroupList();
    }

    @Test
    public void constructor_null_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new GroupList(null));
    }

    @Test
    void testIsEmpty() {
        assertTrue(groupList.isEmpty());
    }

    @Test
    void testAddGroup_lowerCase_whenModifiable() {
        Group group = new Group("group 1");
        groupList.addGroup(group);
        assertFalse(groupList.isEmpty());
        assertEquals(1, groupList.getUnmodifiableGroups().size());
    }

    @Test
    void testAddGroup_upperCase_whenModifiable() {
        Group group = new Group("GROUP 1");
        groupList.addGroup(group);
        assertFalse(groupList.isEmpty());
        assertEquals(1, groupList.getUnmodifiableGroups().size());
    }

    @Test
    void testAddDuplicateGroup_inSameCase() {
        Group group1 = new Group("group 1");
        Group group2 = new Group("group 1");
        groupList.addGroup(group1);
        groupList.addGroup(group2);
        assertEquals(1, groupList.getUnmodifiableGroups().size());
    }

    @Test
    void testAddDuplicateGroup_multipleSpaces() {
        Group group1 = new Group("group 1");
        Group group2 = new Group("group      1");
        groupList.addGroup(group1);
        groupList.addGroup(group2);
        assertEquals(1, groupList.getUnmodifiableGroups().size());
    }

    @Test
    void testAddDuplicateGroup_inDifferentCases() {
        Group group1 = new Group("group 1");
        Group group2 = new Group("GROUP 1");
        groupList.addGroup(group1);
        groupList.addGroup(group2);
        assertEquals(1, groupList.getUnmodifiableGroups().size());
    }

    @Test
    public void testSize() {
        Group group = new Group("group 1");
        groupList.addGroup(group);
        assertEquals(1, groupList.size());
    }

    @Test
    void testMakeListUnmodifiable() {
        groupList.makeListUnmodifiable();
        assertThrows(UnsupportedOperationException.class, () -> groupList.addGroup(new Group("New Group")));
    }

    @Test
    void testAddAllGroups_whenModifiable() {
        Group group1 = new Group("group 1");
        Group group2 = new Group("group 2");
        GroupList anotherGroupList = new GroupList();
        anotherGroupList.addGroup(group1);
        anotherGroupList.addGroup(group2);

        groupList.addAll(anotherGroupList);
        assertEquals(2, groupList.getUnmodifiableGroups().size());
    }

    @Test
    void testAddAll_whenUnmodifiable() {
        groupList.makeListUnmodifiable();

        GroupList anotherGroupList = new GroupList();
        anotherGroupList.addGroup(new Group("group 1"));

        assertThrows(UnsupportedOperationException.class, () -> groupList.addAll(anotherGroupList));
    }

    @Test
    void testMakeCopy() {
        groupList.addGroup(new Group("group 1"));
        GroupList copy = groupList.makeCopy();

        // The copy should not be the same object as original
        assertNotSame(groupList, copy);

        // The lists have to be of the same values
        assertEquals(groupList, copy);

        // The lists have to be of the same size
        assertEquals(groupList.getUnmodifiableGroups().size(), copy.getUnmodifiableGroups().size());
    }

    @Test
    void testIterator() {
        Group firstGroup = new Group("group 1");
        Group secondGroup = new Group("group 2");
        groupList.addGroup(firstGroup);
        groupList.addGroup(secondGroup);

        // Using the iterator to check the groups
        Iterator<Group> iterator = groupList.iterator();

        // Check if there is the first group
        assertTrue(iterator.hasNext());
        assertNotNull(iterator.next());

        //Check if there is the second group
        assertTrue(iterator.hasNext());
        assertNotNull(iterator.next());

        // Check that there should not be a third group
        assertFalse(iterator.hasNext());
    }

    @Test
    public void equals() {
        GroupList groups = new GroupList();
        GroupList anotherGroups = new GroupList();
        Group group = new Group("group 1");

        GroupList groupsWithVal = new GroupList();
        GroupList groupsWithVal2 = new GroupList();
        groupsWithVal.addGroup(group);
        groupsWithVal2.addGroup(group);

        // same object -> returns true
        assertTrue(groups.equals(groups));

        // same values -> returns true
        assertTrue(groupsWithVal.equals(groupsWithVal2));

        // same empty values -> returns true
        assertTrue(groups.equals(anotherGroups));

        // different types -> returns false
        assertFalse(groupsWithVal.equals("group 1"));

        // null -> returns false
        assertFalse(groupsWithVal.equals(null));

        // different values -> returns false
        assertFalse(groups.equals(groupsWithVal));
    }

    @Test
    public void testHashCode_sameGroupList_shouldHaveSameHashCode() {
        Group group = new Group("group 1");

        GroupList groups1 = new GroupList();
        GroupList groups2 = new GroupList();

        // Add the same elements into the list
        groups1.addGroup(group);
        groups2.addGroup(group);

        // Assert that the hash codes are the same
        assertEquals(groups1.hashCode(), groups2.hashCode());
    }

    @Test
    public void testHashCode_differentGroupList_shouldHaveDifferentHashCode() {
        Group group1 = new Group("group 1");
        Group group2 = new Group("group 2");

        GroupList groups1 = new GroupList();
        GroupList groups2 = new GroupList();

        // Add different elements into the lists
        groups1.addGroup(group1);
        groups2.addGroup(group2);

        // Assert that the hash codes are not the same
        assertNotEquals(groups1.hashCode(), groups2.hashCode());
    }
}
