package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Lists all upcoming appointments in the address book to the user.
 */
public class ListAppointmentsCommand extends Command {

    public static final String COMMAND_WORD = "appointment-list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all upcoming appointments in the address book.\n"
            + "Parameters: [DATE] [TIME]\n"
            + "DATE: Optional date filter in the format YYYY-MM-DD\n"
            + "TIME: Optional time filter in the format HH:mm\n"
            + "Example: " + COMMAND_WORD + " 2024-10-15 14:30";

    public static final String MESSAGE_SUCCESS = "Listed %d upcoming appointments";

    private final Optional<LocalDate> dateFilter;
    private final Optional<LocalTime> timeFilter;

    /**
     * All fields are optional
     * @param dateFilter
     * @param timeFilter
     */
    public ListAppointmentsCommand(Optional<LocalDate> dateFilter, Optional<LocalTime> timeFilter) {
        this.dateFilter = dateFilter;
        this.timeFilter = timeFilter;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Person> personList = model.getFilteredPersonList();

        LocalDateTime now = LocalDateTime.now();
        Optional<LocalTime> effectiveTimeFilter = dateFilter.isPresent() ? timeFilter : Optional.empty();

        List<Person> filteredAppointments = personList.stream()
                .filter(person -> person.hasAppointment(now, dateFilter, effectiveTimeFilter))
                .collect(Collectors.toList());

        return new CommandResult(String.format(MESSAGE_SUCCESS, filteredAppointments.size())
                + "\n" + formatAppointments(filteredAppointments));
    }

    /**
     * Formats the list of persons with upcoming appointments into a string.
     *
     * @param appointments List of persons with upcoming appointments.
     * @return A formatted string containing the names and schedules of persons with upcoming appointments.
     */
    private String formatAppointments(List<Person> appointments) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");

        return appointments.stream()
                .map(person -> {
                    Name personName = person.getName();
                    String formattedSchedules = person.getSchedules().stream()
                            .map(schedule -> {
                                LocalDateTime dateTime = LocalDateTime.parse(schedule.getDateTime(), inputFormatter);
                                return dateTime.format(outputFormatter);
                            })
                            .collect(Collectors.joining(", "));
                    return String.format("%s: %s", personName.fullName, formattedSchedules);
                })
                .collect(Collectors.joining("\n"));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ListAppointmentsCommand)) {
            return false;
        }
        ListAppointmentsCommand otherCommand = (ListAppointmentsCommand) other;
        return Objects.equals(dateFilter, otherCommand.dateFilter)
                && Objects.equals(timeFilter , otherCommand.timeFilter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateFilter, timeFilter);
    }
}
