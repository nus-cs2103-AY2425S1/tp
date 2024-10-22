package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a delivery's archive status
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
        return this.value.equals("true");
    }


    @Override
    public String toString() {
        return "Archive: " + value;
    }
}
