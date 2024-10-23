package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's job that is being applied for in HiredFiredPro.
 * Guarantees: immutable; is valid as declared in {@link #isValidJob(String)}
 */
public class Job {

    public static final String MESSAGE_CONSTRAINTS =
            "Error: Invalid job title.";

    /*
     * The first character of the job must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String jobName;

    /**
     * Constructs a {@code Job}.
     *
     * @param job A valid job.
     */
    public Job(String job) {
        requireNonNull(job);
        checkArgument(isValidJob(job), MESSAGE_CONSTRAINTS);
        jobName = job;
    }

    /**
     * Returns true if a given string is a valid job.
     */
    public static boolean isValidJob(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if both jobs ignoring case are the same.
     * This defines a case-insensitive notion of equality between two jobs.
     */
    public boolean isSameJob(Job otherJob) {
        if (otherJob == this) {
            return true;
        }

        return otherJob != null
                && this.jobName.equalsIgnoreCase(otherJob.jobName);
    }


    @Override
    public String toString() {
        return jobName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Job)) {
            return false;
        }

        Job otherJob = (Job) other;
        return jobName.equals(otherJob.jobName);
    }

    @Override
    public int hashCode() {
        return jobName.hashCode();
    }

}
