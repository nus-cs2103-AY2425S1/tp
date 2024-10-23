package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;

/**
 * Lists all upcoming appointments in the address book to the user.
 */
public class ListAppointmentsCommand extends Command {

    public static final String COMMAND_WORD = "appointment-list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all upcoming appointments in the address book.\n"
            + "Parameters: [" + PREFIX_DATE + "] [TIME]\n"
            + "DATE: Optional date filter in the format YYYY-MM-DD\n"
            + "TIME: Optional time filter in the format HHmm\n"
            + "Example: " + COMMAND_WORD + PREFIX_DATE + "2024-10-15 1430";

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

        List<AppointmentInfo> allAppointments = new ArrayList<>();

        for (Person person : personList) {
            for (Schedule schedule : person.getSchedules()) {
                LocalDateTime appointmentDateTime = LocalDateTime.parse(schedule.getDateTime(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));

                if (appointmentDateTime.isBefore(now)) {
                    continue;
                }

                if (dateFilter.isPresent() && !appointmentDateTime.toLocalDate().equals(dateFilter.get())) {
                    continue;
                }

                if (effectiveTimeFilter.isPresent() && !appointmentDateTime.toLocalTime()
                        .equals(effectiveTimeFilter.get())) {
                    continue;
                }

                allAppointments.add(new AppointmentInfo(person, schedule, appointmentDateTime));
            }
        }

        allAppointments.sort(Comparator.comparing(AppointmentInfo::getDateTime));

        String formattedAppointments = formatAppointments(allAppointments);

        return new CommandResult(String.format(MESSAGE_SUCCESS, allAppointments.size())
                + "\n" + formattedAppointments);
    }

    /**
     * Formats the list of persons with upcoming appointments into a string.
     *
     * @param appointments List of persons with upcoming appointments.
     * @return A formatted string containing the names and schedules of persons with upcoming appointments.
     */
    private String formatAppointments(List<AppointmentInfo> appointments) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a");

        return appointments.stream()
                .map(info -> String.format("%s: %s - %s",
                        info.getPerson().getName(),
                        info.getDateTime().format(outputFormatter),
                        info.getSchedule().getNotes()))
                .collect(Collectors.joining("\n"));
    }

    /**
     * Stores the information for each appointment.
     */
    private static class AppointmentInfo {
        private final Person person;
        private final Schedule schedule;
        private final LocalDateTime dateTime;

        AppointmentInfo(Person person, Schedule schedule, LocalDateTime dateTime) {
            this.person = person;
            this.schedule = schedule;
            this.dateTime = dateTime;
        }

        public Person getPerson() {
            return person;
        }

        public Schedule getSchedule() {
            return schedule;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }
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
        return dateFilter.equals(otherCommand.dateFilter)
                && timeFilter.equals(otherCommand.timeFilter);
    }

}
