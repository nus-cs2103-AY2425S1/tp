package seedu.eventfulnus.testutil;

import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_PARTICIPANT;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_SPORT;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_TEAM;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Set;

import seedu.eventfulnus.logic.commands.AddEventCommand;
import seedu.eventfulnus.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.eventfulnus.model.event.Event;
import seedu.eventfulnus.model.person.Person;
import seedu.eventfulnus.model.person.role.athlete.SportString;

/**
 * A utility class for {@link Event}.
 */
public class EventUtil {
    public static String getAddEventCommand(Event event) {
        return AddEventCommand.COMMAND_WORD + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@link Event}'s details.
     */
    public static String getEventDetails(Event event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_SPORT).append(SportString.getSportString(event.getSport())).append(" ");
        sb.append(PREFIX_TEAM).append(event.getFirstTeam()).append(" ");
        sb.append(PREFIX_TEAM).append(event.getSecondTeam()).append(" ");
        sb.append(PREFIX_VENUE).append(event.getVenue()).append(" ");
        sb.append(PREFIX_DATE).append(event.getDateTimeParseString()).append(" ");
        event.getParticipants().forEach(
                p -> sb.append(PREFIX_PARTICIPANT).append(p.getName()).append(" ")
        );

        return sb.toString();
    }

    public static String getEditEventDescriptorDetails(EditEventDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getSport().ifPresent(sport -> sb.append(PREFIX_SPORT).append(sport).append(" "));

        descriptor.getTeams().ifPresent(teams -> {
            sb.append(PREFIX_TEAM).append(teams.getKey()).append(" ");
            sb.append(PREFIX_TEAM).append(teams.getValue()).append(" ");
        });

        descriptor.getVenue().ifPresent(venue -> sb.append(PREFIX_VENUE).append(venue).append(" "));

        descriptor.getDateTime().ifPresent(dateTime -> sb.append(PREFIX_DATE).append(
                dateTime.format(Event.DATE_TIME_PARSE_FORMATTER)).append(" "));

        if (descriptor.getParticipants().isPresent()) {
            Set<Person> participants = descriptor.getParticipants().get();
            if (participants.isEmpty()) {
                sb.append(PREFIX_PARTICIPANT);
            } else {
                participants.forEach(s -> sb.append(PREFIX_PARTICIPANT).append(s.getName()).append(" "));
            }
        }

        return sb.toString();
    }
}
