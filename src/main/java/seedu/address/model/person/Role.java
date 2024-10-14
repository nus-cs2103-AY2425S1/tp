package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Represents the roles taken by a person in NUS.
 */
public class Role {
    public static final String MESSAGE_CONSTRAINTS = "Role consists of two parts, module code and role type.\n"
            + "Module code can take on any values, and should not be blank.\n"
            + "Role type can be blank, and or any of the following values(case insensitive) for each role type:\n"
            + "1. Student: Student or leave blank\n"
            + "2. Tutor: Tutor or TA\n"
            + "3. Professor: Professor or Prof\n";
    public static final String VALIDATION_REGEX = "^$|student|tutor|ta|professor|prof";
    private final HashMap<ModuleCode, RoleType> roles;

    /**
     * Default constructor for a {@code Role}.
     * This is for creating roles for a Person based on the module code and role types
     * extracted from an add command.
     *
     * @param moduleCodes Array of modules codes
     * @param roleTypes Array of role type String
     */
    public Role(ModuleCode[] moduleCodes, String[] roleTypes) {
        requireNonNull(moduleCodes);
        requireNonNull(roleTypes);
        if (moduleCodes.length != roleTypes.length) {
            throw new IllegalArgumentException(
                    "Module code array's length is not consistent with that of role type array.");
        }
        checkArgument(areValidRoleTypes(roleTypes), MESSAGE_CONSTRAINTS);
        HashMap<ModuleCode, RoleType> newRoles = new HashMap<>();
        for (int i = 0; i < moduleCodes.length; i++) {
            newRoles.put(moduleCodes[i], roleTypeStringToEnum(roleTypes[i]));
        }
        this.roles = newRoles;
    }

    /**
     * Constructor for a {@code Role}.
     * Copy of the hashmap is performed to make the class immutable.
     *
     * @param roles new roles which are assigned to the Person
     */
    public Role(HashMap<ModuleCode, RoleType> roles) {
        requireNonNull(roles);
        this.roles = new HashMap<>(roles);
    }

    /**
     * Returns true if the given role type String array contains all
     * valid role type keywords.
     *
     * @param roleTypes Array of role type keywords
     * @return whether the array contains all valid keywords
     */
    public static boolean areValidRoleTypes(String[] roleTypes) {
        requireNonNull(roleTypes);
        for (String keyword: roleTypes) {
            if (!keyword.matches(VALIDATION_REGEX)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Converts a given role type keyword to its corresponding RoleType Enum value.
     *
     * @param keyword Keyword representing a role type.
     * @return corresponding RoleType Enum value.
     */
    public static RoleType roleTypeStringToEnum(String keyword) {
        requireNonNull(keyword);
        switch (keyword.toLowerCase()) {
        case "": // Fall through
        case "student":
            return RoleType.STUDENT;
        case "tutor": // Fall through
        case "ta":
            return RoleType.TUTOR;
        case "professor": // Fall through
        case "prof":
            return RoleType.PROFESSOR;
        default:
            throw new IllegalArgumentException("Invalid role type keyword for conversion to Enum value");
        }
    }

    /**
     * Gets a filtered stream of ModuleCode objects based on the given RoleType Enum Value.
     * Provides a easier way to query, search and manipulate modules.
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
        String[] moduleCodeStringArray = moduleCodeStream
                .map(ModuleCode::toString)
                .toArray(String[]::new);
        return String.join(", ", moduleCodeStringArray);
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
        if (!(other instanceof Role)) {
            return false;
        }
        Role otherRole = (Role) other;
        return this.roles.equals(otherRole.roles);
    }

    @Override
    public int hashCode() {
        return this.roles.hashCode();
    }
}
