package seedu.address.model.person;

import static java.util.Objects.hash;
import static java.util.Objects.requireNonNull;

/**
 * Represents a key-value pair for ModuleRoleMap.
 * Guarantees: immutable
 */
public class ModuleRolePair {

    public final ModuleCode moduleCode;
    public final RoleType roleType;

    /**
     * Constructs a {@code ModuleRolePair}.
     *
     * @param moduleCode A module code.
     * @param roleType A role type.
     */
    public ModuleRolePair(ModuleCode moduleCode, RoleType roleType) {
        requireNonNull(moduleCode);
        requireNonNull(roleType);
        this.moduleCode = moduleCode;
        this.roleType = roleType;
    }

    @Override
    public String toString() {
        return moduleCode.toString() + "-" + roleType.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleRolePair)) {
            return false;
        }

        ModuleRolePair otherModuleRolePair = (ModuleRolePair) other;
        return moduleCode.equals(otherModuleRolePair.moduleCode)
            && roleType.equals(otherModuleRolePair.roleType);
    }

    @Override
    public int hashCode() {
        return hash(moduleCode, roleType);
    }

}
