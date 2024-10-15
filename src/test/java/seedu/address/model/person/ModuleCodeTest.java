package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModuleCodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleCode(null));
    }

    @Test
    public void constructor_emptyString_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ModuleCode("");
        });
    }

    @Test
    public void constructor_moduleCodeWithSpace_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ModuleCode("CS 1101S");
        });
    }

    @Test
    public void constructor_moduleCodeWithWrongOrder_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ModuleCode("1101CS");
        });
    }

    @Test
    public void constructor_validModuleCodeWithNoLetterBehind() {
        assertDoesNotThrow(() -> {
            new ModuleCode("MA1522");
        });
    }

    @Test
    public void constructor_validModuleCodeWithLetterBehind() {
        assertDoesNotThrow(() -> {
            new ModuleCode("CS1231S");
        });
    }

    @Test
    public void constructor_validModuleCodeWithLettersBehind() {
        assertDoesNotThrow(() -> {
            new ModuleCode("CFG2002KH");
        });
    }

    @Test
    public void constructor_validModuleCodeWithLowerCaseLetter() {
        assertDoesNotThrow(() -> {
            new ModuleCode("ma1522");
            new ModuleCode("cs1231s");
        });
    }

    @Test
    public void equals() {
        ModuleCode name = new ModuleCode("CS1101S");

        // same values -> returns true
        assertTrue(name.equals(new ModuleCode("CS1101S")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new ModuleCode("MA1521")));
    }
}
