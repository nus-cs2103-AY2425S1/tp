package seedu.ddd.model.contact.common.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.TypicalContacts.ALICE;
import static seedu.ddd.testutil.TypicalContacts.BOB;

import org.junit.jupiter.api.Test;

public class VendorTypePredicateTest {

    @Test
    public void equals() {
        VendorTypePredicate firstPredicate = new VendorTypePredicate();
        VendorTypePredicate secondPredicate = new VendorTypePredicate();

        // same object -> return true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same value -> return true
        assertTrue(firstPredicate.equals(secondPredicate));

        // different types -> return false
        assertFalse(firstPredicate.equals(1));

        // null -> return false
        assertFalse(firstPredicate.equals(null));
    }

    @Test
    public void test_contactIsAVendor_returnsTrue() {
        VendorTypePredicate predicate = new VendorTypePredicate();
        assertTrue(predicate.test(BOB));
    }

    @Test
    public void test_contactIsNotAVendor_returnsFalse() {
        VendorTypePredicate predicate = new VendorTypePredicate();
        assertFalse(predicate.test(ALICE));
    }

    @Test
    public void toStringMethod() {
        ClientTypePredicate predicate = new ClientTypePredicate();

        String expected = ClientTypePredicate.class.getCanonicalName() + "{}";
        assertEquals(expected, predicate.toString());
    }
}
