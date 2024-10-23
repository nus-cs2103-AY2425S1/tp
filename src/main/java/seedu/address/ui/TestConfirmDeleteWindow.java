package seedu.address.ui;

import seedu.address.model.person.Person;

/**
 * Mock Confirm Delete Window for testing purposes
 */
public class TestConfirmDeleteWindow extends ConfirmDeleteWindow {

    private boolean shouldConfirm;

    /**
     * Constructor for a TestConfirmDelete Window
     * @param shouldConfirm
     */
    public TestConfirmDeleteWindow(boolean shouldConfirm) {
        super();
        this.shouldConfirm = shouldConfirm;
    }

    @Override
    public void show(Person personToDelete) {
        // do nothing
    }

    @Override
    public boolean isConfirmed() {
        return shouldConfirm;
    }
}
