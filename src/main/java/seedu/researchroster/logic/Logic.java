package seedu.researchroster.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.researchroster.commons.core.GuiSettings;
import seedu.researchroster.logic.commands.CommandResult;
import seedu.researchroster.logic.commands.exceptions.CommandException;
import seedu.researchroster.logic.parser.exceptions.ParseException;
import seedu.researchroster.model.ReadOnlyResearchRoster;
import seedu.researchroster.model.person.Person;

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
     * Returns the ResearchRoster.
     *
     * @see seedu.researchroster.model.Model#getAddressBook()
     */
    ReadOnlyResearchRoster getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the user prefs' researchroster book file path.
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
