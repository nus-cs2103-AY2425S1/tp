package seedu.address.model;

import java.nio.file.Path;

import seedu.address.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    /**
     * Returns the GUI settings.
     *
     * @return The {@code GuiSettings}
     */
    GuiSettings getGuiSettings();

    /**
     * Returns the address book file path
     *
     * @return The {@code Path} to the address book file
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user's sort preference
     *
     * @return The sort preference as a {@code String}
     */
    String getSortPreference();

}
