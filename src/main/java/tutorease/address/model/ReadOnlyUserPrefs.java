package tutorease.address.model;

import java.nio.file.Path;

import tutorease.address.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getTutorEaseFilePath();

}
