package seedu.address.model.role;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.person.Person;


/**
 * Represents a Role in the address book.
 */
public abstract class Role {
    public static final String MESSAGE_CONSTRAINTS = "Roles should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    private final HashSet<Person> personList = new HashSet<>();

    private final String roleName;

    /**
     * Constructs a {@code Role}.
     *
     * @param roleName A valid role name.
     */
    protected Role(String roleName) {
        requireNonNull(roleName);
        checkArgument(isValidRoleName(roleName), MESSAGE_CONSTRAINTS);
        this.roleName = roleName;
    }

    /**
     * Returns roleName
     */
    public String getRoleName() {
        return this.roleName;
    }



    /**
     * Returns true if a given string is a valid role name.
     */
    public static boolean isValidRoleName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return roleName.equals(otherRole.roleName);
    }

    @Override
    public int hashCode() {
        return roleName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + roleName + ']';
    }

    /**
     * Gets the people tagged under role as a string. Should be displayed as [ person1, person2, ... ]
     * @return String representation of all people tagged under a role
     */
    public String getPeopleString() {
        List<Person> people = new ArrayList<>(personList);
        Collections.sort(people);
        return people.toString();
    }

    /**
     * Adds a person to the role
     * @param person the person to be added
     */
    public void addPerson(Person person) {
        this.personList.add(person);
    }

    /**
     * Removes a person from the role
     * @param person the person to be removed
     */
    public void removePerson(Person person) {
        if (!this.personList.contains(person)) {
            throw new IllegalArgumentException("Person not found in role");
        }
        this.personList.remove(person);
        //TODO: maybe exception if not found? unsure currently
        // originally tagged

    }
    /**
     * Returns the people tagged under the role
     * @return Set of people tagged under the role
     */
    public Set<Person> getPeople() {
        return this.personList;
    }

    public boolean isTagged(Person person) {
        return this.personList.contains(person);
    }
}
