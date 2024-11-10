package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

        // valid addresses
        assertTrue(Address.isValidAddress("123 Orchard Road #12-34 ABC Building Singapore 123456"));
        assertTrue(Address.isValidAddress(
                "123 Orchard long address Road #12-34 ABC Building Singapore 123456")); // long address
    }

    @Test
    public void equals() {
        Address address = new Address("123 Orchard Road #12-34 ABC Building Singapore 123456");

        // same values -> returns true
        assertEquals(address, new Address("123 Orchard Road #12-34 ABC Building Singapore 123456"));

        // same object -> returns true
        assertEquals(address, address);

        // null -> returns false
        assertNotEquals(null, address);

        // different types -> returns false
        assertNotEquals(5.0f, address);

        // different values -> returns false
        assertNotEquals(address, new Address("123 Other Road #12-34 ABC Building Singapore 123456"));
    }
}
