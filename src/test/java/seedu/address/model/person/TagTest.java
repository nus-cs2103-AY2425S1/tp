package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void equals() {
        Tag tag = new Tag("Emily Tan & Jude Law");

        // same object -> returns true
        assertTrue(tag.equals(tag));

        // same values -> returns true
        Tag tagCopy = new Tag(tag.tagName);
        assertTrue(tag.equals(tagCopy));

        // different types -> returns false
        assertFalse(tag.equals(1));

        // null -> returns false
        assertFalse(tag.equals(null));

        // different tag -> returns false
        Tag differentTag = new Tag("Jean Tan & James Loh");
        assertFalse(tag.equals(differentTag));
    }
}
