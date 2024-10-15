package seedu.address.ui;

import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * API of DetailView component.
 */
public interface DetailView<T> {

    /**
     * Updates the detail view with the given object and refreshes the corresponding GUI components.
     *
     * @param t the object containing the data used to update the view, which should be either
     *          a {@link Person} or an {@link Event}
     */
    void update(T t);
}
