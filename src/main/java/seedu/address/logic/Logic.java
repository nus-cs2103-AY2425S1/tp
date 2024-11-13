package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.contactrecord.ContactRecord;
import seedu.address.model.person.Person;

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
     * Returns an unmodifiable view of the sorted and filtered list of persons
     */
    SortedList<Person> getSortedFilteredPersonList();

    /**
     * Returns an unmodifiable view of the call history.
     */
    ObservableList<ContactRecord> getDisplayedCallHistory();

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

    /**
     * Adds the command text to the history.
     * @param commandText The command text as entered by the user.
     */
    void addCommandTextToHistory(String commandText);

    /**
     * Returns the next command text from the history.
     */
    String getNextCommandTextFromHistory();

    /**
     * Returns the person to display.
     */
    Person getPersonToDisplay();

    /**
     * Returns the previous command text from the history.
     */
    String getPreviousCommandTextFromHistory();
}
