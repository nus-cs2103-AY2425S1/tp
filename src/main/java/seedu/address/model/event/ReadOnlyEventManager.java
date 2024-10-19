package seedu.address.model.event;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of the events list.
 */
public interface ReadOnlyEventManager {
    /**
     * Returns an unmodifiable view of the events list.
     */
    ObservableList<Event> getEventList();
}
