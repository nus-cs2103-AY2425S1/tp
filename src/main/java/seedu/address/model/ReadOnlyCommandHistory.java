package seedu.address.model;

import java.nio.file.Path;

/**
 * Unmodifiable view of command history
 */
public interface ReadOnlyCommandHistory {
    Path getAddressBookFilePath();
}
