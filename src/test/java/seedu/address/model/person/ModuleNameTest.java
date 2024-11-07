package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODNAME_AMY;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.Assert;

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
    public void isValidModuleName() {
        // null module name
        Assert.assertThrows(NullPointerException.class, () -> ModuleName.isValidModName(null));

        // blank module name
        assertFalse(ModuleName.isValidModName("")); // empty string
        assertFalse(ModuleName.isValidModName(" ")); // spaces only

        // invalid module name
        assertFalse(ModuleName.isValidModName("C")); // less than 2 alphabets
        assertFalse(ModuleName.isValidModName("CS")); // missing digits
        assertFalse(ModuleName.isValidModName("2030")); // missing alphabets
        assertFalse(ModuleName.isValidModName("2030S")); // missing alphabets with alphabet at the back
        assertFalse(ModuleName.isValidModName("CS221")); // missing digit
        assertFalse(ModuleName.isValidModName("C2030")); // missing alphabet
        assertFalse(ModuleName.isValidModName("C2030S")); // missing alphabet with alphabet at the back
        assertFalse(ModuleName.isValidModName("C2030-T")); // missing alphabet with hyphen and alphabet at the back
        assertFalse(ModuleName.isValidModName("CS2030CS")); // multiple alphabets at the back
        assertFalse(ModuleName.isValidModName("CS2030C-S")); // hyphen after alphabet
        assertFalse(ModuleName.isValidModName("CS2030-ST")); // 2 alphabets after hyphen

        // valid module name
        assertTrue(ModuleName.isValidModName("CS2030")); // 2 alphabets and 4 digits
        assertTrue(ModuleName.isValidModName("CS2030S")); // 2 alphabets, 4 digits and 1 trailing alphabet
        assertTrue(ModuleName.isValidModName("LSM2251")); // 3 alphabets and 4 digits
        assertTrue(ModuleName.isValidModName("CS2103-T")); // 2 alphabets, 4 digits, a hyphen and trailing alphabet

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
