package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_GENERIC_INDEX_OUT_OF_BOUNDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UID;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.model.Model;
import seedu.address.model.person.FieldContainsKeywordsPredicate;

/**
 * Lists all contacts of a meeting.
 */
public class MeetingContactsCommand extends Command {
    public static final String COMMAND_WORD = "meeting-contacts";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View contacts of this meeting.\n"
            + "Parameters: INDEX"
            + "Example: " + COMMAND_WORD + " 2";

    private final Index targetMeetingIndex;

    public MeetingContactsCommand(Index targetMeetingIndex) {
        this.targetMeetingIndex = targetMeetingIndex;
    }

    /**
     * Uses {@code FindCommand} to find the contacts of a meeting.
     *
     * @param model {@code Model} which the command should operate on.
     * @return the result of the command
     * @throws CommandException If the index is out of bounds.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // check if the index is out of bounds
        if (model.getWeeklySchedule().size() <= targetMeetingIndex.getZeroBased()
                || targetMeetingIndex.getZeroBased() < 0) {
            throw new CommandException(String.format(MESSAGE_GENERIC_INDEX_OUT_OF_BOUNDS,
                    MESSAGE_USAGE));
        }

        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        // assert that meeting has contacts
        // if contacts field becomes optional remove this assertion
        assert(!model.getMeeting(targetMeetingIndex).getContactUids().isEmpty());
        model.getMeeting(targetMeetingIndex).getContactUids().stream()
            .forEach(uid -> argumentMultimap.put(PREFIX_UID, uid.toString()));
        return new FindCommand(new FieldContainsKeywordsPredicate(argumentMultimap))
                .execute(model);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof MeetingContactsCommand
                && targetMeetingIndex.equals(((MeetingContactsCommand) other).targetMeetingIndex));
    }

    @Override
    public String toString() {
        return "MeetingContactsCommand{" + "targetMeetingIndex=" + targetMeetingIndex + '}';
    }
}
