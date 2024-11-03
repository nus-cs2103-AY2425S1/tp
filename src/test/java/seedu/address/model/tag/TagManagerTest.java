package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TagManagerTest {

    private TagManager tagManager;

    @BeforeEach
    void setUp() {
        tagManager = new TagManager();
    }

    @Test
    void containsTag_tagExists_true() {
        Tag tag = new Tag("CS2103T");
        tagManager.getOrCreateTag(tag.tagName);
        assertTrue(tagManager.containsTag(tag));
    }

    @Test
    void containsTag_tagDoesNotExist_false() {
        Tag tag = new Tag("CS2103T");
        assertFalse(tagManager.containsTag(tag));
    }

    @Test
    void getOrCreateTag_newTag_createsTagAndIncrements() {
        Tag createdTag = tagManager.getOrCreateTag("CS2103T");
        assertNotNull(createdTag);
        assertEquals("CS2103T", createdTag.tagName);
        assertEquals(1, createdTag.getOccurrences());
    }

    @Test
    void getOrCreateTag_existingTag_incrementsOccurrences() {
        tagManager.getOrCreateTag("CS2103T");
        Tag existingTag = tagManager.getOrCreateTag("CS2103T");
        assertEquals(2, existingTag.getOccurrences());
    }

    @Test
    void getOrCreateTag_multipleTags_createsAndReturnsTrackedSet() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("CS2103T"));
        tags.add(new Tag("CS2030S"));
        Set<Tag> trackedTags = tagManager.getOrCreateTag(tags);
        assertEquals(2, trackedTags.size());
        assertTrue(tagManager.containsTag(new Tag("CS2103T")));
        assertTrue(tagManager.containsTag(new Tag("CS2030S")));
    }

    @Test
    void removeTagOccurrence_singleTag_removesTagWhenNoOccurrences() {
        Tag tag = tagManager.getOrCreateTag("CS2103T");
        tagManager.removeTagOccurrence(tag);
        assertFalse(tagManager.containsTag(tag));
    }

    @Test
    void removeTagOccurrence_multipleTags_decrementsOrRemoves() {
        Tag tag1 = tagManager.getOrCreateTag("CS2103T");
        tagManager.getOrCreateTag("CS2103T");
        tagManager.removeTagOccurrence(tag1);
        assertTrue(tagManager.containsTag(tag1));
        tagManager.removeTagOccurrence(tag1);
        assertFalse(tagManager.containsTag(tag1));
    }

    @Test
    void setTagCategory_existingTag_updatesCategory() {
        Tag tag = tagManager.getOrCreateTag("CS2103T");
        TagCategory newCategory = TagCategory.ACADEMICS;
        tagManager.setTagCategory("CS2103T", newCategory);
        assertEquals(newCategory, tagManager.getTagCategory(tag));
    }

    @Test
    void setTagCategory_nonExistingTag_noUpdate() {
        TagCategory category = TagCategory.ACADEMICS;
        assertThrows(NullPointerException.class, () -> tagManager.setTagCategory("CS2103T", category));
    }

    @Test
    void removeTags_entireSet_removesAllTags() {
        Tag tag1 = tagManager.getOrCreateTag("CS2103T");
        Tag tag2 = tagManager.getOrCreateTag("CS2030S");
        Set<Tag> tagsToRemove = new HashSet<>();
        tagsToRemove.add(tag1);
        tagsToRemove.add(tag2);
        tagManager.removeTags(tagsToRemove);
        assertFalse(tagManager.containsTag(tag1));
        assertFalse(tagManager.containsTag(tag2));
    }

    @Test
    void clearAllTags_clearsAllTags_emptyTagList() {
        tagManager.getOrCreateTag("CS2103T");
        tagManager.getOrCreateTag("CS2030S");
        tagManager.clearAllTags();
        assertTrue(tagManager.asTagList().isEmpty());
    }
}
