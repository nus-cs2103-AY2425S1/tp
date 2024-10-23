package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODNAME_AMY;

import org.junit.jupiter.api.Test;

public class ModuleNameTest {

    @Test
    public void toString_invalidModuleName_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new ModuleName(INVALID_MODNAME_DESC));
    }

    @Test
    public void toString_validModuleName_returnsCorrectString() {
        assertEquals(VALID_MODNAME_AMY, new ModuleName(VALID_MODNAME_AMY).toString());
    }

    @Test
    public void equals() {
        ModuleName moduleName = new ModuleName("CS1231");

        // same object -> returns true
        assertTrue(moduleName.equals(moduleName));
        // same values -> returns true
        ModuleName moduleNameCopy = new ModuleName(moduleName.getModuleName());
        assertTrue(moduleName.equals(moduleNameCopy));
        // different types -> returns false
        assertFalse(moduleName.equals(1));
        // null -> returns false
        assertFalse(moduleName.equals(null));
        // different ModuleName -> returns false
        ModuleName differentModuleName = new ModuleName("CS1231S");
        assertFalse(moduleName.equals(differentModuleName));
    }

}
