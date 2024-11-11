package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManager.DisplayMode;
import seedu.address.model.client.Client;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.property.Property;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns an unmodifiable view of the filtered list of clients (containing both buyers and sellers)
     */
    ObservableList<Client> getFilteredClientList();
    /**
     * Returns an unmodifiable view of the filtered list of properties
     */
    ObservableList<Property> getFilteredPropertyList();

    /**
     * Returns an unmodifiable view of the filtered list of meetings
     */
    ObservableList<Meeting> getFilteredMeetingList();

    /**
     * Returns the user prefs' client book file path.
     */
    Path getClientBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    ReadOnlyObjectProperty<DisplayMode> getReadOnlyDisplayMode();
}
