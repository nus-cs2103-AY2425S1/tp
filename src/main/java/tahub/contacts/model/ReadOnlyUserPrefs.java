package tahub.contacts.model;

import java.nio.file.Path;

import tahub.contacts.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

    Path getCourseListFilePath();

    Path getScaListFilePath();

}
