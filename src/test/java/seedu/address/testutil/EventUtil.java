package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.model.event.Event;

/**
 * A utility class for Event.
 */
public class EventUtil {

    /**
     * Returns an add command string for adding the {@code event}.
     */
    public static String getAddEventCommand(Event event) {
        return AddEventCommand.COMMAND_WORD + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    public static String getEventDetails(Event event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_EVENT_NAME + event.getEventName().eventName + " ");
        sb.append(PREFIX_EVENT_DESCRIPTION + event.getEventDescription().eventDescription + " ");
        sb.append(PREFIX_EVENT_START_DATE + event.getEventStartDate().toString() + " ");
        sb.append(PREFIX_EVENT_END_DATE + event.getEventEndDate().toString() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code editEventDescriptor}'s details.
     */
    public static String getEditEventDetails(EditEventDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_EVENT_NAME).append(name.eventName).append(" "));
        descriptor.getDescription().ifPresent(description -> sb.append(PREFIX_EVENT_DESCRIPTION)
                .append(description.eventDescription).append(" "));
        descriptor.getDuration().ifPresent(duration -> {
            sb.append(PREFIX_EVENT_START_DATE).append(duration.getStartDate().toString()).append(" ");
            sb.append(PREFIX_EVENT_END_DATE).append(duration.getEndDate().toString()).append(" ");
        });
        return sb.toString();
    }
}
