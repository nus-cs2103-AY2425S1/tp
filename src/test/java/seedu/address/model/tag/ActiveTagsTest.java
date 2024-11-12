package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.TagColors;

public class ActiveTagsTest {
    private ActiveTags activeTags;

    @BeforeEach
    public void setUp() {
        HashMap<Tag, Integer> occurrences = new HashMap<>();
        occurrences.put(new Tag("tag1"), 5);
        occurrences.put(new Tag("tag2"), 4);
        occurrences.put(new Tag("tag3"), 3);

        TagColors tagColors = new TagColors();
        activeTags = new ActiveTags(occurrences, tagColors);
    }

    @Test
    public void incrementTags_oneTag_correctOccurrence() {
        Set<Tag> tagSet = new HashSet<>();
        Tag tag = new Tag("tag1");
        tagSet.add(tag);
        activeTags.incrementTags(tagSet);
        HashMap<Tag, Integer> map = activeTags.getMap();

        assertEquals(map.get(tag), 6);
    }

    @Test
    public void incrementTags_multipleTags_correctOccurrence() {
        Set<Tag> tagSet = new HashSet<>();
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        Tag tag3 = new Tag("tag3");
        tagSet.add(tag1);
        tagSet.add(tag2);
        tagSet.add(tag3);

        activeTags.incrementTags(tagSet);
        HashMap<Tag, Integer> map = activeTags.getMap();

        assertEquals(map.get(tag1), 6);
        assertEquals(map.get(tag2), 5);
        assertEquals(map.get(tag3), 4);
    }

    @Test
    public void decrementTags_oneTag_correctOccurence() {
        Set<Tag> tagSet = new HashSet<>();
        Tag tag = new Tag("tag1");
        tagSet.add(tag);
        activeTags.decrementTags(tagSet);
        HashMap<Tag, Integer> map = activeTags.getMap();

        assertEquals(map.get(tag), 4);
    }

    @Test
    public void decrementTags_multipleTags_correctOccurrence() {
        Set<Tag> tagSet = new HashSet<>();
        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        Tag tag3 = new Tag("tag3");
        tagSet.add(tag1);
        tagSet.add(tag2);
        tagSet.add(tag3);

        activeTags.decrementTags(tagSet);
        HashMap<Tag, Integer> map = activeTags.getMap();

        assertEquals(map.get(tag1), 4);
        assertEquals(map.get(tag2), 3);
        assertEquals(map.get(tag3), 2);
    }
}
