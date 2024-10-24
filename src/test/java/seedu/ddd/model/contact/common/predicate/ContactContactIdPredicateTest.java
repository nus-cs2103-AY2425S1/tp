package seedu.ddd.model.contact.common.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.testutil.ClientBuilder;

public class ContactContactIdPredicateTest {

    @Test
    public void equals() {
        ContactId firstContactId = new ContactId(1);
        ContactId secondContactId = new ContactId(2);

        ContactIdPredicate firstPredicate = new ContactIdPredicate(firstContactId);
        ContactIdPredicate secondPredicate = new ContactIdPredicate(secondContactId);

        // same object -> return true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContactIdPredicate firstPredicateCopy = new ContactIdPredicate(firstContactId);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different id -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_contactContainsId_returnsTrue() {
        ContactIdPredicate predicate = new ContactIdPredicate(new ContactId(1));
        assertTrue(predicate.test(new ClientBuilder().withId(1).build()));

        predicate = new ContactIdPredicate(new ContactId(4));
        assertTrue(predicate.test(new ClientBuilder().withId(4).build()));

        predicate = new ContactIdPredicate(new ContactId(100));
        assertTrue(predicate.test(new ClientBuilder().withId(100).build()));

        predicate = new ContactIdPredicate(new ContactId(1000));
        assertTrue(predicate.test(new ClientBuilder().withId(1000).build()));
    }

    @Test
    public void test_contactDoesNotContainsId_returnsFalse() {
        ContactIdPredicate predicate = new ContactIdPredicate(new ContactId(1));
        assertFalse(predicate.test(new ClientBuilder().withId(2).build()));

        predicate = new ContactIdPredicate(new ContactId(3));
        assertFalse(predicate.test(new ClientBuilder().withId(2).build()));
    }

    @Test
    public void toStringMethod() {
        ContactId contactId = new ContactId(1);
        ContactIdPredicate predicate = new ContactIdPredicate(contactId);

        String expected = ContactIdPredicate.class.getCanonicalName() + "{id=" + contactId + "}";
        assertEquals(expected, predicate.toString());
    }
}
