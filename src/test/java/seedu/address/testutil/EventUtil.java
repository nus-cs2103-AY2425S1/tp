package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.CreateVendorCommand;
import seedu.address.model.event.Event;

/**
 * A utility class for Event.
 */
public class EventUtil {

    /**
     * Returns a create event command string for adding the {@code event}.
     */
    public static String getCreateEventCommand(Event event) {
        return CreateVendorCommand.COMMAND_WORD + " " + PREFIX_EVENT + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code vendor}'s details.
     */
    public static String getEventDetails(Event event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + event.getName().fullName + " ");
        sb.append(PREFIX_DATE + event.getDate().toString() + " ");
        event.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        return sb.toString();
    }

}
