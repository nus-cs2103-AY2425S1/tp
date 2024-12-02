package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.areOfSameSize;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents the mapping between module and role type in the roles
 * taken by a Person in NUS.
 */
public class ModuleRoleMap {
    /* Constraints message for role type. */
    public static final String MESSAGE_CONSTRAINTS =
            "Role consists of two parts, module code and role type.\n"
            + "Role type can be blank, and or any of the following values(case insensitive) for each role type:\n"
            + "1. Student: student or leave blank\n"
            + "2. Tutor: tutor or ta\n"
            + "3. Professor: professor or prof";

    public static final String MESSAGE_INPUT_SIZE_CONSTRAINTS =
            "ModuleCode array and RoleType array must be of the same length";

    public static final String MESSAGE_SINGLE_ROLE_PER_MODULE_CONSTRAINTS =
            "A person should have only one role per module. Check that there are no duplicate module codes";

    /*
     * Input keyword must match one of the keyword provided in the regex.
     * Note that the pattern is only matching lower case, so the other methods
     * need to convert user input to lower case first before validate the input
     */
    public static final String VALIDATION_REGEX = "^$|student|tutor|ta|professor|prof";

    /**
     * The roles will follow the order of the input.
     */
    private final LinkedHashMap<ModuleCode, RoleType> roles;

    /**
     * Default constructor for a {@code ModuleRoleMap}.
     * This is for creating roles for a Person based on the module code and role types
     * extracted from an add command.
     * The order follows the order of the input lists.
     *
     * @param moduleCodes Array of modules codes
     * @param roleTypes Array of role type String
     */
    public ModuleRoleMap(ModuleCode[] moduleCodes, RoleType[] roleTypes) {
        requireAllNonNull((Object[]) moduleCodes);
        requireAllNonNull((Object[]) roleTypes);

        checkArgument(areOfSameSize(moduleCodes, roleTypes), MESSAGE_INPUT_SIZE_CONSTRAINTS);

        LinkedHashMap<ModuleCode, RoleType> newRoles = new LinkedHashMap<>();
        for (int i = 0; i < moduleCodes.length; i++) {
            newRoles.put(moduleCodes[i], roleTypes[i]);
        }
        this.roles = newRoles;
    }

    /**
     * Constructor for a {@code ModuleRoleMap}.
     * Copy of the hashmap is performed to make the class immutable.
     * The order follows the order of the input map.
     *
     * @param roles new roles which are assigned to the Person
     */
    public ModuleRoleMap(Map<ModuleCode, RoleType> roles) {
        requireAllNonNull(roles);
        this.roles = new LinkedHashMap<>(roles);
    }

    /**
     * Gets a filtered stream of ModuleCode objects based on the given RoleType Enum Value.
     * Provides an easier way to query, search and manipulate modules.
     *
     * @param roleType RoleType enum value used to filter the ModuleCode
     * @return A Stream of ModuleCode that are linked to a specific RoleType
     */
    public Stream<ModuleCode> getFilteredModuleCodes(RoleType roleType) {
        requireNonNull(roleType);
        return this.roles.entrySet().stream()
                .filter(entry -> entry.getValue().equals(roleType))
                .map(Map.Entry::getKey);
    }

    /**
     * Gets the comma separated String representation of a list of moduleCodes based on
     * the filtered Stream of ModuleCode.
     *
     * @param moduleCodeStream filtered Stream of module codes.
     * @return comma separated String representation of a list of ModuleCode
     */
    public static String getModuleCodeString(Stream<ModuleCode> moduleCodeStream) {
        requireNonNull(moduleCodeStream);
        return moduleCodeStream
                .map(ModuleCode::toString)
                .collect(Collectors.joining(", "));
    }

    /**
     * Get the String representation of a Person's Role for a specific
     * role type.
     * For example, if a Person takes up roles of CS1101S Tutor, MA1522 Tutor,
     * CS2106 Student, CS3230 Student, and I call getRoleDescription(RoleType.Tutor).
     * I will get the description of the Tutor roles taken by this person like this:
     * "Tutor of: CS1101S, MA1522"
     *
     * @param roleType Type of role to search and display.
     * @return String representation of the roles with the specified type.
     */
    public String getRoleDescription(RoleType roleType) {
        requireNonNull(roleType);

        // Define prefix for String construction and matching
        String prefix = roleType + " of: ";
        StringBuilder description = new StringBuilder(prefix);

        // Get comma separated String of ModuleCodes based on the given RoleType
        Stream<ModuleCode> filteredStream = getFilteredModuleCodes(roleType);
        description.append(getModuleCodeString(filteredStream));

        return description.toString().endsWith(prefix)
                ? ""
                : description.toString();
    }

    @Override
    public String toString() {
        if (this.roles.isEmpty()) {
            return "This contact does not have a specified role";
        }
        StringBuilder rolesDescription = new StringBuilder();
        for (RoleType roleType: RoleType.values()) {
            String description = getRoleDescription(roleType);
            if (description.isEmpty()) {
                continue;
            }
            rolesDescription
                    .append(description)
                    .append("\n");
        }
        return rolesDescription.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ModuleRoleMap)) {
            return false;
        }
        ModuleRoleMap otherModuleRoleMap = (ModuleRoleMap) other;
        return this.roles.equals(otherModuleRoleMap.roles);
    }

    @Override
    public int hashCode() {
        return this.roles.hashCode();
    }

    /**
     * Gets the roles in HashMap.
     *
     */
    public LinkedHashMap<ModuleCode, RoleType> getRoles() {
        return this.roles;
    }

    /**
     * Returns a list of strings representing module role pairs.
     * Retains the original input order.
     */
    public List<ModuleRolePair> getData() {
        return getData(false);
    }

    /**
     * Returns a list of strings representing module role pairs.
     * @param sorted true to sort the list based on the {@code ModuleRolePair}'s natural ordering;
     *               false to retain the original input order.
     */
    public List<ModuleRolePair> getData(boolean sorted) {
        if (sorted) {
            return roles.entrySet().stream()
                    .map(entry -> new ModuleRolePair(entry.getKey(), entry.getValue()))
                    .sorted()
                    .collect(Collectors.toList());
        } else {
            return roles.entrySet().stream()
                    .map(entry -> new ModuleRolePair(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Returns true if the module role map is empty.
     */
    public boolean isEmpty() {
        return this.roles.isEmpty();
    }

    /**
     * Adds all module role pairs from {@code moduleRolePairsToAdd} to {@code this}.
     * This is equivalent to a set union operation.
     * @param moduleRolePairsToAdd The module role pairs to add.
     * @return A {@code ModuleRoleMap} containing all pairs failed to add.
     *         (i.e. the moduleRolePairsToAdd U this)
     */
    public ModuleRoleMap putAll(List<ModuleRolePair> moduleRolePairsToAdd) {
        requireNonNull(moduleRolePairsToAdd);
        assert !moduleRolePairsToAdd.isEmpty() : "Module roles to add cannot be empty!";
        Map<ModuleCode, RoleType> failedModuleRoleMap = new HashMap<>();

        for (ModuleRolePair moduleRolePair : moduleRolePairsToAdd) {
            if (!this.put(moduleRolePair.moduleCode, moduleRolePair.roleType)) {
                failedModuleRoleMap.put(moduleRolePair.moduleCode, moduleRolePair.roleType);
            }
        }
        return new ModuleRoleMap(failedModuleRoleMap);
    }

    /**
     * Removes all module role pairs from {@code moduleRolePairsToRemove} from {@code this}.
     * This is equivalent to a set difference operation.
     * @param moduleRolePairsToRemove The module role pairs to remove.
     * @return A {@code ModuleRoleMap} containing all pairs failed to remove.
     *         (i.e. moduleRolePairsToRemove \ this)
     */
    public List<ModuleRolePair> removeAll(List<ModuleRolePair> moduleRolePairsToRemove) {
        requireNonNull(moduleRolePairsToRemove);
        List<ModuleRolePair> failedModuleRolePairs = new ArrayList<>();

        for (ModuleRolePair moduleRolePair : moduleRolePairsToRemove) {
            if (!this.remove(moduleRolePair.moduleCode, moduleRolePair.roleType)) {
                failedModuleRolePairs.add(moduleRolePair);
            }
        }
        return failedModuleRolePairs;
    }

    /**
     * Removes all module role pairs from {@code this}. This method checks module codes only and ignores role types.
     * This is equivalent to a set difference operation.
     * @param moduleCodesToRemove The module codes to remove.
     * @return A {@code ModuleRoleMap} containing all pairs failed to remove.
     */
    public List<ModuleCode> removeAllIgnoringRoles(List<ModuleCode> moduleCodesToRemove) {
        requireNonNull(moduleCodesToRemove);

        List<ModuleCode> failedModuleCodes = new ArrayList<>();

        for (ModuleCode moduleCode : moduleCodesToRemove) {
            if (!this.removeIgnoringRole(moduleCode)) {
                failedModuleCodes.add(moduleCode);
            }
        }
        return failedModuleCodes;
    }

    /**
     * Adds a module role pair to the module role map.
     * @param moduleCode The module code to add.
     * @param role The role to add.
     * @return True if the module role pair is added successfully.
     */
    public boolean put(ModuleCode moduleCode, RoleType role) {
        if (this.roles.containsKey(moduleCode)) {
            return false;
        }

        this.roles.put(moduleCode, role);
        return true;
    }

    /**
     * Adds a module role pair to the module role map.
     * @param moduleRolePair The module role pair to add.
     * @return True if the module role pair is added successfully.
     */
    public boolean put(ModuleRolePair moduleRolePair) {
        return put(moduleRolePair.moduleCode, moduleRolePair.roleType);
    }

    /**
     * Removes a module role pair from the module role map.
     * @param moduleCode The module code to remove.
     * @param role The role to remove.
     * @return True if the module role pair is removed successfully.
     */
    public boolean remove(ModuleCode moduleCode, RoleType role) {
        return roles.remove(moduleCode, role);
    }

    /**
     * Removes a module role pair from the module role map.
     * @param moduleRolePair The module role pair to remove.
     * @return True if the module role pair is removed successfully.
     */
    public boolean remove(ModuleRolePair moduleRolePair) {
        return remove(moduleRolePair.moduleCode, moduleRolePair.roleType);
    }

    /**
     * Removes a module role pair from the module role map. This method checks module codes only and ignores role types.
     * @param moduleCode The module code to remove.
     * @return True if the module role pair is removed successfully.
     */
    public boolean removeIgnoringRole(ModuleCode moduleCode) {
        if (this.containsModule(moduleCode)) {
            this.roles.remove(moduleCode);
            return true;
        }
        return false;
    }

    /**
     * Returns true if the module role map contains the specified module code.
     * @param moduleCode The module code to check.
     * @return True if the module role map contains the specified module code.
     */
    public boolean containsModule(ModuleCode moduleCode) {
        return roles.containsKey(moduleCode);
    }

    /**
     * Returns true if the module role map contains the specified module role.
     * @param moduleCode The module code to check.
     * @param role The role to check.
     * @return True if the module role map contains the specified module role.
     */
    public boolean containsModuleRolePair(ModuleCode moduleCode, RoleType role) {
        return roles.containsKey(moduleCode) && roles.get(moduleCode).equals(role);
    }

    /**
     * Returns true if the module role map contains the specified module role pair.
     * @param moduleRolePair The module role pair to check.
     * @return True if the module role map contains the specified module role pair.
     */
    public boolean containsModuleRolePair(ModuleRolePair moduleRolePair) {
        return containsModuleRolePair(moduleRolePair.moduleCode, moduleRolePair.roleType);
    }
}
