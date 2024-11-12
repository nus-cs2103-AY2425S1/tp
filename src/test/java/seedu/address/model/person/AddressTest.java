package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertFalse(Address.isValidAddress("24 Johnny Cena Street Earth Milky Way Galaxy; "
                + "Emperor's Angels Ultramarine Chapter, "
                + "172 Avenue Bolter Tower, Cadia Stands")); // 121 character long > 120 allowed
        assertFalse(Address.isValidAddress("   Johnny Cena Street Earth Milky Way Galaxy; "
                + "Emperor's Angels Ultramarine Chapter, "
                + "172 Avenue Bolter Tower, Cadia       ")); // 121 character long, with leading + trailing whitespaces

        // valid addresses
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Address.isValidAddress("-")); // one character
        assertTrue(Address.isValidAddress("The Pinnacle ˏˋ°•*⁀➷")); // special characters
        assertTrue(Address.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
        assertTrue(Address.isValidAddress("24 Johnny Cena Street Earth Milky Way Galaxy; Emperor's Angels"
                + " Ultramarine Chapter, 172 Avenue Bolter Tower, Cadia Stand")); // 120 characters
    }

    @Test
    public void equals() {
        Address address = new Address("Valid Address");

        // same values -> returns true
        assertTrue(address.equals(new Address("Valid Address")));

        // same object -> returns true
        assertTrue(address.equals(address));

        // null -> returns false
        assertFalse(address.equals(null));

        // different types -> returns false
        assertFalse(address.equals(5.0f));

        // different values -> returns false
        assertFalse(address.equals(new Address("Other Valid Address")));
    }

    @Test
    public void toStringMethod() {
        String address = "King's Village, Magic Land";

        assertEquals(new Address(address).toString(), address);
    }
}
