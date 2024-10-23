package seedu.address.logic;

import seedu.address.model.person.Person;

/**
 * StaticContext class to store the person to be deleted.
 */
public class StaticContext {
    private static Person personToDelete;

    public static Person getPersonToDelete() {
        return personToDelete;
    }

    public static void setPersonToDelete(Person personToDelete) {
        StaticContext.personToDelete = personToDelete;
    }
}
