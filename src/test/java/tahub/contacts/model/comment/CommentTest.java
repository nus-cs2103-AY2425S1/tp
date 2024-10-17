package tahub.contacts.model.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import tahub.contacts.model.person.Address;
import tahub.contacts.model.person.Email;
import tahub.contacts.model.person.MatriculationNumber;
import tahub.contacts.model.person.Name;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.person.Phone;

public class CommentTest {
    @Test
    void testCommenter() {
        Person commenter = new Person(new MatriculationNumber("A0277100P"), new Name("Alice"), new Phone("1234567890"),
                new Email("alice@example.com"), new Address("123 Street"), new HashSet<>());
        Person receiver = new Person(new MatriculationNumber("A0277101P"), new Name("Bob"), new Phone("9876543210"),
                new Email("bob@example.com"), new Address("321 Avenue"), new HashSet<>());
        Comment comment = new Comment(commenter, receiver, "Great job!");
        assertEquals(comment.getCommenter(), commenter);
    }

    @Test
    void testReceiver() {
        Person commenter = new Person(new MatriculationNumber("A0277100P"), new Name("Alice"), new Phone("1234567890"),
                new Email("alice@example.com"), new Address("123 Street"), new HashSet<>());
        Person receiver = new Person(new MatriculationNumber("A0277101P"), new Name("Bob"), new Phone("9876543210"),
                new Email("bob@example.com"), new Address("321 Avenue"), new HashSet<>());
        Comment comment = new Comment(commenter, receiver, "Great job!");
        assertEquals(comment.getReceiver(), receiver);
    }

    @Test
    void testCommentText() {
        Person commenter = new Person(new MatriculationNumber("A0277100P"), new Name("Alice"), new Phone("1234567890"),
                new Email("alice@example.com"), new Address("123 Street"), new HashSet<>());
        Person receiver = new Person(new MatriculationNumber("A0277101P"), new Name("Bob"), new Phone("9876543210"),
                new Email("bob@example.com"), new Address("321 Avenue"), new HashSet<>());
        Comment comment = new Comment(commenter, receiver, "Great job!");
        assertEquals(comment.getCommentText(), "Great job!");
    }

    @Test
    void testEquals() {
        Person commenter = new Person(new MatriculationNumber("A0277100P"), new Name("Alice"), new Phone("1234567890"),
                new Email("alice@example.com"), new Address("123 Street"), new HashSet<>());
        Person receiver = new Person(new MatriculationNumber("A0277101P"), new Name("Bob"), new Phone("9876543210"),
                new Email("bob@example.com"), new Address("321 Avenue"), new HashSet<>());
        Comment comment1 = new Comment(commenter, receiver, "Great job!");
        Person commenter2 = new Person(new MatriculationNumber("A0277100P"), new Name("Alice"), new Phone("1234567890"),
                new Email("alice@example.com"), new Address("123 Street"), new HashSet<>());
        Person receiver2 = new Person(new MatriculationNumber("A0277101P"), new Name("Bob"), new Phone("9876543210"),
                new Email("bob@example.com"), new Address("321 Avenue"), new HashSet<>());
        Comment comment2 = new Comment(commenter2, receiver2, "Great job!");
        assertEquals(comment1, comment2);
    }

    @Test
    void testNotEquals() {
        Person commenter = new Person(new MatriculationNumber("A0277100P"), new Name("Alice"), new Phone("1234567890"),
                new Email("alice@example.com"), new Address("123 Street"), new HashSet<>());
        Person receiver = new Person(new MatriculationNumber("A0277101P"), new Name("Bob"), new Phone("9876543210"),
                new Email("bob@example.com"), new Address("321 Avenue"), new HashSet<>());
        Comment comment1 = new Comment(commenter, receiver, "Great job!");
        Person commenter2 = new Person(new MatriculationNumber("A0377101P"),
                new Name("Charlie"), new Phone("1112223334"),
                new Email("charlie@example.com"), new Address("456 Lane"), new HashSet<>());
        Person receiver2 = new Person(new MatriculationNumber("A0477101P"), new Name("Dave"), new Phone("4445556667"),
                new Email("dave@example.com"), new Address("789 Boulevard"), new HashSet<>());
        Comment comment2 = new Comment(commenter2, receiver2, "Nice work!");
        assertNotEquals(comment1, comment2);
    }
}
