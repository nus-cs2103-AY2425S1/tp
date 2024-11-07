package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class TagsTest {

    public final Tag testTag = new Tag("test");
    public final Tags testTags = new Tags(Set.of(testTag));

    @Test
    public void constructor_null_returnsEmptyTags() {
        Set<Tag> expectedTagSet = new HashSet<>();
        Tags actualTags = new Tags();
        Set<Tag> actualTagSet = actualTags.getTags();
        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void constructor_validTags_returnsCorrectTags() {
        Set<Tag> expectedTagSet = new HashSet<>();
        expectedTagSet.add(testTag);
        Tags actualTags = new Tags(Set.of(testTag));
        Set<Tag> actualTagSet = actualTags.getTags();
        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void tagExists_validInput_returnsCorrectly() {
        Tags sameCaseTags = new Tags(Set.of(new Tag("test")));
        Tags diffCaseTags = new Tags(Set.of(new Tag("TEST")));
        Tags diffTags = new Tags(Set.of(new Tag("0")));
        assertTrue(testTags.tagExists(sameCaseTags));
        assertTrue(testTags.tagExists(diffCaseTags));
        assertFalse(testTags.tagExists(diffTags));
    }

    @Test
    public void equals_validInput_returnsCorrectly() {
        Tag anotherTestTag = new Tag("testing");
        Tags tags = new Tags(Set.of(testTag, anotherTestTag));

        Tags sameCaseTags = new Tags(Set.of(testTag, anotherTestTag));

        Tag diffCaseTag = new Tag("TEST");
        Tags diffCaseTags = new Tags(Set.of(diffCaseTag, anotherTestTag));

        assertTrue(tags.equals(sameCaseTags));
        assertTrue(tags.equals(diffCaseTags));
        assertFalse(tags.equals(testTags));
    }
}
