package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.FLORIST;
import static seedu.address.testutil.TypicalPersons.PHOTOGRAPHER;

import java.util.Arrays;
import java.util.Collections;
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
        assertFalse(uniqueTagList.contains(FLORIST));
    }

    @Test
    public void contains_tagInList_returnsTrue() {
        uniqueTagList.add(FLORIST);
        assertTrue(uniqueTagList.contains(FLORIST));
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
        uniqueTagList.add(FLORIST);
        assertThrows(DuplicateTagException.class, () -> uniqueTagList.add(FLORIST));
    }

    @Test
    public void setTag_nullTargetTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.setTag(null, FLORIST));
    }

    @Test
    public void setTag_nullEditedTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.setTag(FLORIST, null));
    }

    @Test
    public void setTag_targetTagNotInList_throwsTagNotFoundException() {
        assertThrows(TagNotFoundException.class, () -> uniqueTagList.setTag(FLORIST, FLORIST));
    }

    @Test
    public void setTag_editedTagIsSameTag_success() {
        uniqueTagList.add(FLORIST);
        uniqueTagList.setTag(FLORIST, FLORIST);
        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        expectedUniqueTagList.add(FLORIST);
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setTag_editedTagHasDifferentIdentity_success() {
        uniqueTagList.add(FLORIST);
        uniqueTagList.setTag(FLORIST, PHOTOGRAPHER);
        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        expectedUniqueTagList.add(PHOTOGRAPHER);
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setTag_editedTagHasNonUniqueIdentity_throwsDuplicateTagException() {
        uniqueTagList.add(FLORIST);
        uniqueTagList.add(PHOTOGRAPHER);
        assertThrows(DuplicateTagException.class, () -> uniqueTagList.setTag(FLORIST, PHOTOGRAPHER));
    }

    @Test
    public void remove_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.remove(null));
    }

    @Test
    public void remove_tagDoesNotExist_throwsTagNotFoundException() {
        assertThrows(TagNotFoundException.class, () -> uniqueTagList.remove(FLORIST));
    }

    @Test
    public void remove_existingTag_removesTag() {
        uniqueTagList.add(FLORIST);
        uniqueTagList.remove(FLORIST);
        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setTags_nullUniqueTagList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTagList.setTags((List<Tag>) null));
    }

    @Test
    public void setTags_uniqueTagList_replacesOwnListWithProvidedUniqueTagList() {
        uniqueTagList.add(FLORIST);
        List<Tag> tagList = Collections.singletonList(PHOTOGRAPHER);
        uniqueTagList.setTags(tagList);
        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        expectedUniqueTagList.add(PHOTOGRAPHER);
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setTags_listWithDuplicateTags_throwsDuplicateTagException() {
        List<Tag> listWithDuplicateTags = Arrays.asList(FLORIST, FLORIST);
        assertThrows(DuplicateTagException.class, () -> uniqueTagList.setTags(listWithDuplicateTags));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueTagList.asUnmodifiableObservableList().remove(0));
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

    @Test
    public void toStringTest() {
        assertEquals(uniqueTagList.asUnmodifiableObservableList().toString(), uniqueTagList.toString());
    }
}
