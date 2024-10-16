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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UiState)) {
            return false;
        }

        UiState e = (UiState) other;
        return currentState.equals(e.currentState);
    }
}
