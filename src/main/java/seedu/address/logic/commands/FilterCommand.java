package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHSERVICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;

import javafx.collections.transformation.FilteredList;
import javafx.util.Pair;
import org.w3c.dom.css.ElementCSSInlineStyle;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.healthservice.HealthService;
import seedu.address.model.person.AppointmentDateFilter;
import seedu.address.model.person.Appt;
import seedu.address.model.person.Person;

import java.time.LocalDate;
import java.util.List;

/**
 * Filters the patients based on their appointment dates and health services.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters patients based on their appointment dates and"
            + " healthservices. Parameters : "
            + PREFIX_STARTDATE + "START_DATE "
            + PREFIX_ENDDATE + "END_DATE "
            + "[" + PREFIX_HEALTHSERVICE + "HEALTHSERVICE] ...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STARTDATE + "2024-08-30 "
            + PREFIX_ENDDATE + "2024-11-30 "
            + PREFIX_HEALTHSERVICE + "VACCINATION";

    public static final String MESSAGE_SUCCESS = "List of patients sorted based on their appointment dates";

    private final AppointmentDateFilter dateFilter;

    public FilterCommand(AppointmentDateFilter dateFilter) {
        this.dateFilter = dateFilter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        LocalDate startDate = dateFilter.getStartDate() == null ? LocalDate.now() : dateFilter.getStartDate();
        LocalDate endDate = dateFilter.getEndDate();
        HealthService service = dateFilter.getHealthService();

        List<Person> personList = model.getFilteredPersonList();
        personList.stream()
                ## match the duration of appointments and match healthservice if not null
            ## add to a new List<Pair<Person, Appt>> to keep track of the NRIC and name
            get a new LIST
            then update UI based on Appointment

    }

}
