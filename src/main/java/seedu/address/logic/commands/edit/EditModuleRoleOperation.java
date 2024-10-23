package seedu.address.logic.commands.edit;

import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.ModuleRoleMap;

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
}
