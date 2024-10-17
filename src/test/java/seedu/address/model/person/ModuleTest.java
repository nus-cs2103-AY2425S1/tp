package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModuleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Module(null));
    }

    @Test
    public void constructor_invalidModuleName_throwsIllegalArgumentException() {
        String invalidModuleName = "";
        assertThrows(IllegalArgumentException.class, () -> new Module(invalidModuleName));
    }

    @Test
    public void isValidModule() {
        // null module name
        assertThrows(NullPointerException.class, () -> Module.isValidModule(null));

        // invalid module names
        assertFalse(Module.isValidModule(""));
        assertFalse(Module.isValidModule(" "));
        assertFalse(Module.isValidModule("CS2103T@"));

        // valid module names
        assertTrue(Module.isValidModule("CS2103T"));
        assertTrue(Module.isValidModule("MA2103"));
        assertTrue(Module.isValidModule("CS12345"));
    }

    @Test
    public void equals() {
        Module module = new Module("CS2103T");

        // same values -> returns true
        assertTrue(module.equals(new Module("CS2103T")));

        // same object -> returns true
        assertTrue(module.equals(module));

        // null -> returns false
        assertFalse(module.equals(null));

        // different types -> returns false
        assertFalse(module.equals(5.0f));

        // different values -> returns false
        assertFalse(module.equals(new Module("CS2100")));
    }

    @Test
    public void hashCode_equalsSameModule_returnsSameHashCode() {
        Module module = new Module("CS2103T");
        Module sameModule = new Module("CS2103T");
        assertTrue(module.hashCode() == sameModule.hashCode());
    }

    @Test
    public void toString_returnsCorrectString() {
        Module module = new Module("CS2103T");
        assertEquals("[CS2103T]", module.toString());
    }
}
