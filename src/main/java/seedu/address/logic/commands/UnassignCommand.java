package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.UNASSIGN_EVENT_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.UNASSIGN_VOLUNTEER_PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

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
        requireNonNull(model);
        model.unassignVolunteer(volunteerIndex, eventIndex);
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
