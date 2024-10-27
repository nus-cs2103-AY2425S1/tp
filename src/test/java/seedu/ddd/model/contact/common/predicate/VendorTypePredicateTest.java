package seedu.ddd.model.contact.common.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.contact.TypicalContacts.ALICE;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_VENDOR;

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
        assertTrue(predicate.test(VALID_VENDOR));
    }

    @Test
    public void test_contactIsNotAVendor_returnsFalse() {
        VendorTypePredicate predicate = new VendorTypePredicate();
        assertFalse(predicate.test(ALICE));
    }

    @Test
    public void toStringMethod() {
        VendorTypePredicate predicate = new VendorTypePredicate();

        String expected = VendorTypePredicate.class.getCanonicalName() + "{}";
        assertEquals(expected, predicate.toString());
    }
}
