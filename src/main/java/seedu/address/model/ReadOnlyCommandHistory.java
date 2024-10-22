package seedu.address.model;

import java.util.Deque;

/**
 * Unmodifiable view of command history
 */
public interface ReadOnlyCommandHistory {
    Deque<String> getCommandStack();
}
