package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.UNASSIGN_EVENT_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.UNASSIGN_VOLUNTEER_PREFIX_NAME;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.exceptions.NotAssignedException;
import seedu.address.model.volunteer.Volunteer;

/**
 * Unassigns a volunteer from an event.
 * This command unassigns a volunteer from an event in the system using their respective indices.
 */
public class UnassignCommand extends Command {

    public static final String COMMAND_WORD = "unassign";
    public static final String MESSAGE_NOT_ASSIGNED = "Volunteer is not assigned to this event!";
    private static final String MESSAGE_SUCCESS = "Volunteer unassigned successfully!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unassigns a volunteer from an event."
            + " Parameters: "
            + UNASSIGN_VOLUNTEER_PREFIX_NAME + "Volunteer Index "
            + UNASSIGN_EVENT_PREFIX_NAME + "Event Index "
            + "Example: " + COMMAND_WORD + " "
            + UNASSIGN_VOLUNTEER_PREFIX_NAME + "2 "
            + UNASSIGN_EVENT_PREFIX_NAME + "3 ";
    // Example usage: unassign v/1 e/2

    private final Index volunteerIndex;
    private final Index eventIndex;

    /**
     * Creates an UnassignCommand to unassign a volunteer from an event.
     *
     * @param volunteerIndex The index of the volunteer in the currently displayed list.
     * @param eventIndex The index of the event in the currently displayed list.
     */
    public UnassignCommand(Index volunteerIndex, Index eventIndex) {
        requireAllNonNull(volunteerIndex, eventIndex);
        this.volunteerIndex = volunteerIndex;
        this.eventIndex = eventIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);

        System.out.println("We are assigning volunteer " + this.volunteerIndex + " to event " + this.eventIndex);

        List<Volunteer> lastShownVolunteerList = model.getFilteredVolunteerList();
        List<Event> lastShownEventList = model.getFilteredEventList();

        if (volunteerIndex.getZeroBased() >= lastShownVolunteerList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
        }

        if (eventIndex.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event e = lastShownEventList.get(eventIndex.getZeroBased());
        Volunteer v = lastShownVolunteerList.get(volunteerIndex.getZeroBased());

        try {
            model.unassignVolunteerFromEvent(v, e);
        } catch (NotAssignedException exception) {
            throw new CommandException(MESSAGE_NOT_ASSIGNED);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
