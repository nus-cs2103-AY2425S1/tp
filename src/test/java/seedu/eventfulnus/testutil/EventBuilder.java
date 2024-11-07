package seedu.eventfulnus.testutil;

import static seedu.eventfulnus.testutil.TypicalPersons.getTypicalPersons;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.util.Pair;
import seedu.eventfulnus.logic.parser.ParserUtil;
import seedu.eventfulnus.model.event.Event;
import seedu.eventfulnus.model.event.Venue;
import seedu.eventfulnus.model.person.Person;
import seedu.eventfulnus.model.person.role.Faculty;
import seedu.eventfulnus.model.person.role.athlete.Sport;

/**
 * A utility class to help with building {@link Event} objects.
 */
public class EventBuilder {
    public static final Sport DEFAULT_SPORT = Sport.CHESS;
    public static final Venue DEFAULT_VENUE = new Venue("UTown Sports Hall");
    public static final Pair<Faculty, Faculty> DEFAULT_TEAMS = new Pair<>(Faculty.SCI, Faculty.FASS);
    public static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.parse("2021-10-10T10:00");

    private Sport sport;
    private Pair<Faculty, Faculty> teams;
    private Venue venue;
    private LocalDateTime dateTime;
    private Set<Person> participants;

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        sport = DEFAULT_SPORT;
        venue = DEFAULT_VENUE;
        teams = DEFAULT_TEAMS;
        dateTime = DEFAULT_DATE_TIME;
        participants = new HashSet<>();
        ParserUtil.setFilteredPersonList(new FilteredList<>(FXCollections.observableArrayList(getTypicalPersons())));
    }

    /**
     * Initializes the {@code EventBuilder} with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        sport = eventToCopy.getSport();
        teams = eventToCopy.getTeams();
        venue = eventToCopy.getVenue();
        dateTime = eventToCopy.getDateTime();
        participants = new HashSet<>(eventToCopy.getParticipants());
    }

    /**
     * Sets the {@link Sport} of the {@link Event} that we are building.
     */
    public EventBuilder withSport(String sport) {
        this.sport = ParserUtil.parseSport(sport);
        return this;
    }

    /**
     * Sets the teams of the {@link Event} that we are building.
     */
    public EventBuilder withTeams(String team1, String team2) {
        this.teams = new Pair<>(ParserUtil.parseFaculty(team1), ParserUtil.parseFaculty(team2));
        return this;
    }

    /**
     * Sets the {@link Venue} of the {@link Event} that we are building.
     */
    public EventBuilder withVenue(String venue) {
        this.venue = new Venue(venue);
        return this;
    }

    /**
     * Sets the {@code dateTime} of the {@link Event} that we are building.
     */
    public EventBuilder withDateTime(String dateTimeString) {
        this.dateTime = ParserUtil.parseDateTime(dateTimeString);
        return this;
    }

    /**
     * Parses the {@code participants} into a {@link Set}<{@link Person}>
     * and assigns it to the {@link Event} that we are building.
     */
    public EventBuilder withParticipants(String ... participants) {
        List<String> participantsList = new ArrayList<>(Arrays.asList(participants));
        this.participants = ParserUtil.parseParticipants(participantsList);
        return this;
    }

    public Event build() {
        return new Event(sport, teams, venue, dateTime, participants);
    }
}
