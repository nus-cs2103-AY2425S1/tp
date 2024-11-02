package seedu.address.logic.commands.edit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.ModuleRoleMap;
import seedu.address.model.person.ModuleRolePair;
import seedu.address.model.person.RoleType;

/**
 * Represents an operation to delete a person's module roles.
 */
public class DeleteModuleRoleOperation extends EditModuleRoleOperation {

    private static final String MESSAGE_NONEXISTENT_MODULE_ROLE_PAIRS = "You wish to delete these module role pair(s) "
            + "but they do not exist: ";

    private static final String MESSAGE_NONEXISTENT_MODULE_CODES = "You wish to delete these module code(s) "
            + "(ignoring role) but they do not exist: ";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private final DeleteModuleRoleDescriptor descriptor;

    /**
     * Constructor for DeleteModuleRoleOperation.
     *
     * @param descriptor Descriptor containing the module role pairs to delete.
     */
    public DeleteModuleRoleOperation(DeleteModuleRoleDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    /**
     * Creates a new {@code ModuleRoleMap} from {@code moduleRoleMapToEdit} with the module role pairs deleted.
     *
     * @param moduleRoleMapToEdit ModuleRoleMap to delete.
     * @return A new ModuleRoleMap with module role pairs deleted.
     */
    @Override
    protected ModuleRoleMap execute(ModuleRoleMap moduleRoleMapToEdit) throws CommandException {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>(moduleRoleMapToEdit.getRoles());
        ModuleRoleMap result = new ModuleRoleMap(roles);
        List<ModuleRolePair> failedModuleRoles = result.removeAll(descriptor.getToDeletes());
        List<ModuleCode> failedModuleCodes = result.removeAllIgnoringRoles(descriptor.getToDeleteAnyRoles());

        List<String> exceptionMessages = new ArrayList<>();
        if (!failedModuleRoles.isEmpty()) {
            exceptionMessages.add(MESSAGE_NONEXISTENT_MODULE_ROLE_PAIRS + failedModuleRoles);
        }
        if (!failedModuleCodes.isEmpty()) {
            exceptionMessages.add(MESSAGE_NONEXISTENT_MODULE_CODES + failedModuleCodes);
        }
        if (!exceptionMessages.isEmpty()) {
            throw new CommandException(String.join("\n", exceptionMessages));
        }

        logger.info("Deleted module roles with descriptor: " + descriptor);
        logger.info("Resulting module roles: " + result);
        logger.info("Failed to delete module roles: " + failedModuleRoles);
        logger.info("Failed to delete module codes (ignoring roles): " + failedModuleCodes);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeleteModuleRoleOperation otherDeleteModuleRoleOperation)) {
            return false;
        }
        return descriptor.equals(otherDeleteModuleRoleOperation.descriptor);
    }

    @Override
    public String toString() {
        return "-" + descriptor;
    }

    /**
     * Descriptor for DeleteModuleRoleOperation.
     */
    public static class DeleteModuleRoleDescriptor {
        /**
         * List of module role pairs to delete.
         */
        private List<ModuleRolePair> toDeletes;
        /**
         * List of module codes to delete, regardless of role.
         */
        private List<ModuleCode> toDeleteAnyRoles;

        /**
         * Constructor for DeleteModuleRoleDescriptor.
         */
        public DeleteModuleRoleDescriptor(List<ModuleRolePair> toDeletes, List<ModuleCode> toDeleteAnyRoles) {
            this.toDeletes = toDeletes == null ? new ArrayList<>() : new ArrayList<>(toDeletes);
            this.toDeleteAnyRoles = toDeleteAnyRoles == null ? new ArrayList<>() : new ArrayList<>(toDeleteAnyRoles);
        }

        /**
         * Copy constructor for DeleteModuleRoleDescriptor.
         */
        public DeleteModuleRoleDescriptor(DeleteModuleRoleDescriptor toCopy) {
            this(toCopy.toDeletes, toCopy.toDeleteAnyRoles);
        }

        public List<ModuleRolePair> getToDeletes() {
            return new ArrayList<>(toDeletes);
        }

        public void setToDeletes(List<ModuleRolePair> toDeletes) {
            this.toDeletes = new ArrayList<>(toDeletes);
        }

        public List<ModuleCode> getToDeleteAnyRoles() {
            return new ArrayList<>(toDeleteAnyRoles);
        }

        public void setToDeleteAnyRoles(List<ModuleCode> toDeleteAnyRoles) {
            this.toDeleteAnyRoles = new ArrayList<>(toDeleteAnyRoles);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof DeleteModuleRoleDescriptor otherDeleteModuleRoleDescriptor)) {
                return false;
            }
            return toDeletes.equals(otherDeleteModuleRoleDescriptor.toDeletes)
                    && toDeleteAnyRoles.equals(otherDeleteModuleRoleDescriptor.toDeleteAnyRoles);
        }

        @Override
        public String toString() {
            // note that deletion order should be preserved, so we can't sort the list
            return this.toDeletes + " " + this.toDeleteAnyRoles;
        }
    }
}
