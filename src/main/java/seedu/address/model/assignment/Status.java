package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an assignment's status
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {
    public static final String MESSAGE_CONSTRAINTS =
            "Status should either be Y or N (case insensitive)";

    public static final String VALIDATION_REGEX = "Y|N|y|n";

    /**
     * Status of a submission / grading
     */
    public enum State {
        Y,
        N
    }

    public final State status;

    /**
     * Constructs a {@code Status}
     *
     * @param status A valid status
     */
    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        if (status.equalsIgnoreCase("y")) {
            this.status = State.Y;
        } else {
            this.status = State.N;
        }
    }

    /**
     * Constructs a {@code Status}
     *
     * @param state A valid state
     */
    private Status(State state) {
        this.status = state;
    }

    /**
     * Factory method to initialize default status.
     */
    public static Status getDefault() {
        return new Status(State.N);
    }

    /**
     * Returns true if the given string is a valid status
     */
    public static boolean isValidStatus(String status) {
        return status.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if the assignment is submitted
     */
    public boolean isSubmitted() {
        return status == State.Y;
    }

    @Override
    public String toString() {
        return status.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Status)) {
            return false;
        }

        Status otherStatus = (Status) other;
        return this.status.equals(otherStatus.status);
    }

}
