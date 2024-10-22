package seedu.address.model;

import java.nio.file.Path;
import java.util.Deque;

/**
 * Unmodifiable view of command history
 */
public interface ReadOnlyCommandHistory {
    Deque<String> getCommandStack();
    Path getAddressBookFilePath();
}
