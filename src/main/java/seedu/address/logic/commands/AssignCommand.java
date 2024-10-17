package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.ASSIGN_EVENT_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.ASSIGN_VOLUNTEER_PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.volunteer.Volunteer;

/**
 * Adds a volunteer to the system.
 */
public class AssignCommand extends Command {
    public static final String COMMAND_WORD = "assign";
    private static final String MESSAGE_DUPLICATE_ASSIGN = "Already assigned!";
    private static final String MESSAGE_SUCCESS = "Volunteer assigned successfully!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a volunteer to an event."
            + "Parameters: "
            + ASSIGN_VOLUNTEER_PREFIX_NAME + "Volunteer ID "
            + ASSIGN_EVENT_PREFIX_NAME + "Event ID "
            + "Example: " + COMMAND_WORD + " "
            + ASSIGN_VOLUNTEER_PREFIX_NAME + "2 "
            + ASSIGN_EVENT_PREFIX_NAME + "3 ";

    /**
     * Creates a {@code VolunteerAddCommand} to add the specified {@code Volunteer}.
     *
     * @param volunteer The volunteer to be added.
     */
    private final int volunteerId;
    private final int eventId;

    /**
     * Constructs a {@code VolunteerAddCommand} that adds the specified {@code Volunteer} to the system.
     *
     * @param volunteerId The volunteer to be added. Must not be null.
     */
    public AssignCommand(int volunteerId, int eventId) {
        requireAllNonNull(volunteerId, eventId);
        this.volunteerId = volunteerId;
        this.eventId = eventId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);

        System.out.println("We are assigning volunteer " + this.volunteerId + " to event " + this.eventId);

        Event e = model.getEvent(eventId);
        Volunteer v = model.getVolunteer(volunteerId);

        // Check if the volunteer is already assigned to the event
        if (e.getVolunteers().contains(v.getName().toString())) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGN);
        }

        // Check if the event is already in the volunteer's list
        if (v.getEvents().contains(e.getName().toString())) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGN);
        }

        e.addVolunteer(v.getName().toString());
        v.addEvent(e.getName().toString());

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
