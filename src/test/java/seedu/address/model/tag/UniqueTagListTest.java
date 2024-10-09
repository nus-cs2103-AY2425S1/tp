package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

public class UniqueTagListTest {

    private final UniqueTagList uniqueTagList = new UniqueTagList();
    private final Tag friendTag = new Tag(new TagName("friend"));
    private final Tag colleagueTag = new Tag(new TagName("colleague"));

    @Test
    public void contains_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.contains(null));
    }

    @Test
    public void contains_tagNotInList_returnsFalse() {
        assertFalse(uniqueTagList.contains(friendTag));
    }

    @Test
    public void contains_tagInList_returnsTrue() {
        uniqueTagList.add(friendTag);
        assertTrue(uniqueTagList.contains(friendTag));
    }

    @Test
    public void contains_tagWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTagList.add(friendTag);
        Tag sameTag = new Tag(new TagName("friend"));
        assertTrue(uniqueTagList.contains(sameTag));
    }

    @Test
    public void add_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.add(null));
    }

    @Test
    public void add_duplicateTag_throwsDuplicateTagException() {
        uniqueTagList.add(friendTag);
        assertThrows(DuplicateTagException.class, () -> uniqueTagList.add(friendTag));
    }

    @Test
    public void setTag_nullTargetTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.setTag(null, friendTag));
    }

    @Test
    public void setTag_nullEditedTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.setTag(friendTag, null));
    }

    @Test
    public void setTag_targetTagNotInList_throwsTagNotFoundException() {
        assertThrows(TagNotFoundException.class, () -> uniqueTagList.setTag(friendTag, colleagueTag));
    }

    @Test
    public void setTag_editedTagIsSame_success() {
        uniqueTagList.add(friendTag);
        uniqueTagList.setTag(friendTag, friendTag);
        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        expectedUniqueTagList.add(friendTag);
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setTag_editedTagHasSameIdentity_success() {
        uniqueTagList.add(friendTag);
        Tag newFriendTag = new Tag(new TagName("friend"));
        uniqueTagList.setTag(friendTag, newFriendTag);
        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        expectedUniqueTagList.add(newFriendTag);
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setTag_editedTagHasDifferentIdentity_success() {
        uniqueTagList.add(friendTag);
        uniqueTagList.setTag(friendTag, colleagueTag);
        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        expectedUniqueTagList.add(colleagueTag);
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setTag_editedTagHasDifferentIdentity_throwsDuplicateTagException() {
        uniqueTagList.add(friendTag);
        uniqueTagList.add(colleagueTag);
        assertThrows(DuplicateTagException.class, () -> uniqueTagList.setTag(friendTag, colleagueTag));
    }

    @Test
    public void remove_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.remove(null));
    }

    @Test
    public void remove_tagNotInList_throwsTagNotFoundException() {
        assertThrows(TagNotFoundException.class, () -> uniqueTagList.remove(friendTag));
    }

    @Test
    public void remove_existingTag_removesTag() {
        uniqueTagList.add(friendTag);
        uniqueTagList.remove(friendTag);
        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setTags_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.setTags(null));
    }

    @Test
    public void setTags_uniqueTags_success() {
        List<Tag> uniqueTags = Arrays.asList(friendTag, colleagueTag);
        uniqueTagList.setTags(uniqueTags);
        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        expectedUniqueTagList.add(friendTag);
        expectedUniqueTagList.add(colleagueTag);
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> uniqueTagList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        UniqueTagList anotherList = new UniqueTagList();
        anotherList.add(friendTag);
        uniqueTagList.add(friendTag);
        assertTrue(uniqueTagList.equals(anotherList));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        UniqueTagList anotherList = new UniqueTagList();
        anotherList.add(colleagueTag);
        uniqueTagList.add(friendTag);
        assertFalse(uniqueTagList.equals(anotherList));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(uniqueTagList.equals(uniqueTagList));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(uniqueTagList.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(uniqueTagList.equals(5));
    }

    @Test
    public void hashCode_sameValues_returnsSameHashCode() {
        UniqueTagList anotherList = new UniqueTagList();
        anotherList.add(friendTag);
        uniqueTagList.add(friendTag);
        assertEquals(uniqueTagList.hashCode(), anotherList.hashCode());
    }

    @Test
    public void hashCode_differentValues_returnsDifferentHashCode() {
        UniqueTagList anotherList = new UniqueTagList();
        anotherList.add(colleagueTag);
        uniqueTagList.add(friendTag);
        assertFalse(uniqueTagList.hashCode() == anotherList.hashCode());
    }
}
