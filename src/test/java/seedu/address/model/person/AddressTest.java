package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        // invalid addresses
        assertFalse(Address.isValidAddress("")); // empty string
        assertFalse(Address.isValidAddress(" ")); // spaces only
        assertFalse(Address.isValidAddress("-"));
        assertFalse(Address.isValidAddress(", Den Road, #01-355"));


        // valid addresses
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Address.isValidAddress("#01-355 Blk 456, Den Road"));
        assertTrue(Address.isValidAddress("91 Pemimpin Pl"));
        assertTrue(Address.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA #12-345; USA")); // long address
    }

    @Test
    public void equals() {
        Address address = new Address("Valid Address #12-345");

        // same values -> returns true
        assertTrue(address.equals(new Address("Valid Address #12-345")));

        // same object -> returns true
        assertTrue(address.equals(address));

        // null -> returns false
        assertFalse(address.equals(null));

        // different types -> returns false
        assertFalse(address.equals(5.0f));

        // different values -> returns false
        assertFalse(address.equals(new Address("Other Valid Address #12-345")));
    }
    @Test
    public void hashCode_sameAddress_returnsSameHashCode() {
        Address address1 = new Address("123 Main St");
        Address address2 = new Address("123 Main St");

        assertTrue(address1.hashCode() == address2.hashCode());
    }

    @Test
    public void hashCode_differentAddress_returnsDifferentHashCode() {
        Address address1 = new Address("123 Main St");
        Address address2 = new Address("456 Main St");

        assertFalse(address1.hashCode() == address2.hashCode());
    }

    @Test
    public void hashCode_differentValues_returnsDifferentHashCode() {
        Address address1 = new Address("#10-34 Main St");
        Address address2 = new Address("#11-34 Main St");

        assertFalse(address1.hashCode() == address2.hashCode());
    }

    @Test
    public void hashCode_sameObject_returnsSameHashCode() {
        Address address = new Address("123 Main St");
        assertTrue(address.hashCode() == address.hashCode());
    }
}
