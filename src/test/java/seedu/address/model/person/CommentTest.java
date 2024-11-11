package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CommentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Comment(null));
    }

    @Test
    public void isValidComment() {
        // null name
        assertThrows(NullPointerException.class, () -> Comment.isValidComment(null));

        // valid name
        assertTrue(Comment.isValidComment("")); // empty string
        assertTrue(Comment.isValidComment(" ")); // spaces only
        assertTrue(Comment.isValidComment("^")); // only non-alphanumeric characters
        assertTrue(Comment.isValidComment("prefer another set of cutlery*")); // contains non-alphanumeric characters

        assertTrue(Comment.isValidComment("prefer another set of cutlery")); // alphabets only
        assertTrue(Comment.isValidComment("12345")); // numbers only
        assertTrue(Comment.isValidComment("is 1st member")); // alphanumeric characters
        assertTrue(Comment.isValidComment("Prefer Another Set of Cutlery")); // with capital letters
        assertTrue(Comment.isValidComment("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        Comment comment = new Comment("Valid Comment");

        // same values -> returns true
        assertTrue(comment.equals(new Comment("Valid Comment")));

        // same object -> returns true
        assertTrue(comment.equals(comment));

        // null -> returns false
        assertFalse(comment.equals(null));
    }
}
