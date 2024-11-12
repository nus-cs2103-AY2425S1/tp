package careconnect.logic;

import java.nio.file.Path;

import careconnect.commons.core.GuiSettings;
import careconnect.logic.autocompleter.exceptions.AutocompleteException;
import careconnect.logic.commands.CommandResult;
import careconnect.logic.commands.exceptions.CommandException;
import careconnect.logic.parser.exceptions.ParseException;
import careconnect.model.Model;
import careconnect.model.ReadOnlyAddressBook;
import careconnect.model.person.Person;
import javafx.collections.ObservableList;


/**
 * API of the Logic component
 */
public interface Logic {

    /**
     * Represents results of validating syntax
     */
    enum ValidateSyntaxResultEnum {
        VALID_COMMAND_WORD,
        VALID_FULL_COMMAND,
        INVALID_COMMAND,
    }

    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Provides an autocomplete suggestion based on the given command text
     * @param commandText The partial command for which to generate an autocomplete suggestion.
     * @return A string containing the most relevant autocomplete suggestion based on the given input.
     * @throws AutocompleteException If no valid autocomplete suggestion can be generated.
     */
    String autocompleteCommand(String commandText) throws AutocompleteException;

    /**
     * Checks if given string is valid syntax.
     *
     * @param syntax The syntax to check
     * @return true if given string is valid syntax; else false
     */
    ValidateSyntaxResultEnum validateSyntax(String syntax);

    /**
     * Returns the AddressBook.
     *
     * @see Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

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
