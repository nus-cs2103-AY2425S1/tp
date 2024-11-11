package seedu.eventtory.testutil;

import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.eventtory.logic.commands.CreateEventCommand;
import seedu.eventtory.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.eventtory.model.commons.tag.Tag;
import seedu.eventtory.model.event.Event;

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
        event.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
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
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
