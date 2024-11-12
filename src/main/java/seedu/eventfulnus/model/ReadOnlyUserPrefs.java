package seedu.eventfulnus.model;

import java.nio.file.Path;

import seedu.eventfulnus.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

}
