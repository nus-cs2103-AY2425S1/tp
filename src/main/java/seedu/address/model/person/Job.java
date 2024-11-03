package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's job in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidJob(String)}
 */
public class Job {

    public static final String MESSAGE_CONSTRAINTS = "Job can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Job}.
     *
     * @param job A valid job.
     */
    public Job(String job) {
        requireNonNull(job);
        checkArgument(isValidJob(job), MESSAGE_CONSTRAINTS);
        value = capitaliseJob(job);
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidJob(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
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
        return value.equals(otherJob.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Capitalises the first letter of each word in a given job title.
     * Converts the rest of each word to lowercase and returns the formatted string.
     *
     * @param job job title as a string
     * @return a string of job title with each word capitalised
     */
    public String capitaliseJob(String job) {
        String capitalisedJob = "";
        String[] words = job.toLowerCase().split(" ");
        for (String word : words) {
            capitalisedJob += Character.toUpperCase(word.charAt(0)) + word.substring(1) + " ";
        }
        return capitalisedJob.trim();
    }
}
