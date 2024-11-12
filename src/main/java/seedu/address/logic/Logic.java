package seedu.address.logic;

import java.nio.file.Path;
import java.util.TreeSet;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyClinicConnectSystem;
import seedu.address.model.filteredappointment.FilteredAppointment;
import seedu.address.model.patient.Patient;

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
     * Returns the ClinicConnectSystem.
     *
     * @see seedu.address.model.Model#getClinicConnectSystem()
     */
    ReadOnlyClinicConnectSystem getClinicConnectSystem();

    /** Returns an unmodifiable view of the filtered list of patients */
    ObservableList<Patient> getFilteredPatientList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getClinicConnectSystemFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    TreeSet<FilteredAppointment> getFilteredAppts();
}
