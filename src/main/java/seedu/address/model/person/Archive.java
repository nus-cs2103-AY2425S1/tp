package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a person's archive status
 */
public class Archive {
    public static final String MESSAGE_CONSTRAINTS = "Archive should be boolean";

    public final String value;

    /**
     * Constructs a {@code Archive}.
     *
     * @param isArchived A boolean.
     */
    public Archive(boolean isArchived) {
        value = Boolean.toString(isArchived);
    }

    /**
     * Constructs a {@code Archive} from Json data
     */
    public Archive(String archive) {
        requireNonNull(archive);
        checkArgument(isValidArchive(archive), MESSAGE_CONSTRAINTS);
        this.value = archive;
    }

    /**
     * Returns true if a given string is a valid archive.
     */
    public static boolean isValidArchive(String test) {
        requireNonNull(test);
        return test.equals("true") || test.equals("false");
    }

    /**
     * Returns true if a given string is a valid archive.
     */
    public boolean isArchived() {
        return Boolean.parseBoolean(this.value);
    }


    @Override
    public String toString() {
        return "Archive: " + value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Address)) {
            return false;
        }

        Archive otherArchive = (Archive) other;
        return value.equals(otherArchive.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
