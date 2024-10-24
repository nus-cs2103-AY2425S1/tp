package seedu.address.logic.commands.edit;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.ModuleRoleMap;
import seedu.address.model.person.ModuleRolePair;

/**
 * Represents an operation to edit a person's module roles.
 */
public abstract class EditModuleRoleOperation {
    public static final String VALIDATION_REGEX_ROLE = "(?:-student|-tutor|-ta|-professor|-prof)?";
    public static final String VALIDATION_REGEX = "[\\+-] *" + ModuleCode.VALIDATION_REGEX + VALIDATION_REGEX_ROLE
            + "(?: +" + ModuleCode.VALIDATION_REGEX + VALIDATION_REGEX_ROLE + ")*";
    public static final String MESSAGE_VALID_OPERATION_CONSTRAINT = """
            Module role operation follows this format:
            +(MODULECODE[-ROLETYPE])+ for adding new module role(s)
            or -(MODULEROLE[-ROLETYPE])+ for deleting existing module role(s)
            e.g. +CS1101S MA1521-TA
            adds CS1101S-Student and MA1521-Tutor to the person
            """;

    /**
     * Executes the operation on the given module role map.
     * @param moduleRoleMapToEdit The module role map to edit.
     * @return The module role map after the operation.
     */
    protected abstract ModuleRoleMap execute(ModuleRoleMap moduleRoleMapToEdit);

    /**
     * Checks if the given operation is a valid module role operation.
     * @param operation The operation to check.
     * @return True if the operation is valid, false otherwise.
     */
    public static boolean isValidModuleRoleOperation(String operation) {
        return operation.toLowerCase().matches(VALIDATION_REGEX);
    }

    /**
     * Returns a description of the change in module roles.
     * Example:
     * Change(s) made:
     * Name: Alex -> Bob
     * Phone: 99889988 -> 10102222
     * Module role(s) added: MA1521-Tutor
     *
     * @param moduleRoleMapBefore The module role map before the change.
     * @param moduleRoleMapAfter The module role map after the change.
     * @return A description of the change in module roles.
     */
    public static String getModuleCodeChangeDescription(
            ModuleRoleMap moduleRoleMapBefore, ModuleRoleMap moduleRoleMapAfter) {

        StringBuilder stringBuilderAdded = new StringBuilder();
        boolean hasAdded = false;
        for (ModuleRolePair moduleRolePair : moduleRoleMapAfter.getData()) {
            if (!moduleRoleMapBefore.containsModuleRolePair(moduleRolePair)) {
                hasAdded = true;
                stringBuilderAdded.append(moduleRolePair).append(" ");
            }
        }

        StringBuilder stringBuilderDeleted = new StringBuilder();
        boolean hasDeleted = false;
        for (ModuleRolePair moduleRolePair : moduleRoleMapBefore.getData()) {
            if (!moduleRoleMapAfter.containsModuleRolePair(moduleRolePair)) {
                hasDeleted = true;
                stringBuilderDeleted.append(moduleRolePair).append(" ");
            }
        }
        List<String> finalDescription = new ArrayList<>();
        if (hasAdded) {
            finalDescription.add("Module role(s) added: " + stringBuilderAdded.toString().strip());
        }
        if (hasDeleted) {
            finalDescription.add("Module role(s) deleted: " + stringBuilderDeleted.toString().strip());
        }

        return String.join("\n", finalDescription);
    }
}
