package seedu.address.logic.commands.edit;

import java.util.HashMap;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.ModuleRoleMap;
import seedu.address.model.person.RoleType;

/**
 * Represents an operation to delete a person's module roles.
 */
public class DeleteModuleRoleOperation extends EditModuleRoleOperation {

    private final Logger logger = LogsCenter.getLogger(getClass());
    private final ModuleRoleMap moduleRoleMapToDelete;

    /**
     * Constructor for DeleteModuleRoleOperation.
     * @param moduleRoleMapToDelete ModuleRoleMap to delete.
     */
    public DeleteModuleRoleOperation(ModuleRoleMap moduleRoleMapToDelete) {
        this.moduleRoleMapToDelete = moduleRoleMapToDelete;
    }

    /**
     * Creates a new {@code ModuleRoleMap} from {@code moduleRoleMapToEdit} with the module role pairs deleted.
     * @param moduleRoleMapToEdit ModuleRoleMap to delete.
     * @return A new ModuleRoleMap with module role pairs deleted.
     */
    @Override
    protected ModuleRoleMap execute(ModuleRoleMap moduleRoleMapToEdit) {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>(moduleRoleMapToEdit.getRoles());
        ModuleRoleMap ret = new ModuleRoleMap(roles);
        ret.removeAll(moduleRoleMapToDelete);
        logger.info("Deleted module roles: " + moduleRoleMapToDelete.getData()
                + "from: " + moduleRoleMapToEdit.getData());
        return ret;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeleteModuleRoleOperation otherDeleteModuleRoleOperation)) {
            return false;
        }
        return moduleRoleMapToDelete.equals(otherDeleteModuleRoleOperation.moduleRoleMapToDelete);
    }

    @Override
    public String toString() {
        return "- " + moduleRoleMapToDelete.toString();
    }
}
