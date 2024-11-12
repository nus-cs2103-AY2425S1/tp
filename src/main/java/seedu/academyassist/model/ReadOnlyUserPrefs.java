package seedu.academyassist.model;

import java.nio.file.Path;

import seedu.academyassist.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAcademyAssistFilePath();

    boolean isFirstTime();

}
