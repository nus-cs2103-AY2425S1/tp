package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_TITLE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingDate;
import seedu.address.model.meeting.MeetingTitle;

/**
 * Deletes a meeting using its meetingTitle and meetingDate.
 */
public class DeleteMeetingCommand extends Command {

    public static final String COMMAND_WORD = "deletemeeting";

    public static final String MESSAGE_USAGE = String.format(
            "%s: Deletes the meeting identified by its title and date."
                    + "\nParameters: %sTITLE %sDATE\nRestrictions: "
                    + "Title has to be a valid String and Date has to be in the format dd-mm-yyyy",
            COMMAND_WORD,
            PREFIX_MEETING_TITLE,
            PREFIX_MEETING_DATE
    );

    public static final String MESSAGE_DELETE_MEETING_SUCCESS = "Deleted meeting: %1$s";

    private final MeetingTitle meetingTitle;
    private final MeetingDate meetingDate;

    /**
     * Constructs a {@code DeleteMeetingCommand} with the specified meeting title and meeting date to delete a meeting.
     *
     * @param meetingTitle The postal code of the property to delete.
     * @param meetingDate The unit number of the property to delete.
     */
    public DeleteMeetingCommand(MeetingTitle meetingTitle, MeetingDate meetingDate) {
        this.meetingTitle = meetingTitle;
        this.meetingDate = meetingDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Meeting meetingToDelete = model.getFilteredMeetingList().stream()
                .filter(meeting -> meeting.getMeetingTitle().equals(meetingTitle)
                        && meeting.getMeetingDate().equals(meetingDate))
                .findFirst().orElseThrow(() ->
                        new CommandException(String.format("Meeting not found. ", meetingTitle,
                                meetingDate)));

        model.deleteMeeting(meetingToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MEETING_SUCCESS, Messages.format(meetingToDelete)));
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteMeetingCommand)) {
            return false;
        }

        DeleteMeetingCommand otherDeleteMeetingCommand = (DeleteMeetingCommand) other;
        return meetingTitle.equals(otherDeleteMeetingCommand.meetingTitle)
                && meetingDate.equals(otherDeleteMeetingCommand.meetingDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("meetingTitle", meetingTitle)
                .add("meetingDate", meetingDate)
                .toString();
    }
}
