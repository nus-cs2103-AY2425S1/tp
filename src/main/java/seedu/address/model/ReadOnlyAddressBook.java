package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.Vendor;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.wedding.Wedding;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the vendors list.
     * This list will not contain any duplicate vendors.
     */
    ObservableList<Vendor> getVendorList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

    /**
     * Returns an unmodifiable view of the tasks list.
     * This list will not contain any duplicate tasks.
     */
    ObservableList<Task> getTaskList();

    /**
     * Returns an unmodifiable view of the weddings list.
     * This list will not contain any duplicate weddings.
     */
    ObservableList<Wedding> getWeddingList();

}
