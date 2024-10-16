package seedu.address.ui;

/**
 * Stores the current state of the UI display.
 */
public class UiState {

    /**
     * States of UI display.
     */
    public enum State {
        Details, Tasks
    }

    private State currentState = State.Details;

    /**
     * Returns the UI state of the AddressBook.
     */
    public State getState() {
        return currentState;
    }

    /**
     * Sets the UI state of the AddressBook.
     */
    public void setState(State state) {
        currentState = state;
        System.out.println(state);
    }
}
