package seedu.address.logic.commands;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.Meeting;

/**
 * Adds a new schedule to the specified person in the address book.
 * The schedule contains an event name, date, and time.
 */
public class AddScheduleCommand extends Command {
    public static final String COMMAND_WORD = "add-schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a schedule to a contact. "
            + "Parameters: c/CONTACT_INDEX n/NAME d/DATE t/TIME\n"
            + "Example: " + COMMAND_WORD + " c/1 n/Dinner d/10-10-2024 t/1800";

    public static final String MESSAGE_SUCCESS = "New schedule added: %1$s";
    public static final String MESSAGE_INVALID_DATE = "Date must be in the format DD-MM-YYYY.";
    public static final String MESSAGE_INVALID_TIME = "Time must be in the format HHmm (24-hour).";
    public static final String MESSAGE_DUPLICATE_SCHEDULE = "This schedule conflicts with an existing schedule.";

    private final int contactIndex;
    private final String name;
    private final LocalDate date;
    private final LocalTime time;

    /**
     * Creates an AddScheduleCommand to add a schedule for a specified person.
     * The schedule includes an event name, date, and time.
     *
     * @param contactIndex The index of the person in the filtered person list.
     * @param name         The name or description of the event.
     * @param date         The date of the event in LocalDate format.
     * @param time         The time of the event in LocalTime format.
     */
    public AddScheduleCommand(int contactIndex, String name, LocalDate date, LocalTime time) {
        this.contactIndex = contactIndex;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        ArrayList<Person> personInvolved = new ArrayList<>();

        if (contactIndex < 0 || contactIndex >= lastShownList.size()) {
            throw new CommandException("The contact index provided is invalid.");
        }

        //Supposed to do in Parser Class for Add Schedule but Down here 1st
        Meeting recordMeeting = new Meeting((Arrays.asList(contactIndex)), name, date, time);

        // Check for duplicate schedule or time conflict here already, just click in hopefully works
        model.addMeeting(recordMeeting);

        return new CommandResult(String.format(MESSAGE_SUCCESS, name + " on " + date + " at " + time));
    }

}
