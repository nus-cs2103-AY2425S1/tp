package seedu.address.logic.commands.edit;

import java.util.HashMap;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.ModuleRoleMap;
import seedu.address.model.person.RoleType;

/**
 * Represents an operation to add a person's module roles.
 */
public class AddModuleRoleOperation extends EditModuleRoleOperation {

    private final ModuleRoleMap moduleRoleMapToAdd;
    private final Logger logger = LogsCenter.getLogger(getClass());
    private static final String MESSAGE_MODULE_ROLE_PAIRS_CLASH = "You wish to add these module role pair(s) "
            + "but they clash with existing ones: %1$s";

    /**
     * Constructor for AddModuleRoleOperation.
     * @param moduleRoleMapToAdd ModuleRoleMap to add.
     */
    public AddModuleRoleOperation(ModuleRoleMap moduleRoleMapToAdd) {
        this.moduleRoleMapToAdd = moduleRoleMapToAdd;
    }

    /**
     * Creates a new {@code ModuleRoleMap} from {@code moduleRoleMapToEdit} with the module role pairs added.
     * @param moduleRoleMapToEdit ModuleRoleMap to edit.
     * @return A new ModuleRoleMap with module role pairs added.
     */
    @Override
    protected ModuleRoleMap execute(ModuleRoleMap moduleRoleMapToEdit) throws CommandException {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>(moduleRoleMapToEdit.getRoles());
        ModuleRoleMap result = new ModuleRoleMap(roles);
        ModuleRoleMap failed = result.putAll(moduleRoleMapToAdd);
        if (!failed.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_MODULE_ROLE_PAIRS_CLASH,
                    failed.getData(true)));
        }
        logger.info("Added module roles: " + moduleRoleMapToAdd.getData()
                + "to: " + moduleRoleMapToEdit.getData());
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AddModuleRoleOperation otherAddModuleRoleOperation)) {
            return false;
        }
        return moduleRoleMapToAdd.equals(otherAddModuleRoleOperation.moduleRoleMapToAdd);
    }

    @Override
    public String toString() {
        return "+ " + moduleRoleMapToAdd.toString();
    }
}
