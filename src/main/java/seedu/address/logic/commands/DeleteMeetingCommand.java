package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Deletes a meeting from an Udder's list of meetings.
 */
public class DeleteMeetingCommand extends Command {

    public static final String COMMAND_WORD = "deletem";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the meeting identified by the index number used in the command result panel.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MEETING_SUCCESS = "Deleted Meeting: %1$s";

    private final Index targetIndex;

    public DeleteMeetingCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (targetIndex.getZeroBased() >= model.getMeetingSize()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEETING_INDEX);
        }


        Meeting toDelete = model.getMeeting(targetIndex.getZeroBased());
        Name target = toDelete.getPersonToMeet();
        Person personToDeleteMeeting = null;
        for (Person person : model.getFilteredPersonList()) {
            if (person.getName().equals(target)) {
                personToDeleteMeeting = person;
            }
        }

        if (personToDeleteMeeting == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEETING_INDEX);
        }

        model.deleteMeeting(personToDeleteMeeting, toDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MEETING_SUCCESS, Messages.format(toDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteMeetingCommand otherDeleteMeetingCommand = (DeleteMeetingCommand) other;
        return targetIndex.equals(otherDeleteMeetingCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
