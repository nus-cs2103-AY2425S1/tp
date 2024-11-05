package seedu.hiredfiredpro.model;

import java.nio.file.Path;

import seedu.hiredfiredpro.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getHiredFiredProFilePath();

}
