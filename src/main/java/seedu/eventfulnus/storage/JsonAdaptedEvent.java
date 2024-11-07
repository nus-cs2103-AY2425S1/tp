package seedu.eventfulnus.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.util.Pair;
import seedu.eventfulnus.commons.exceptions.IllegalValueException;
import seedu.eventfulnus.logic.parser.ParserUtil;
import seedu.eventfulnus.model.event.Event;
import seedu.eventfulnus.model.event.Venue;
import seedu.eventfulnus.model.person.Person;
import seedu.eventfulnus.model.person.role.Faculty;
import seedu.eventfulnus.model.person.role.athlete.Sport;
import seedu.eventfulnus.model.person.role.athlete.SportString;


/**
 * Jackson-friendly version of {@link Event}.
 */
public class JsonAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String sport;
    private final List<String> teams = new ArrayList<>();
    private final String venue;
    private final String dateTime;
    private final List<JsonAdaptedPerson> participants = new ArrayList<>();


    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("sport") String sport,
                            @JsonProperty("teams") List<String> teams,
                            @JsonProperty("venue") String venue,
                            @JsonProperty("dateTime") String dateTime,
                            @JsonProperty("participants") List<JsonAdaptedPerson> participants) {
        this.sport = sport;
        if (teams != null) {
            this.teams.addAll(teams);
        }
        this.venue = venue;
        this.dateTime = dateTime;
        if (participants != null) {
            this.participants.addAll(participants);
        }
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        sport = SportString.getSportString(source.getSport());
        teams.addAll(List.of(source.getTeams().getKey().toString(), source.getTeams().getValue().toString()));
        venue = source.getVenue().toString();
        dateTime = source.getDateTimeParseString();
        participants.addAll(source.getParticipants().stream()
                .map(JsonAdaptedPerson::new)
                .toList());
    }

    @JsonProperty("sport")
    public String getSport() {
        return sport;
    }

    @JsonProperty("teams")
    public List<String> getTeams() {
        return teams;
    }

    @JsonProperty("venue")
    public String getVenue() {
        return venue;
    }

    @JsonProperty("dateTime")
    public String getDateTime() {
        return dateTime;
    }

    @JsonProperty("participants")
    public List<JsonAdaptedPerson> getParticipants() {
        return participants;
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        if (sport == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Sport.class.getSimpleName()));
        }
        final Sport eventSport = ParserUtil.parseSport(sport);

        final List<String> teams = new ArrayList<>(this.teams);
        final Pair<Faculty, Faculty> eventTeams = ParserUtil.parseTeams(teams);

        if (venue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Venue.class.getSimpleName()));
        }
        final Venue eventVenue = new Venue(venue);

        if (dateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LocalDateTime.class.getSimpleName()));
        }
        final LocalDateTime eventDateTime = ParserUtil.parseDateTime(dateTime);

        final List<Person> eventPersons = new ArrayList<>();
        for (JsonAdaptedPerson person : participants) {
            eventPersons.add(person.toModelType());
        }

        return new Event(eventSport, eventTeams, eventVenue, eventDateTime, new HashSet<>(eventPersons));
    }
}
