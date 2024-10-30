package seedu.eventtory.model;

import java.nio.file.Path;

import seedu.eventtory.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getEventToryFilePath();

}
