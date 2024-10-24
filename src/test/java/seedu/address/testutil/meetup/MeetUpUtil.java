package seedu.address.testutil.meetup;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import seedu.address.logic.commands.meetup.AddCommand;
import seedu.address.logic.commands.meetup.EditCommand.EditMeetUpDescriptor;
import seedu.address.model.meetup.MeetUp;

/**
 * A utility class for MeetUp.
 */
public class MeetUpUtil {

    /**
     * Returns an add command string for adding the {@code meetUp}.
     */
    public static String getAddMeetUpCommand(MeetUp meetUp) {
        return AddCommand.COMMAND_WORD + " " + getMeetUpDetails(meetUp);
    }

    /**
     * Returns the part of command string for the given {@code meetUp}'s details.
     */
    public static String getMeetUpDetails(MeetUp meetUp) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + meetUp.getName().toString() + " ");
        sb.append(PREFIX_INFO + meetUp.getInfo().toString() + " ");
        sb.append(PREFIX_FROM + meetUp.getFrom().toString() + " ");
        sb.append(PREFIX_TO + meetUp.getTo().toString());
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditMeetUpDescriptor}'s details.
     */
    public static String getEditMeetUpDescriptorDetails(EditMeetUpDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getMeetUpName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.toString()).append(" "));
        descriptor.getMeetUpInfo().ifPresent(info -> sb.append(PREFIX_INFO).append(info.toString()).append(" "));
        descriptor.getMeetUpFrom().ifPresent(from -> sb.append(PREFIX_FROM).append(from.toString()).append(" "));
        descriptor.getMeetUpTo().ifPresent(to -> sb.append(PREFIX_TO).append(to.toString()).append(" "));
        return sb.toString();
    }
}
