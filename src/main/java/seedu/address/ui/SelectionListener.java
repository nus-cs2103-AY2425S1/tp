package seedu.address.ui;

import seedu.address.model.person.Person;

/**
 * A listener for selection events in the application.
 *
 * The listener receives updates when there is any change made to the components that have a {@code SelectionListener}.
 * In this context, the SelectionListener class is implemented to handle the event where a person is selected from the
 * {@code PersonListPanel}.
 */
public interface SelectionListener {
    /**
     * Called when a person is selected by the user.
     * This method provides a mechanism for handling the event that occurs
     * when a user selects a person from {@code PersonListPanel}.
     *
     * @param person The {@link Person} that was selected. This object contains
     *               the data of the selected person.
     * @param index  The index at which the person was selected in the list or collection.
     *               This can be used for reference or to manage ordered collections.
     */
    void onPersonSelected(Person person, int index);
}
