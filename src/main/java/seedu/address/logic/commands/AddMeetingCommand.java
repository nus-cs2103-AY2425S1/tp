package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_TITLE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meeting.Meeting;

/**
 * Adds a {@code Meeting} to the meeting book.
 */
public class AddMeetingCommand extends Command {

    public static final String COMMAND_WORD = "addmeeting";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meeting to the meeting log. "
            + "Parameters: "
            + PREFIX_MEETING_TITLE + "TITLE "
            + PREFIX_MEETING_DATE + "DATE \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MEETING_TITLE + "Meeting 1 "
            + PREFIX_MEETING_DATE + "28-11-2024 ";

    public static final String MESSAGE_SUCCESS = "New meeting added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEETING = "There is already a meeting of the same title "
            + "scheduled on this date. Please change either the meeting title or date.";

    private final Meeting toAdd;

    /**
     * Creates an AddMeetingCommand to add the specified {@code Meeting}
     */
    public AddMeetingCommand(Meeting meeting) {
        requireNonNull(meeting);
        toAdd = meeting;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasMeeting(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEETING);
        }

        model.addMeeting(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddMeetingCommand otherAddCommand = (AddMeetingCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
