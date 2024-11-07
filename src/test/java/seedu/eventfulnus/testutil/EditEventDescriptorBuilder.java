package seedu.eventfulnus.testutil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.eventfulnus.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.eventfulnus.logic.parser.ParserUtil;
import seedu.eventfulnus.model.event.Event;
import seedu.eventfulnus.model.event.Venue;
import seedu.eventfulnus.model.person.Person;
import seedu.eventfulnus.model.person.role.athlete.Sport;

/**
 * A utility class to help with building {@link EditEventDescriptor} objects.
 */
public class EditEventDescriptorBuilder {
    private final EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventDescriptor descriptor) {
        this.descriptor = new EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@link Event}'s details.
     */
    public EditEventDescriptorBuilder(Event event) {
        descriptor = new EditEventDescriptor();
        descriptor.setSport(event.getSport());
        descriptor.setTeams(event.getTeams());
        descriptor.setVenue(event.getVenue());
        descriptor.setDateTime(event.getDateTime());
        descriptor.setParticipants(event.getParticipants());
    }

    /**
     * Sets the {@link Sport} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withSport(String sport) {
        descriptor.setSport(ParserUtil.parseSport(sport));
        return this;
    }

    /**
     * Sets the {@code teams} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withTeams(String team1, String team2) {
        descriptor.setTeams(ParserUtil.parseTeams(List.of(team1, team2)));
        return this;
    }

    /**
     * Sets the {@link Venue} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withVenue(String venue) {
        descriptor.setVenue(new Venue(venue));
        return this;
    }

    /**
     * Sets the {@code dateTime} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDateTime(String dateTime) {
        descriptor.setDateTime(ParserUtil.parseDateTime(dateTime));
        return this;
    }

    /**
     * Sets the {@code participants} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withParticipants(String... participants) {
        Set<Person> participantSet = Stream.of(participants)
                .map(ParserUtil::parseParticipant)
                .collect(Collectors.toSet());
        descriptor.setParticipants(participantSet);
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }
}
