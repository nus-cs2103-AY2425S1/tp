package seedu.address.commons.core;

/**
 * Represents which state of display (Student, Group, Task) the GUI is currently in.
 * Saved in UserPrefs.
 */
public class State {
    private static final String DEFAULT = "Students";
    private final String state;

    public State() {
        this.state = DEFAULT;
    }

    public State(String state) {
        this.state = state;
    }    

    public boolean equals(State other) {
        return this.state.equals(other.state);
    }
}
