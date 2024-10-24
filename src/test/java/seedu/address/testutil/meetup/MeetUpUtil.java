package seedu.address.testutil.meetup;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDED_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.util.Set;

import seedu.address.logic.commands.meetup.AddCommand;
import seedu.address.logic.commands.meetup.EditCommand.EditMeetUpDescriptor;
import seedu.address.model.meetup.AddedBuyer;
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
        sb.append(PREFIX_TO + meetUp.getTo().toString() + " ");
        meetUp.getAddedBuyers().stream().forEach(
                s -> sb.append(PREFIX_ADDED_BUYER + s.addedBuyerName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditMeetUpDescriptor}'s details.
     */
    public static String getEditMeetUpDescriptorDetails(EditMeetUpDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name).append(" "));
        descriptor.getInfo().ifPresent(info -> sb.append(PREFIX_INFO).append(info).append(" "));
        descriptor.getFrom().ifPresent(from -> sb.append(PREFIX_FROM).append(from).append(" "));
        descriptor.getTo().ifPresent(to -> sb.append(PREFIX_TO).append(to).append(" "));
        if (descriptor.getAddedBuyers().isPresent()) {
            Set<AddedBuyer> addedBuyers = descriptor.getAddedBuyers().get();
            if (addedBuyers.isEmpty()) {
                sb.append(PREFIX_ADDED_BUYER);
            } else {
                addedBuyers.forEach(s -> sb.append(PREFIX_ADDED_BUYER).append(s.addedBuyerName).append(" "));
            }
        }
        return sb.toString();
    }
}
