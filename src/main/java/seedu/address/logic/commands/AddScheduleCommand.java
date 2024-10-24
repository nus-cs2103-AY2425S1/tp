package seedu.address.logic.commands;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Meeting;

/**
 * Adds a new schedule to the specified persons in the address book.
 * The schedule contains an event name, date, and time.
 */
public class AddScheduleCommand extends Command {
    public static final String COMMAND_WORD = "add-schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a schedule to contacts. "
            + "Parameters: c/CONTACT_INDEXES n/NAME d/DATE t/TIME\n"
            + "Example: " + COMMAND_WORD + " c/1 2 n/Dinner d/10-10-2024 t/1800";

    public static final String MESSAGE_SUCCESS = "New schedule added: %1$s";
    public static final String MESSAGE_INVALID_CONTACT_INDEX = "One or more contact indices provided are invalid.";
    public static final String MESSAGE_INVALID_DATE = "Date must be in the format DD-MM-YYYY.";
    public static final String MESSAGE_INVALID_TIME = "Time must be in the format HHmm (24-hour).";
    public static final String MESSAGE_DUPLICATE_SCHEDULE = "This schedule conflicts with an existing schedule.";

    private final List<Index> contactIndexes;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    /**
     * Creates an AddScheduleCommand to add a schedule for specified persons.
     * The schedule includes an event name, date, and time.
     *
     * @param contactIndexes    The indices of the persons in the filtered person list.
     * @param name              The name or description of the event.
     * @param date              The date of the event in LocalDate format.
     * @param time              The time of the event in LocalTime format.
     */
    public AddScheduleCommand(List<Index> contactIndexes, String name, LocalDate date, LocalTime time) {
        this.contactIndexes = contactIndexes;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        assert model != null : "Model cannot be null";

        List<Person> lastShownList = model.getFilteredPersonList();
        assert lastShownList != null && !lastShownList.isEmpty() : "Person list cannot be null or empty";

        // Check if all contact indices are valid
        for (Index i : contactIndexes) {
            if (i.getZeroBased() >= lastShownList.size() || i.getZeroBased() < 0) {
                throw new CommandException("One or more contact indexes are invalid.");
            }
        }

        // Retrieve the UUIDs of the persons involved
        List<UUID> contactsList = contactIndexes.stream()
                .map(index -> lastShownList.get(index.getZeroBased()).getUid())
                .collect(Collectors.toList());

        // Create the new meeting
        Meeting newMeeting = new Meeting(contactsList, name, date, time);

        // Check for duplicate schedule or conflict
        if (model.hasMeeting(newMeeting)) {
            throw new CommandException(MESSAGE_DUPLICATE_SCHEDULE);
        }

        // Add the meeting to the model if no conflict
        model.addMeeting(newMeeting);

        return new CommandResult(String.format(MESSAGE_SUCCESS, name + " on " + date + " at " + time));
    }
}
