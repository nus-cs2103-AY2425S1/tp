package seedu.address.model.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class CommentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Comment(null));
    }

    @Test
    public void constructor_invalidComment_throwsIllegalArgumentException() {
        String invalidComment = "";
        assertThrows(IllegalArgumentException.class, () -> new Comment(invalidComment));
    }

    @Test
    public void isValidComment() {
        // null name
        assertThrows(NullPointerException.class, () -> Comment.isValidComment(null));

        // invalid name
        assertFalse(Comment.isValidComment("")); // empty string
        assertFalse(Comment.isValidComment(" ")); // spaces only
        assertFalse(Comment.isValidComment("^")); // only non-alphanumeric characters
        assertFalse(Comment.isValidComment("prefer another set of cutlery*")); // contains non-alphanumeric characters

        // valid name
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

        // different types -> returns false
        assertFalse(comment.equals(5.0f));

        // different values -> returns false
        assertFalse(comment.equals(new Comment("Other Valid Comment")));
    }

}
