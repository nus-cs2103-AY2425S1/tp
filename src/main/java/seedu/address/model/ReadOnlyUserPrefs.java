package seedu.address.model;

import java.nio.file.Path;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.State;
import seedu.address.model.task.Task;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

    String getMostRecentGroupTaskDisplay();

    Task getMostRecentTaskDisplay();

    State getState();

}
