package seedu.address.model;

import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;
import seedu.address.model.shortcut.ShortCut;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    ObservableList<Person> getFilteredPersonList(Set<Tag> tagList);

    ObservableList<Order> getOrderList();
    ObservableList<ShortCut> getShortCutList();

}
