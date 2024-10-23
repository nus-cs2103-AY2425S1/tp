package seedu.ddd.model.contact.common.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.TypicalContacts.ALICE;
import static seedu.ddd.testutil.TypicalContacts.BENSON;

import org.junit.jupiter.api.Test;

import seedu.ddd.model.contact.common.Email;

public class ContactEmailPredicateTest {

    @Test
    public void equals() {
        Email emailOne = new Email("heinz@example.com");
        Email emailTwo = new Email("martin@example.com");
        ContactEmailPredicate predicateOne = new ContactEmailPredicate(emailOne);
        ContactEmailPredicate predicateTwo = new ContactEmailPredicate(emailTwo);

        // same object -> returns true
        assertTrue(predicateOne.equals(predicateOne));

        // same values -> returns true
        ContactEmailPredicate firstPredicateCopy =
                new ContactEmailPredicate(emailOne);
        assertTrue(predicateOne.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(predicateOne.equals(1));

        // null -> returns false
        assertFalse(predicateOne.equals(null));

        // different name list -> returns false
        assertFalse(predicateOne.equals(predicateTwo));
    }

    @Test
    public void test_emailMatchesEmail_returnsTrue() {
        Email emailOne = new Email("alice@example.com");
        ContactEmailPredicate predicate = new ContactEmailPredicate(emailOne);

        assertTrue(predicate.test(ALICE));

        emailOne = new Email("johnd@example.com");
        predicate = new ContactEmailPredicate(emailOne);

        assertTrue(predicate.test(BENSON));
    }

    @Test
    public void test_emailDoesNotMatchEmail_returnsFalse() {
        Email emailOne = new Email("alice@example.com");
        ContactEmailPredicate predicate = new ContactEmailPredicate(emailOne);

        assertFalse(predicate.test(BENSON));

        emailOne = new Email("johnd@example.com");
        predicate = new ContactEmailPredicate(emailOne);

        assertFalse(predicate.test(ALICE));
    }

    @Test
    public void toStringMethod() {
        Email emailOne = ALICE.getEmail();
        ContactEmailPredicate predicate = new ContactEmailPredicate(emailOne);

        String expected = ContactEmailPredicate.class.getCanonicalName() + "{email=" + emailOne + "}";
        assertEquals(expected, predicate.toString());
    }
}
