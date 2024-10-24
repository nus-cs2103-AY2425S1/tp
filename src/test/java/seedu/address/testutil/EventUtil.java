package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.CreateEventCommand;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.model.event.Event;

/**
 * A utility class for Event.
 */
public class EventUtil {

    /**
     * Returns a create event command string for adding the {@code event}.
     */
    public static String getCreateEventCommand(Event event) {
        return CreateEventCommand.COMMAND_WORD + " " + PREFIX_EVENT + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code vendor}'s details.
     */
    public static String getEventDetails(Event event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + event.getName().fullName + " ");
        sb.append(PREFIX_DATE + event.getDate().toString() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given
     * {@code EditEventDescriptor}'s details.
     */
    public static String getEditEventDescriptorDetails(EditEventDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date.toString()).append(" "));
        return sb.toString();
    }
}
