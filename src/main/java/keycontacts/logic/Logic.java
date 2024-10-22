package keycontacts.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import keycontacts.commons.core.GuiSettings;
import keycontacts.logic.commands.CommandResult;
import keycontacts.logic.commands.exceptions.CommandException;
import keycontacts.logic.parser.exceptions.ParseException;
import keycontacts.model.Model;
import keycontacts.model.ReadOnlyStudentDirectory;
import keycontacts.model.student.Student;

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
     * Returns the StudentDirectory.
     *
     * @see Model#getStudentDirectory()
     */
    ReadOnlyStudentDirectory getStudentDirectory();

    /** Returns an unmodifiable view of the filtered list of students */
    ObservableList<Student> getStudentList();

    /**
     * Returns the user prefs' student directory file path.
     */
    Path getStudentDirectoryFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
