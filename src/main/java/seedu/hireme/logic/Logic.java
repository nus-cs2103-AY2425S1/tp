package seedu.hireme.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.hireme.commons.core.GuiSettings;
import seedu.hireme.logic.commands.CommandResult;
import seedu.hireme.logic.commands.exceptions.CommandException;
import seedu.hireme.logic.parser.exceptions.ParseException;
import seedu.hireme.model.Model;
import seedu.hireme.model.ReadOnlyAddressBook;
import seedu.hireme.model.internshipapplication.InternshipApplication;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of internship applications */
    ObservableList<InternshipApplication> getFilteredList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
