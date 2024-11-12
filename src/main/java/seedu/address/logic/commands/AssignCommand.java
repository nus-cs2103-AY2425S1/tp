package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.ASSIGN_EVENT_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.ASSIGN_VOLUNTEER_PREFIX_NAME;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.exceptions.DuplicateAssignException;
import seedu.address.model.exceptions.OverlappingAssignException;
import seedu.address.model.exceptions.VolunteerNotAvailableException;
import seedu.address.model.volunteer.Volunteer;

/**
 * Assigns a volunteer from an event.
 * This command assigns a volunteer from an event in the system using their respective indices.
 */
public class AssignCommand extends Command {
    public static final String COMMAND_WORD = "assign";
    private static final String MESSAGE_DUPLICATE_ASSIGN = "Already assigned!";
    private static final String MESSAGE_SUCCESS = "Volunteer assigned successfully!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns a volunteer to an event.\n"
            + "Parameters: "
            + ASSIGN_VOLUNTEER_PREFIX_NAME + "VOLUNTEER_INDEX "
            + ASSIGN_EVENT_PREFIX_NAME + "EVENT_INDEX "
            + "Example: " + COMMAND_WORD + " "
            + ASSIGN_VOLUNTEER_PREFIX_NAME + "2 "
            + ASSIGN_EVENT_PREFIX_NAME + "3 ";

    private final Index volunteerIndex;
    private final Index eventIndex;

    /**
     * Creates an AssignCommand to assign a volunteer from an event.
     *
     * @param volunteerIndex The index of the volunteer in the currently displayed list.
     * @param eventIndex The index of the event in the currently displayed list.
     */
    public AssignCommand(Index volunteerIndex, Index eventIndex) {
        requireAllNonNull(volunteerIndex, eventIndex);
        this.volunteerIndex = volunteerIndex;
        this.eventIndex = eventIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireAllNonNull(model);


        Logger.getLogger("AssignCommand").fine("Assigning volunteer " + this.volunteerIndex
                + " to event " + this.eventIndex);

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
            model.assignVolunteerToEvent(v, e);
        } catch (DuplicateAssignException exception) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGN);
        } catch (OverlappingAssignException | VolunteerNotAvailableException exception) {
            throw new CommandException(exception.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof AssignCommand) {
            AssignCommand otherAssignCommand = (AssignCommand) other;
            return volunteerIndex.equals(otherAssignCommand.volunteerIndex)
                    && eventIndex.equals(otherAssignCommand.eventIndex);
        } else {
            return false;
        }
    }
}
