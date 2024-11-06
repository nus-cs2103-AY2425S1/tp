package seedu.address.ui;

import seedu.address.model.person.Person;

/**
 * Represents an observer in the observer design pattern.
 * <p>
 * Classes implementing this interface are notified of changes in observable subjects
 * and can perform updates in response. The specific behavior of {@code update()} depends on
 * the implementation, which may include refreshing UI elements, logging changes, or
 * performing other relevant actions.
 * </p>
 */
public interface Observer {
    /**
     * Updates the current state of this object.
     * <p>
     * This method performs any necessary actions to bring the object's state
     * up-to-date, ensuring it reflects the latest data or changes. The exact
     * behavior of this update depends on the implementing class.
     * </p>
     */
    public void update();
    public void update(Person person);
}
