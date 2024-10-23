package seedu.address.model;

import seedu.address.commons.core.CommandStack;

/**
 * Unmodifiable view of command history
 */
public interface ReadOnlyCommandHistory {
    CommandStack getCommandStack();
}
