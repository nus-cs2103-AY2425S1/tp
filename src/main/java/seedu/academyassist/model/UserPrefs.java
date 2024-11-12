package seedu.academyassist.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.academyassist.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path academyAssistFilePath = Paths.get("data" , "academyassist.json");
    private boolean isFirstTime = true;

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setAcademyAssistFilePath(newUserPrefs.getAcademyAssistFilePath());
        setIsFirstTime(newUserPrefs.isFirstTime());
    }

    @Override
    public boolean isFirstTime() {
        return isFirstTime;
    }

    public void setIsFirstTime(boolean isFirstTime) {
        this.isFirstTime = isFirstTime;
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getAcademyAssistFilePath() {
        return academyAssistFilePath;
    }

    public void setAcademyAssistFilePath(Path academyAssistFilePath) {
        requireNonNull(academyAssistFilePath);
        this.academyAssistFilePath = academyAssistFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UserPrefs)) {
            return false;
        }

        UserPrefs otherUserPrefs = (UserPrefs) other;
        return guiSettings.equals(otherUserPrefs.guiSettings)
                && academyAssistFilePath.equals(otherUserPrefs.academyAssistFilePath)
                && isFirstTime == otherUserPrefs.isFirstTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, academyAssistFilePath, isFirstTime);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + academyAssistFilePath);
        sb.append("\nIs first time : " + isFirstTime);
        return sb.toString();
    }

}
