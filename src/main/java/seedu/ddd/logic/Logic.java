package seedu.ddd.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.ddd.commons.core.GuiSettings;
import seedu.ddd.logic.commands.CommandResult;
import seedu.ddd.logic.commands.exceptions.CommandException;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.Displayable;
import seedu.ddd.model.Model;
import seedu.ddd.model.ReadOnlyAddressBook;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.event.common.Event;

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

    /** Returns an unmodifiable view of the filtered list of contacts */
    ObservableList<Contact> getFilteredContactList();

    /** Returns an unmodifiable view of the filtered list of contacts */
    ObservableList<Event> getFilteredEventList();

    /** Returns an unmodifiable view of the list of items to display */
    ObservableList<Displayable> getDisplayedList();

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
