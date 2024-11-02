package seedu.academyassist.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.academyassist.commons.core.GuiSettings;
import seedu.academyassist.logic.commands.CommandResult;
import seedu.academyassist.logic.commands.exceptions.CommandException;
import seedu.academyassist.logic.parser.exceptions.ParseException;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.ReadOnlyAcademyAssist;
import seedu.academyassist.model.person.Person;

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
     * Returns the AcademyAssist.
     *
     * @see Model#getAcademyAssist()
     */
    ReadOnlyAcademyAssist getAcademyAssist();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAcademyAssistFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the Model instance.
     */
    Model getModel();
}
