package keycontacts.model;

import java.nio.file.Path;

import keycontacts.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getStudentDirectoryFilePath();

}
