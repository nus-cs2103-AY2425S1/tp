package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.UNASSIGN_EVENT_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.UNASSIGN_VOLUNTEER_PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.volunteer.Volunteer;

import java.util.List;

public class UnassignCommand extends Command {

    public static final String COMMAND_WORD = "unassign";
    private static final String MESSAGE_NOT_ASSIGNED = "Volunteer is not assigned to this event!";
    private static final String MESSAGE_SUCCESS = "Volunteer unassigned successfully!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unassigns a volunteer from an event."
            + " Parameters: "
            + UNASSIGN_VOLUNTEER_PREFIX_NAME + "Volunteer Index "
            + UNASSIGN_EVENT_PREFIX_NAME + "Event Index "
            + "Example: " + COMMAND_WORD + " "
            + UNASSIGN_VOLUNTEER_PREFIX_NAME + "2 "
            + UNASSIGN_EVENT_PREFIX_NAME + "3 ";
    // Example usage: unassign v/1 e/1
    private final Index volunteerIndex;
    private final Index eventIndex;

    public UnassignCommand(Index volunteerIndex, Index eventIndex) {
        requireAllNonNull(volunteerIndex, eventIndex);
        this.volunteerIndex = volunteerIndex;
        this.eventIndex = eventIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);

        System.out.println("We are unassigning volunteer " + this.volunteerIndex + " from event " + this.eventIndex);

        List<Volunteer> lastShownVolunteerList = model.getFilteredVolunteerList();
        List<Event> lastShownEventList = model.getFilteredEventList();

        if (volunteerIndex.getZeroBased() >= lastShownVolunteerList.size()) {
            throw new CommandException(MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
        }

        if (eventIndex.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event e = lastShownEventList.get(eventIndex.getZeroBased());
        Volunteer v = lastShownVolunteerList.get(volunteerIndex.getZeroBased());

        if (!e.getVolunteers().contains(v.getName().toString())) {
            throw new CommandException(MESSAGE_NOT_ASSIGNED);
        }

        if (!v.getEvents().contains(e.getName().toString())) {
            throw new CommandException(MESSAGE_NOT_ASSIGNED);
        }

        e.removeVolunteer(v.getName().toString());

        v.removeEvent(e.getName().toString());

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
