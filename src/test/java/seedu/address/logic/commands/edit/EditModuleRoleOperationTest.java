package seedu.address.logic.commands.edit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.edit.EditModuleRoleOperation.isValidModuleRoleOperation;

import org.junit.jupiter.api.Test;

public class EditModuleRoleOperationTest {
    @Test
    public void isValidModuleRoleOperation_validInput_success() {
        assertTrue(isValidModuleRoleOperation("+cs1231s"));
        assertTrue(isValidModuleRoleOperation("-GESS1025"));
        assertTrue(isValidModuleRoleOperation("+MA1522 cs1101s"));
        assertTrue(isValidModuleRoleOperation("+   ma1521-PROf   cs2109s-StUdeNt"));
    }

    @Test
    public void isValidModuleRoleOperation_validInput_failure() {
        assertFalse(isValidModuleRoleOperation("+-cs1231s"));

        assertFalse(isValidModuleRoleOperation("+cs1101s-ma1522"));
        assertFalse(isValidModuleRoleOperation("+12345"));

        assertFalse(isValidModuleRoleOperation("cs1231s"));
        assertFalse(isValidModuleRoleOperation(" "));
        assertFalse(isValidModuleRoleOperation("=cs1231s"));
        assertFalse(isValidModuleRoleOperation(" +cs1231s"));
        assertFalse(isValidModuleRoleOperation("+cs1101s +ma1522 -ma1521"));
        assertFalse(isValidModuleRoleOperation("+cs1101sma1521"));
    }
}
