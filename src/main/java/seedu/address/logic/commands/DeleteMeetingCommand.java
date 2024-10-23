package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_TITLE;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
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

    public static final String MESSAGE_MEETING_NOT_FOUND = "Meeting not found with title: %1$s and date: %2$s";

    private static final Logger logger = LogsCenter.getLogger(DeleteMeetingCommand.class);

    private final MeetingTitle meetingTitle;
    private final MeetingDate meetingDate;

    /**
     * Constructs a {@code DeleteMeetingCommand} with the specified meeting title and meeting date to delete a meeting.
     *
     * @param meetingTitle The title of the meeting to delete.
     * @param meetingDate The date of the meeting to delete.
     */
    public DeleteMeetingCommand(MeetingTitle meetingTitle, MeetingDate meetingDate) {
        // Defensive programming: Ensure that neither meetingTitle nor meetingDate are null
        assert meetingTitle != null : "Meeting title should not be null";
        assert meetingDate != null : "Meeting date should not be null";

        this.meetingTitle = meetingTitle;
        this.meetingDate = meetingDate;
        logger.info("DeleteMeetingCommand created with title: " + meetingTitle + " and date: " + meetingDate);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        logger.info("Executing DeleteMeetingCommand for title: " + meetingTitle + " and date: " + meetingDate);

        // Defensive programming: Check if the meeting exists before trying to delete it
        Meeting meetingToDelete = model.getFilteredMeetingList().stream()
                .filter(meeting -> meeting.getMeetingTitle().equals(meetingTitle)
                        && meeting.getMeetingDate().equals(meetingDate))
                .findFirst()
                .orElseThrow(() -> {
                    logger.warning("Meeting not found with title: " + meetingTitle + " and date: " + meetingDate);
                    return new CommandException(String.format(MESSAGE_MEETING_NOT_FOUND, meetingTitle, meetingDate));
                });

        model.deleteMeeting(meetingToDelete);

        // Ensure the meeting was successfully deleted
        assert !model.getFilteredMeetingList().contains(meetingToDelete)
                : "Meeting should have been deleted from the address book";

        logger.info("Successfully deleted meeting: " + meetingToDelete);
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
