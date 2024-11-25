package seedu.hireme.model;

import java.nio.file.Path;

import seedu.hireme.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getHireMeFilePath();

}
