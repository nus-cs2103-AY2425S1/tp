package seedu.hiredfiredpro.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.hiredfiredpro.commons.core.GuiSettings;
import seedu.hiredfiredpro.logic.commands.CommandResult;
import seedu.hiredfiredpro.logic.commands.exceptions.CommandException;
import seedu.hiredfiredpro.logic.parser.exceptions.ParseException;
import seedu.hiredfiredpro.model.ReadOnlyHiredFiredPro;
import seedu.hiredfiredpro.model.person.Person;

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
     * Returns the HiredFiredPro.
     *
     * @see seedu.hiredfiredpro.model.Model#getHiredFiredPro()
     */
    ReadOnlyHiredFiredPro getHiredFiredPro();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    ObservableList<Person> getSortedPersonList();

    /**
     * Returns the user prefs' hiredfiredpro file path.
     */
    Path getHiredFiredProFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
