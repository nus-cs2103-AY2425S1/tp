package seedu.address.model.listing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AddressTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new seedu.address.model.listing.Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new seedu.address.model.listing.Address(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> seedu.address.model.listing.Address.isValidAddress(null));

        // invalid addresses
        assertFalse(seedu.address.model.listing.Address.isValidAddress("")); // empty string
        assertFalse(seedu.address.model.listing.Address.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(seedu.address.model.listing.Address.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(seedu.address.model.listing.Address.isValidAddress("-")); // one character
        assertTrue(seedu.address.model.listing.Address
                .isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }

    @Test
    public void equals() {
        seedu.address.model.listing.Address address = new seedu.address.model.listing.Address("Valid Address");

        // same values -> returns true
        assertTrue(address.equals(new seedu.address.model.listing.Address("Valid Address")));

        // same object -> returns true
        assertTrue(address.equals(address));

        // null -> returns false
        assertFalse(address.equals(null));

        // different types -> returns false
        assertFalse(address.equals(5.0f));

        // different values -> returns false
        assertFalse(address.equals(new Address("Other Valid Address")));
    }
}
