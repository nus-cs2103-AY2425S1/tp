package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalTags;

public class TagListTest {
    private TagList tagList;
    private Tag friendsTag;
    private Tag colleaguesTag;

    @BeforeEach
    public void setUp() {
        tagList = new TagList();
        friendsTag = TypicalTags.FRIENDS;
        colleaguesTag = TypicalTags.COLLEAGUES;
    }

    @Test
    public void addTag_newTag_success() {
        assertTrue(tagList.addTag(friendsTag));
        assertTrue(tagList.contains(friendsTag));
    }

    @Test
    public void addTag_duplicateTag_failure() {
        tagList.addTag(friendsTag);
        assertFalse(tagList.addTag(friendsTag));
    }

    @Test
    public void deleteTag_existingTag_success() {
        tagList.addTag(friendsTag);
        assertTrue(tagList.deleteTag(friendsTag));
    }

    @Test
    public void deleteTag_nonExistentTag_failure() {
        assertFalse(tagList.deleteTag(friendsTag));
    }

    @Test
    public void contains_tagInList_success() {
        tagList.addTag(friendsTag);
        assertTrue(tagList.contains(friendsTag));
    }

    @Test
    public void contains_tagNotInList_failure() {
        assertFalse(tagList.contains(friendsTag));
    }

    @Test
    public void checkAcceptableTagListSize_belowMaximum_success() {
        for (int i = 0; i < TagList.MAXIMUM_TAGLIST_SIZE - 2; i++) {
            tagList.addTag(new Tag(String.valueOf(i)));
        }

        assertTrue(tagList.checkAcceptableSize(1));
    }

    @Test
    public void checkAcceptableTagListSize_addMultipleTagsBelowMaximum_success() {
        for (int i = 0; i < TagList.MAXIMUM_TAGLIST_SIZE - 3; i++) {
            tagList.addTag(new Tag(String.valueOf(i)));
        }

        assertTrue(tagList.checkAcceptableSize(2));
    }

    @Test
    public void checkAcceptableTagListSize_atMaximum_success() {
        for (int i = 0; i < TagList.MAXIMUM_TAGLIST_SIZE - 1; i++) {
            tagList.addTag(new Tag(String.valueOf(i)));
        }

        assertTrue(tagList.checkAcceptableSize(1));
    }

    @Test
    public void checkAcceptableTagListSize_aboveMaximum_failure() {
        for (int i = 0; i < TagList.MAXIMUM_TAGLIST_SIZE; i++) {
            tagList.addTag(new Tag(String.valueOf(i)));
        }

        assertFalse(tagList.checkAcceptableSize(1));
    }

    @Test
    public void equals_sameTagLists_success() {
        TagList otherTagList = new TagList();
        tagList.addTag(friendsTag);
        otherTagList.addTag(friendsTag);

        assertEquals(tagList, otherTagList); // Both lists contain the same tag.
    }

    @Test
    public void equals_differentTagLists_failure() {
        TagList otherTagList = new TagList();
        tagList.addTag(friendsTag);
        otherTagList.addTag(colleaguesTag);

        assertFalse(tagList.equals(otherTagList)); // Different tags in the lists.
    }

    @Test
    public void equals_emptyTagLists_success() {
        TagList otherTagList = new TagList();
        assertEquals(tagList, otherTagList); // Both lists are empty.
    }

    @Test
    public void toString_singleTag_correctFormat() {
        tagList.addTag(friendsTag);
        assertEquals("friends", tagList.toString());
    }

    @Test
    public void toString_multipleTags_correctFormat() {
        tagList.addTag(friendsTag);
        tagList.addTag(colleaguesTag);
        assertEquals("friends, colleagues", tagList.toString());
    }
}
