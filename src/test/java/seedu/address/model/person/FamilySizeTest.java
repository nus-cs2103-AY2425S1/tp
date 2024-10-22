package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import java.util.*;

public class FamilySizeTest {

    private static int INVALID_FAMILY_SIZE = -1;

    private static int VALID_FAMILY_SIZE_ZERO = 0;

    private static int VALID_FAMILY_SIZE = 3;

    @Test
    public void constructor_invalidFamilySize_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new FamilySize(INVALID_FAMILY_SIZE));
    }

    @Test
    public void constructor_zeroFamilySize_shouldSucceed() {
        assertDoesNotThrow(() -> new FamilySize(VALID_FAMILY_SIZE_ZERO));
    }

    @Test
    public void constructor_validFamilySize_shouldSucceed() {
        assertDoesNotThrow(() -> new FamilySize(VALID_FAMILY_SIZE));
    }

    @Test
    public void isValidFamilySize_validFamilySize_returnsTrue() {
        assertTrue(FamilySize.isValidFamilySize(VALID_FAMILY_SIZE));
    }

    @Test
    public void isValidFamilySize_zeroFamilySize_returnsTrue() {
        assertTrue(FamilySize.isValidFamilySize(VALID_FAMILY_SIZE_ZERO));
    }

    @Test
    public void isValidFamilySize_invalidFamilySize_returnsFalse() {
        assertFalse(FamilySize.isValidFamilySize(INVALID_FAMILY_SIZE));
    }

    @Test
    public void equals() {
        assertEquals(new FamilySize(VALID_FAMILY_SIZE_ZERO), new FamilySize(VALID_FAMILY_SIZE_ZERO));
        assertNotEquals(new FamilySize(VALID_FAMILY_SIZE), new FamilySize(VALID_FAMILY_SIZE_ZERO));
        assertNotEquals(new FamilySize(VALID_FAMILY_SIZE), null);

    }
}
