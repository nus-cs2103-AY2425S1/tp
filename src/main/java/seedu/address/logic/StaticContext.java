package seedu.address.logic;

import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * StaticContext class to store the person or wedding to be deleted.
 */
public class StaticContext {
    private static Person personToDelete;
    private static Wedding weddingToDelete;

    public static Person getPersonToDelete() {
        return personToDelete;
    }

    public static Wedding getWeddingToDelete() {
        return weddingToDelete;
    }

    public static void setPersonToDelete(Person personToDelete) {
        StaticContext.personToDelete = personToDelete;
    }


    public static void setWeddingToDelete(Wedding weddingToDelete) {
        StaticContext.weddingToDelete = weddingToDelete;
    }
}
