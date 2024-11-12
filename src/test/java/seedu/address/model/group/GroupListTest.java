package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGroups.GROUP_A;
import static seedu.address.testutil.TypicalGroups.GROUP_B;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.testutil.GroupBuilder;

public class GroupListTest {

    private final GroupList groupList = new GroupList();

    @Test
    public void contains_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groupList.contains(null));
    }

    @Test
    public void contains_groupNotInList_returnsFalse() {
        assertFalse(groupList.contains(GROUP_A));
    }

    @Test
    public void contains_groupInList_returnsTrue() {
        groupList.add(GROUP_A);
        assertTrue(groupList.contains(GROUP_A));
    }

    @Test
    public void contains_groupWithSameIdentityFieldsInList_returnsFalse() {
        groupList.add(GROUP_A);
        Group editedGroupA = new GroupBuilder(GROUP_A).withGroupName("Different Name").build();
        assertFalse(groupList.contains(editedGroupA));
    }

    @Test
    public void add_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groupList.add(null));
    }

    @Test
    public void add_duplicateGroup_throwsDuplicateGroupException() {
        groupList.add(GROUP_A);
        assertThrows(DuplicateGroupException.class, () -> groupList.add(GROUP_A));
    }

    @Test
    public void setGroup_nullTargetGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groupList.setGroup(null, GROUP_A));
    }

    @Test
    public void setGroup_nullEditedGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groupList.setGroup(GROUP_A, null));
    }

    @Test
    public void setGroup_targetGroupNotInList_throwsGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () -> groupList.setGroup(GROUP_A, GROUP_A));
    }

    @Test
    public void setGroup_editedGroupIsSameGroup_success() {
        groupList.add(GROUP_A);
        groupList.setGroup(GROUP_A, GROUP_A);
        GroupList expectedGroupList = new GroupList();
        expectedGroupList.add(GROUP_A);
        assertEquals(expectedGroupList, groupList);
    }

    @Test
    public void setGroup_editedGroupHasSameIdentity_success() {
        groupList.add(GROUP_A);
        Group editedGroupA = new GroupBuilder(GROUP_A).withGroupName("Different Name").build();
        groupList.setGroup(GROUP_A, editedGroupA);
        GroupList expectedGroupList = new GroupList();
        expectedGroupList.add(editedGroupA);
        assertEquals(expectedGroupList, groupList);
    }

    @Test
    public void setGroup_editedGroupHasDifferentIdentity_success() {
        groupList.add(GROUP_A);
        groupList.setGroup(GROUP_A, GROUP_B);
        GroupList expectedGroupList = new GroupList();
        expectedGroupList.add(GROUP_B);
        assertEquals(expectedGroupList, groupList);
    }

    @Test
    public void setGroup_editedGroupHasNonUniqueIdentity_throwsDuplicateGroupException() {
        groupList.add(GROUP_A);
        groupList.add(GROUP_B);
        assertThrows(DuplicateGroupException.class, () -> groupList.setGroup(GROUP_A, GROUP_B));
    }

    @Test
    public void remove_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groupList.remove(null));
    }

    @Test
    public void remove_groupDoesNotExist_throwsGroupNotFoundException() {
        assertThrows(GroupNotFoundException.class, () -> groupList.remove(GROUP_A));
    }

    @Test
    public void remove_existingGroup_removesGroup() {
        groupList.add(GROUP_A);
        groupList.remove(GROUP_A);
        GroupList expectedGroupList = new GroupList();
        assertEquals(expectedGroupList, groupList);
    }

    @Test
    public void setGroups_nullGroupList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groupList.setGroups((GroupList) null));
    }

    @Test
    public void setGroups_groupList_replacesOwnListWithProvidedGroupList() {
        groupList.add(GROUP_A);
        GroupList expectedGroupList = new GroupList();
        expectedGroupList.add(GROUP_B);
        groupList.setGroups(expectedGroupList);
        assertEquals(expectedGroupList, groupList);
    }

    @Test
    public void setGroups_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> groupList.setGroups((List<Group>) null));
    }

    @Test
    public void setGroups_list_replacesOwnListWithProvidedList() {
        groupList.add(GROUP_A);
        List<Group> groupList = Collections.singletonList(GROUP_B);
        this.groupList.setGroups(groupList);
        GroupList expectedGroupList = new GroupList();
        expectedGroupList.add(GROUP_B);
        assertEquals(expectedGroupList, this.groupList);
    }

    @Test
    public void setGroups_listWithDuplicateGroups_throwsDuplicateGroupException() {
        List<Group> listWithDuplicateGroups = Arrays.asList(GROUP_A, GROUP_A);
        assertThrows(DuplicateGroupException.class, () -> groupList.setGroups(listWithDuplicateGroups));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> groupList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(groupList.asUnmodifiableObservableList().toString(), groupList.toString());
    }

    @Test
    public void iterator_emptyList_returnsEmptyIterator() {
        assertFalse(groupList.iterator().hasNext());
    }

    @Test
    public void iterator_nonEmptyList_returnsIterator() {
        groupList.add(GROUP_A);
        Iterator<Group> iterator = groupList.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(GROUP_A, iterator.next());
    }
    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(groupList.equals(groupList));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        assertFalse(groupList.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(groupList.equals("Not a GroupList"));
    }

    @Test
    public void equals_differentGroupLists_returnsFalse() {
        GroupList otherGroupList = new GroupList();
        otherGroupList.add(GROUP_B);
        assertFalse(groupList.equals(otherGroupList));
    }

    @Test
    public void equals_sameGroupLists_returnsTrue() {
        GroupList otherGroupList = new GroupList();
        groupList.add(GROUP_A);
        otherGroupList.add(GROUP_A);
        assertTrue(groupList.equals(otherGroupList));
    }

    @Test
    public void hashCode_sameGroupLists_returnsSameHashCode() {
        GroupList otherGroupList = new GroupList();
        groupList.add(GROUP_A);
        otherGroupList.add(GROUP_A);
        assertEquals(groupList.hashCode(), otherGroupList.hashCode());
    }

    @Test
    public void hashCode_differentGroupLists_returnsDifferentHashCode() {
        GroupList otherGroupList = new GroupList();
        groupList.add(GROUP_A);
        otherGroupList.add(GROUP_B);
        assertNotEquals(groupList.hashCode(), otherGroupList.hashCode());
    }



}
