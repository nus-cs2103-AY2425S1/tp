package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AgeTest {

    @Test
    public void constructor_negativeAge_throwsIllegalArgumentException() {
        int negativeAge = -1;
        assertThrows(IllegalArgumentException.class, () -> new Age(negativeAge));
    }

    @Test
    public void constructor_zeroAge_shouldSucceed() {
        assertDoesNotThrow(() -> new Age(0));
    }

    @Test
    public void isValidAge_null_throwsNullPointerException() {
        String nullString = null;
        assertThrows(NullPointerException.class, () -> Age.isValidAge(nullString));
    }

    @Test
    public void isValidAge_validAge_returnsTrue() {
        int validAge1 = 20;
        int validAge2 = 0;

        assertTrue(Age.isValidAge(validAge1));
        assertTrue(Age.isValidAge(validAge2));
    }

    @Test
    public void isValidAge_negativeAge_returnsFalse() {
        int negativeAge = -1;
        assertFalse(Age.isValidAge(negativeAge));
    }

    @Test
    public void isValidAge_validAgeString_returnsTrue() {
        String validAgeString1 = "20";
        String validAgeString2 = "0";

        assertTrue(Age.isValidAge(validAgeString1));
        assertTrue(Age.isValidAge(validAgeString2));
    }

    @Test
    public void isValidAge_invalidAgeString_returnsFalse() {
        List<String> invalidStrings = List.of("00212", "-2910", "-0");

        for (String invalidString : invalidStrings) {
            assertFalse(Age.isValidAge(invalidString));
        }
    }

    @Test
    public void equals() {
        HashMap<Age, Age> equalAges = new HashMap<>();
        equalAges.put(new Age(20), new Age(20));
        equalAges.put(new Age(0), new Age(0));
        equalAges.put(new Age(100), new Age(100));

        HashMap<Age, Age> differentAges = new HashMap<>();
        differentAges.put(new Age(20), new Age(21));
        differentAges.put(new Age(0), new Age(928));
        differentAges.put(new Age(10), new Age(1));

        for (HashMap.Entry<Age, Age> entry: equalAges.entrySet()) {
            Age age1 = entry.getKey();
            Age age2 = entry.getValue();
            assertEquals(age1, age2);
        }

        for (HashMap.Entry<Age, Age> entry: differentAges.entrySet()) {
            Age age1 = entry.getKey();
            Age age2 = entry.getValue();
            assertNotEquals(age1, age2);
        }
    }
}
