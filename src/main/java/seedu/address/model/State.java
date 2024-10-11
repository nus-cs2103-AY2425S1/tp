package seedu.address.model;

/**
 * Represents which state of display (Student, Group, Task) the GUI is currently in.
 * Saved in UserPrefs.
 */
public class State {
    private final String state;

    public State(String state) {
        this.state = state;
    }

    public boolean equals(State other) {
        return this.state.equals(other.state);
    }
}