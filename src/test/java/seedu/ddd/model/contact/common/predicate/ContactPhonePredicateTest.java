package seedu.ddd.model.contact.common.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.testutil.ClientBuilder;

public class ContactPhonePredicateTest {

    @Test
    public void equals() {
        Phone phoneNumber1 = new Phone("81234567");
        Phone phoneNumber2 = new Phone("93215412");

        ContactPhonePredicate firstPredicate = new ContactPhonePredicate(phoneNumber1);
        ContactPhonePredicate secondPredicate = new ContactPhonePredicate(phoneNumber2);

        // same object -> return true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContactPhonePredicate firstPredicateCopy = new ContactPhonePredicate(phoneNumber1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different id -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_contactContainsPhone_returnsTrue() {
        Phone phoneNumber = new Phone("81234567");
        ContactPhonePredicate predicate = new ContactPhonePredicate(phoneNumber);
        assertTrue(predicate.test(new ClientBuilder().withPhone("81234567").build()));

        predicate = new ContactPhonePredicate(new Phone("99999999"));
        assertTrue(predicate.test(new ClientBuilder().withPhone("99999999").build()));

        predicate = new ContactPhonePredicate(new Phone("12345"));
        assertTrue(predicate.test(new ClientBuilder().withPhone("12345").build()));

        // contains the number
        predicate = new ContactPhonePredicate(new Phone("123"));
        assertTrue(predicate.test(new ClientBuilder().withPhone("12345").build()));
    }

    @Test
    public void test_contactDoesNotContainsPhone_returnsFalse() {
        Phone phoneNumber = new Phone("1234567");
        ContactPhonePredicate predicate = new ContactPhonePredicate(phoneNumber);
        assertFalse(predicate.test(new ClientBuilder().withPhone("82345678").build()));
        assertFalse(predicate.test(new ClientBuilder().withPhone("81234654").build()));

        predicate = new ContactPhonePredicate(new Phone("99999999"));
        assertFalse(predicate.test(new ClientBuilder().withPhone("99999998").build()));
    }

    @Test
    public void toStringMethod() {
        Phone phoneNumber = new Phone("81234567");
        ContactPhonePredicate predicate = new ContactPhonePredicate(phoneNumber);

        String expected = ContactPhonePredicate.class.getCanonicalName() + "{phoneNumber=" + phoneNumber + "}";
        assertEquals(expected, predicate.toString());
    }
}
