package tahub.contacts.model.comment;

import org.junit.jupiter.api.Test;
import tahub.contacts.model.person.Person;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class CommentTest {

    @Test
    void testCommenter() {
        Person commenter = new Person("Alice", "1234567890", "alice@example.com", "123 Street", new HashSet<>());
        Person receiver = new Person("Bob", "9876543210", "bob@example.com", "321 Avenue", new HashSet<>());

        Comment comment = new Comment(commenter, receiver, "Great job!");
        assertEquals(comment.getCommenter(), commenter);
    }

    @Test
    void testReceiver() {
        Person commenter = new Person("Alice", "1234567890", "alice@example.com", "123 Street", new HashSet<>());
        Person receiver = new Person("Bob", "9876543210", "bob@example.com", "321 Avenue", new HashSet<>());

        Comment comment = new Comment(commenter, receiver, "Great job!");
        assertEquals(comment.getReceiver(), receiver);
    }

    @Test
    void testCommentText() {
        Person commenter = new Person("Alice", "1234567890", "alice@example.com", "123 Street", new HashSet<>());
        Person receiver = new Person("Bob", "9876543210", "bob@example.com", "321 Avenue", new HashSet<>());

        Comment comment = new Comment(commenter, receiver, "Great job!");
        assertEquals(comment.getCommentText(), "Great job!");
    }

    @Test
    void testEquals() {
        Person commenter = new Person("Alice", "1234567890", "alice@example.com", "123 Street", new HashSet<>());
        Person receiver = new Person("Bob", "9876543210", "bob@example.com", "321 Avenue", new HashSet<>());
        Comment comment1 = new Comment(commenter, receiver, "Great job!");

        Person commenter2 = new Person("Alice", "1234567890", "alice@example.com", "123 Street", new HashSet<>());
        Person receiver2 = new Person("Bob", "9876543210", "bob@example.com", "321 Avenue", new HashSet<>());
        Comment comment2 = new Comment(commenter2, receiver2, "Great job!");

        assertTrue(comment1.equals(comment2));
    }

    @Test
    void testNotEquals() {
        Person commenter = new Person("Alice", "1234567890", "alice@example.com", "123 Street", new HashSet<>());
        Person receiver = new Person("Bob", "9876543210", "bob@example.com", "321 Avenue", new HashSet<>());
        Comment comment1 = new Comment(commenter, receiver, "Great job!");

        Person commenter2 = new Person("Charlie", "1112223334", "charlie@example.com", "456 Lane", new HashSet<>());
        Person receiver2 = new Person("Dave", "4445556667", "dave@example.com", "789 Boulevard", new HashSet<>());
        Comment comment2 = new Comment(commenter2, receiver2, "Nice work!");

        assertFalse(comment1.equals(comment2));
    }
}