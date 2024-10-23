package seedu.address.commons.core.filename;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 *
 */
public class Filename {
    public static final String MESSAGE_CONSTRAINTS = "Invalid filename. Filenames should not contain any of the "
            + "following characters: < > : \" / \\ | ? *";

    /* Regex pattern for valid Windows filenames:
     * - Must not contain any of the following characters: < > : " / \ | ? *
     */
    private static final String WINDOWS_VALIDATION_REGEX = "^[^<>:\"/\\\\|?*]*$";


    /* Regex pattern for valid Linux and MacOS filenames:
     * - Must not contain a forward slash (/)
     */
    private static final String LINUX_MAC_VALIDATION_REGEX = "^[^/]*$";

    private String filename;

    /**
     * Constructs a {@code Filename}.
     *
     * @param filename A valid filename.
     */
    public Filename(String filename) {
        requireNonNull(filename);
        checkArgument(isValidFilename(filename), MESSAGE_CONSTRAINTS);
        this.filename = filename;
    }

    /**
     * Returns true if a given string is a valid filename.
     *
     * @param test String to test.
     */
    public static boolean isValidFilename(String test) {
        return test.matches(WINDOWS_VALIDATION_REGEX) && test.matches(LINUX_MAC_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Filename otherFilename)) {
            return false;
        }

        return filename.equals(otherFilename.filename);
    }

    @Override
    public String toString() {
        return filename;
    }
}
