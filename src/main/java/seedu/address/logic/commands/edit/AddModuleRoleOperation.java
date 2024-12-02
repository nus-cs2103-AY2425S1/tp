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
 * Represents an operation to add a person's module roles.
 */
public class AddModuleRoleOperation extends EditModuleRoleOperation {

    private static final String MESSAGE_MODULE_ROLE_PAIRS_CLASH = "You wish to add these module role pair(s) "
            + "but they clash with existing ones: %1$s";
    private final AddModuleRoleDescriptor descriptor;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Constructor for AddModuleRoleOperation.
     * @param descriptor Descriptor containing the module role pairs to add.
     */
    public AddModuleRoleOperation(AddModuleRoleDescriptor descriptor) {
        this.descriptor = descriptor;
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
        ModuleRoleMap failed = result.putAll(descriptor.getToAdds());
        if (!failed.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_MODULE_ROLE_PAIRS_CLASH,
                    failed.getData(true)));
        }
        logger.info("Added module roles: " + descriptor
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
        return descriptor.equals(otherAddModuleRoleOperation.descriptor);
    }

    @Override
    public String toString() {
        return "+" + descriptor;
    }

    /**
     * Descriptor for AddModuleRoleOperation.
     */
    public static class AddModuleRoleDescriptor {
        /**
         * List of module role pairs to add.
         */
        private List<ModuleRolePair> toAdds;

        /**
         * Constructor for AddModuleRoleDescriptor.
         */
        public AddModuleRoleDescriptor(List<ModuleRolePair> toAdds) {
            this.toAdds = toAdds == null ? new ArrayList<>() : new ArrayList<>(toAdds);
        }

        /**
         * Copy constructor for AddModuleRoleDescriptor.
         */
        public AddModuleRoleDescriptor(AddModuleRoleDescriptor toCopy) {
            this(toCopy.toAdds);
        }

        public List<ModuleRolePair> getToAdds() {
            return new ArrayList<>(toAdds);
        }

        public void setToAdds(List<ModuleRolePair> toAdds) {
            this.toAdds = new ArrayList<>(toAdds);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AddModuleRoleDescriptor otherAddModuleRoleDescriptor)) {
                return false;
            }
            return toAdds.equals(otherAddModuleRoleDescriptor.toAdds);
        }

        @Override
        public String toString() {
            // note that the addition order matters, so we can't sort the list
            return this.toAdds.toString();
        }
    }
}
