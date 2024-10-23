package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.State;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    // used to define strictness of FuzzyWuzzy ratio
    // higher means must match more
    public static final int MATCH_RATIO = 75;
    private static final State DEFAULT_STATE = new State("Students");
    private static final State GROUP_STATE = new State("Groups");
    private static final State GROUP_TASK_STATE = new State("GroupTask");
    private static final State TASK_STATE = new State("Tasks");
    private String mostRecentGroupTaskDisplay = "";
    private State guiState = GROUP_STATE;
    private GuiSettings guiSettings = new GuiSettings();
    private Path addressBookFilePath = Paths.get("data", "addressbook.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {
    }

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Sets the state of the system to "Students".
     */
    public void setStateStudents() {
        this.guiState = DEFAULT_STATE;
    }

    /**
     * Sets the state of the system to "Groups".
     */
    public void setStateGroups() {
        this.guiState = GROUP_STATE;
    }

    /**
     * Sets the state of the system to "Groups".
     */
    public void setStateGroupTask() {
        this.guiState = GROUP_TASK_STATE;
    }

    public void setStateTasks() {
        this.guiState = TASK_STATE;
    }

    public State getState() {
        return this.guiState;
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setAddressBookFilePath(newUserPrefs.getAddressBookFilePath());
        setMostRecentGroupTaskDisplay(newUserPrefs.getMostRecentGroupTaskDisplay());
        this.guiState = newUserPrefs.getState();
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public String getMostRecentGroupTaskDisplay() {
        return this.mostRecentGroupTaskDisplay;
    }

    public void setMostRecentGroupTaskDisplay(String groupName) {
        this.mostRecentGroupTaskDisplay = groupName;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.addressBookFilePath = addressBookFilePath;
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
            && addressBookFilePath.equals(otherUserPrefs.addressBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + addressBookFilePath);
        return sb.toString();
    }

}
