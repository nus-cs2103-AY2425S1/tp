package seedu.address.model;

import static java.util.Objects.requireNonNull;

/**
 * Represents the particular version of T_Assistant.
 */
public class History {
    private Model model;
    private ReadOnlyUserPrefs userPrefs;
    private History prev = null;
    private History next = null;

    public History(Model model, ReadOnlyUserPrefs userPrefs) {
        this.model = model;
        this.userPrefs = userPrefs;
    }

    public void setNext(History history) {
        this.next = history;
    }

    public void setPrev(History history) {
        this.prev = history;
    }
}