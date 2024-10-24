package seedu.address.logic.commands.edit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.edit.EditModuleRoleOperation.isValidModuleRoleOperation;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.ModuleRoleMap;
import seedu.address.model.person.ModuleRolePair;
import seedu.address.model.person.RoleType;

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

    @Test
    public void getModuleCodeChangeDescription_noChange_success() {
        ModuleRoleMap moduleRoleMapBefore = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS2103T"), new ModuleCode("CS2101")},
                new RoleType[]{RoleType.STUDENT, RoleType.TUTOR}
        );
        String actualDescription = EditModuleRoleOperation.getModuleCodeChangeDescription(
                moduleRoleMapBefore, moduleRoleMapBefore
        );
        String expectedDescription = "";
        assertEquals(actualDescription, expectedDescription);
    }

    @Test
    public void getModuleCodeChangeDescription_addedOnly_success() {
        ModuleRoleMap moduleRoleMapBefore = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS2103T"), new ModuleCode("CS2101")},
                new RoleType[]{RoleType.STUDENT, RoleType.TUTOR}
        );
        ModuleRoleMap moduleRoleMapAfter = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS2103T"), new ModuleCode("CS2101"), new ModuleCode("CS2102")},
                new RoleType[]{RoleType.STUDENT, RoleType.TUTOR, RoleType.PROFESSOR}
        );
        String actualDescription = EditModuleRoleOperation.getModuleCodeChangeDescription(
                moduleRoleMapBefore, moduleRoleMapAfter
        );
        String expectedDescription = "Module role(s) added: "
                + new ModuleRolePair(new ModuleCode("CS2102"), RoleType.PROFESSOR);
        assertEquals(actualDescription, expectedDescription);
    }

    @Test
    public void getModuleCodeChangeDescription_deletedOnly_success() {
        ModuleRoleMap moduleRoleMapBefore = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS2103T"), new ModuleCode("CS2101")},
                new RoleType[]{RoleType.STUDENT, RoleType.TUTOR}
        );
        ModuleRoleMap moduleRoleMapAfter = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS2103T")},
                new RoleType[]{RoleType.STUDENT}
        );
        String actualDescription = EditModuleRoleOperation.getModuleCodeChangeDescription(
                moduleRoleMapBefore, moduleRoleMapAfter
        );
        String expectedDescription = "Module role(s) deleted: "
                + new ModuleRolePair(new ModuleCode("CS2101"), RoleType.TUTOR);
        assertEquals(actualDescription, expectedDescription);
    }

    @Test
    public void getModuleCodeChangeDescription_addedAndDeleted_success() {
        ModuleRoleMap moduleRoleMapBefore = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS2103T"), new ModuleCode("CS2101")},
                new RoleType[]{RoleType.STUDENT, RoleType.TUTOR}
        );
        ModuleRoleMap moduleRoleMapAfter = new ModuleRoleMap(
                new ModuleCode[]{new ModuleCode("CS2103T"), new ModuleCode("CS2102")},
                new RoleType[]{RoleType.STUDENT, RoleType.PROFESSOR}
        );
        String actualDescription = EditModuleRoleOperation.getModuleCodeChangeDescription(
                moduleRoleMapBefore, moduleRoleMapAfter
        );
        String expectedDescription = "Module role(s) added: "
                + new ModuleRolePair(new ModuleCode("CS2102"), RoleType.PROFESSOR)
                + "\nModule role(s) deleted: "
                + new ModuleRolePair(new ModuleCode("CS2101"), RoleType.TUTOR);
        assertEquals(actualDescription, expectedDescription);
    }
}
