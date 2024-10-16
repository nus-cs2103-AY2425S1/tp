package seedu.address.model.person;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CommentTest {

    @Test
    public void equals() {
        Comment comment = new Comment("Hello");

        // same object -> returns true
        assertTrue(comment.equals(comment));

        // same values -> returns true
        Comment commentCopy = new Comment(comment.fullComment);
        assertTrue(comment.equals(commentCopy));

        // different types -> returns false
        assertFalse(comment.equals(1));

        // null -> returns false
        assertFalse(comment.equals(null));

        // different Comment -> returns false
        Comment differentComment = new Comment("Bye");
        assertFalse(comment.equals(differentComment));
    }
}
