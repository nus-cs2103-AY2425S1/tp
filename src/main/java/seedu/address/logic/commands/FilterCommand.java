package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.filteredappointment.FilteredAppointment.APPOINTMENT_COMPARATOR;

import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointmentdatefilter.AppointmentDateFilter;
import seedu.address.model.filteredappointment.FilteredAppointment;

/**
 * Filters the patients based on their appointment dates and health services.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters existing patient records based on their "
            + "appointment dates and health services\n"
            + "Input \"help " + COMMAND_WORD + "\" for description and usage of this command";

    public static final String RETURN_TO_HOME = "\nInput \"home\" to return to home page";

    private final AppointmentDateFilter dateFilter;

    /**
     * Creates an FilterCommand to filter commands based on the date and {code@ HealthService}
     */
    public FilterCommand(AppointmentDateFilter dateFilter) {
        requireNonNull(dateFilter);
        this.dateFilter = dateFilter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.filterAppts(dateFilter);

        int patientsFiltered = model.getFilteredAppts().size();

        String patientLabel = patientsFiltered == 1 ? "patient" : "patients";

        String msg = patientsFiltered + " " + patientLabel + " found " + dateFilter;
        if (patientsFiltered == 0) {
            msg = "No " + patientLabel + " found " + dateFilter;
        }

        msg += RETURN_TO_HOME;

        return new CommandResult(msg, "appts");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return dateFilter.equals(otherFilterCommand.dateFilter);
    }
}
