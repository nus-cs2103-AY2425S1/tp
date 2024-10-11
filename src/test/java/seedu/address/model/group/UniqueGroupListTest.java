package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.student.exceptions.DuplicatePersonException;
import seedu.address.model.student.exceptions.PersonNotFoundException;

public class UniqueGroupListTest {

    private static final Group DUMMY_GROUP = new Group(new GroupName("Team1"));
    private final UniqueGroupList uniqueGroupList = new UniqueGroupList();

    @Test
    public void contains_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.contains(null));
    }

    @Test
    public void contains_groupNotInList_returnsFalse() {
        assertFalse(uniqueGroupList.contains(DUMMY_GROUP));
    }

    @Test
    public void contains_groupInList_returnsTrue() {
        uniqueGroupList.add(DUMMY_GROUP);
        assertTrue(uniqueGroupList.contains(DUMMY_GROUP));
    }

    @Test
    public void add_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.add(null));
    }

    @Test
    public void add_duplicateGroup_throwsDuplicateGroupException() {
        uniqueGroupList.add(DUMMY_GROUP);
        assertThrows(DuplicatePersonException.class, () -> uniqueGroupList.add(DUMMY_GROUP));
    }

    @Test
    public void setGroup_nullTargetGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.setGroup(null, DUMMY_GROUP));
    }

    @Test
    public void setGroup_nullEditedGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.setGroup(DUMMY_GROUP, null));
    }

    @Test
    public void setGroup_targetGroupNotInList_throwsGroupNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueGroupList.setGroup(DUMMY_GROUP, DUMMY_GROUP));
    }

    @Test
    public void remove_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.remove(null));
    }

    @Test
    public void remove_groupDoesNotExist_throwsGroupNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueGroupList.remove(DUMMY_GROUP));
    }

    @Test
    public void remove_existingGroup_removesGroup() {
        uniqueGroupList.add(DUMMY_GROUP);
        uniqueGroupList.remove(DUMMY_GROUP);
        UniqueGroupList expectedUniqueGroupList = new UniqueGroupList();
        assertEquals(expectedUniqueGroupList, uniqueGroupList);
    }

    @Test
    public void setGroup_nullUniqueGroupList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGroupList.setGroup((Group) null, (Group) null));
    }

    @Test
    public void setGroup_listWithDuplicateGroup_throwsDuplicatePersonException() {
        uniqueGroupList.add(DUMMY_GROUP);
        assertThrows(DuplicatePersonException.class, () -> uniqueGroupList.setGroup(DUMMY_GROUP, DUMMY_GROUP));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueGroupList.asUnmodifiableObservableList().toString(), uniqueGroupList.toString());
    }
}
