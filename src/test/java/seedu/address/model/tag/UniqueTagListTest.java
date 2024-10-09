package seedu.address.model.tag;

import org.junit.jupiter.api.Test;
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.FLORIST;
import static seedu.address.testutil.TypicalPersons.PHOTOGRAPHER;

public class UniqueTagListTest {
    private final UniqueTagList uniqueTagList = new UniqueTagList();

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
    public void toStringTest() {
        assertEquals(uniqueTagList.asUnmodifiableObservableList().toString(), uniqueTagList.toString());
    }
}
