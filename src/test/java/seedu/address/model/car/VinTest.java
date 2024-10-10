package seedu.address.model.car;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class VinTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Vin(null));
    }

    @Test
    public void constructor_invalidVin_throwsIllegalArgumentException() {
        String invalidVin = "";
        assertThrows(IllegalArgumentException.class, () -> new Vin(invalidVin));
    }

    @Test
    public void isValidVin() {
        // null vin
        assertThrows(NullPointerException.class, () -> Vin.isValidVin(null));

        // blank vin
        assertFalse(Vin.isValidVin("")); // empty string
        assertFalse(Vin.isValidVin(" ")); // spaces only

        // incorrect number of characters
        assertFalse(Vin.isValidVin("ABCED12345")); // 10
        assertFalse(Vin.isValidVin("ABCDE12345ABCDE")); // 15
        assertFalse(Vin.isValidVin("ABCDE12345ABCDE1")); //16
        assertFalse(Vin.isValidVin("ABCDE12345ABCDE123")); //18
        assertFalse(Vin.isValidVin("ABCDE12345ABCDE12345")); // 20

        // valid Vin
        assertTrue(Vin.isValidVin("KMHGH4JH3EU073801")); // Sample Hyundai Vin
        assertTrue(Vin.isValidVin("WBAAA1305H8251545")); // Sample BMW Vin
        assertTrue(Vin.isValidVin("ABCDE12345ABCDE12")); // mixed
        assertTrue(Vin.isValidVin("AB128BH14HIJ928HS")); // well mixed
        assertTrue(Vin.isValidVin("BBSSHUUEHAI123456")); // chars to number
        assertTrue(Vin.isValidVin("146576837BSDDHIHH")); // number to chars

        // invalid Vin (fails checksum)
        assertFalse(Vrn.isValidVrn("ABCDE12345ABCDE1*")); // invalid characters
        assertFalse(Vrn.isValidVrn("ABCDE12345 ABCDE1")); // invalid spacing

    }

    @Test
    public void equals() {
        Vin vin = new Vin("KMHGH4JH3EU073801");

        // same values -> returns true
        assertTrue(vin.equals(new Vin("KMHGH4JH3EU073801")));

        // same object -> returns true
        assertTrue(vin.equals(vin));

        // null -> returns false
        assertFalse(vin.equals(null));

        // different types -> returns false
        assertFalse(vin.equals(5.0f));

        // different values -> returns false
        assertFalse(vin.equals(new Vin("WBAAA1305H8251545")));
    }
}
