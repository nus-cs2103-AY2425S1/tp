package tuteez.logic;

import java.nio.file.Path;
import java.util.Optional;

import javafx.collections.ObservableList;
import tuteez.commons.core.GuiSettings;
import tuteez.logic.commands.CommandResult;
import tuteez.logic.commands.exceptions.CommandException;
import tuteez.logic.parser.exceptions.ParseException;
import tuteez.model.ReadOnlyAddressBook;
import tuteez.model.person.Person;

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
     * @see tuteez.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the person's details */
    Optional<Person> getLastViewedPerson();

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
