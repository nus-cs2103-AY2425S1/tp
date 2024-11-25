package seedu.hireme.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.hireme.commons.core.GuiSettings;
import seedu.hireme.commons.core.LogsCenter;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private static final Logger logger = LogsCenter.getLogger(UserPrefs.class);
    private GuiSettings guiSettings = new GuiSettings();
    private Path hireMeFilePath = Paths.get("data" , "hireme.json");

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
        logger.info("Setting new user preferences");
        setGuiSettings(newUserPrefs.getGuiSettings());
        setHireMeFilePath(newUserPrefs.getHireMeFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        logger.info("Setting new gui settings");
        this.guiSettings = guiSettings;
    }

    public Path getHireMeFilePath() {
        return hireMeFilePath;
    }

    public void setHireMeFilePath(Path hireMeFilePath) {
        requireNonNull(hireMeFilePath);
        logger.info("Setting new HireMe file path");
        this.hireMeFilePath = hireMeFilePath;
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
                && hireMeFilePath.equals(otherUserPrefs.hireMeFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, hireMeFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + hireMeFilePath);
        return sb.toString();
    }

}
