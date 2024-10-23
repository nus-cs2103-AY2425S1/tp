package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Person's job in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidJob(String)}
 */
public class Job {

    public static final String MESSAGE_CONSTRAINTS = "Job can be any non-empty string.";

    public final String value;

    /**
     * Constructs a {@code Job}.
     *
     * @param job A valid job.
     */
    public Job(String job) {
        requireNonNull(job);
        checkArgument(isValidJob(job), "Job is invalid");
        this.value = job;
    }

    /**
     * Returns true if a given string is a valid job.
     */
    public static boolean isValidJob(String test) {
        return test != null && !test.trim().isEmpty();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Job) {
            Job otherJob = (Job) other;
            return value.equals(otherJob.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
