package seedu.ddd.model;

import java.nio.file.Path;

import seedu.ddd.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

    Path getDDDFilePath();

}
