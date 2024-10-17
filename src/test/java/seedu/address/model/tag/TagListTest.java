package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TagListTest {
    private TagList tagList;
    private Tag tag1;
    private Tag tag2;
    private Tag tag3;

    @BeforeEach
    public void setUp() {
        tagList = new TagList();
        tag1 = new Tag("friend");
        tag2 = new Tag("colleague");
        tag3 = new Tag("family");
    }

    @Test
    public void addTag_newTag_success() {
        assertTrue(tagList.addTag(tag1)); // Tag should be added.
        assertTrue(tagList.contains(tag1)); // Tag should be present in the list.
    }

    @Test
    public void addTag_duplicateTag_failure() {
        tagList.addTag(tag1);
        assertFalse(tagList.addTag(tag1)); // Adding the same tag again should return false.
    }

    @Test
    public void contains_tagInList_success() {
        tagList.addTag(tag2);
        assertTrue(tagList.contains(tag2)); // Tag should be found in the list.
    }

    @Test
    public void contains_tagNotInList_failure() {
        assertFalse(tagList.contains(tag3)); // Tag should not be found in the empty list.
    }

    @Test
    public void equals_sameTagLists_success() {
        TagList otherTagList = new TagList();
        tagList.addTag(tag1);
        otherTagList.addTag(tag1);

        assertEquals(tagList, otherTagList); // Both lists contain the same tag.
    }

    @Test
    public void equals_differentTagLists_failure() {
        TagList otherTagList = new TagList();
        tagList.addTag(tag1);
        otherTagList.addTag(tag2);

        assertFalse(tagList.equals(otherTagList)); // Different tags in the lists.
    }

    @Test
    public void equals_emptyTagLists_success() {
        TagList otherTagList = new TagList();
        assertEquals(tagList, otherTagList); // Both lists are empty.
    }

    @Test
    public void toString_singleTag_correctFormat() {
        tagList.addTag(tag1);
        assertEquals("friend", tagList.toString()); // Single tag should be represented correctly.
    }

    @Test
    public void toString_multipleTags_correctFormat() {
        tagList.addTag(tag1);
        tagList.addTag(tag2);
        assertEquals("friend, colleague", tagList.toString()); // Multiple tags should be separated by a comma.
    }
}
