package seedu.internbuddy.model;

import java.nio.file.Path;

import seedu.internbuddy.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefsCompany {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

}
