package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CommandTextHistory;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.employee.Employee;
import seedu.address.model.project.Project;

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
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns the {@code CommandTextHistory}.
     *
     * @see seedu.address.model.Model#getCommandTextHistory()
     */
    CommandTextHistory getCommandTextHistory();

    /** Returns an unmodifiable view of the filtered list of employees */
    ObservableList<Employee> getFilteredEmployeeList();

    /** Returns an unmodifiable view of the filtered list of projects */
    ObservableList<Project> getFilteredProjectList();

    /** Returns an unmodifiable view of the filtered list of assignments */
    ObservableList<Assignment> getFilteredAssignmentList();

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
