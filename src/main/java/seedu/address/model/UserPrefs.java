package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path clientBookFilePath = Paths.get("data" , "clientbook.json");
    private Path propertyBookFilePath = Paths.get("data" , "propertybook.json");
    private Path meetingBookFilePath = Paths.get("data" , "meetingbook.json");

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
        setClientBookFilePath(newUserPrefs.getClientBookFilePath());
        setPropertyBookFilePath(newUserPrefs.getPropertyBookFilePath());
        setMeetingBookFilePath(newUserPrefs.getMeetingBookFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getClientBookFilePath() {
        return clientBookFilePath;
    }

    public void setClientBookFilePath(Path propertyBookFilePath) {
        requireNonNull(propertyBookFilePath);
        this.clientBookFilePath = propertyBookFilePath;
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
                && clientBookFilePath.equals(otherUserPrefs.clientBookFilePath)
                && propertyBookFilePath.equals(otherUserPrefs.propertyBookFilePath)
                && meetingBookFilePath.equals(otherUserPrefs.meetingBookFilePath);
    }

    public Path getPropertyBookFilePath() {
        return propertyBookFilePath;
    }

    public void setPropertyBookFilePath(Path propertyBookFilePath) {
        requireNonNull(propertyBookFilePath);
        this.propertyBookFilePath = propertyBookFilePath;
    }

    public Path getMeetingBookFilePath() {
        return meetingBookFilePath;
    }

    public void setMeetingBookFilePath(Path meetingBookFilePath) {
        requireNonNull(meetingBookFilePath);
        this.meetingBookFilePath = meetingBookFilePath;
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, clientBookFilePath, propertyBookFilePath, meetingBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nClient book file location : " + clientBookFilePath);
        sb.append("\nProperty book file location : " + propertyBookFilePath);
        sb.append("\nMeeting book file location : " + meetingBookFilePath);
        return sb.toString();
    }
}
