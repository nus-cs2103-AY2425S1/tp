package seedu.eventfulnus.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_PARTICIPANT;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_SPORT;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_TEAM;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.eventfulnus.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javafx.util.Pair;
import seedu.eventfulnus.commons.core.index.Index;
import seedu.eventfulnus.commons.util.CollectionUtil;
import seedu.eventfulnus.commons.util.ToStringBuilder;
import seedu.eventfulnus.logic.Messages;
import seedu.eventfulnus.logic.commands.exceptions.CommandException;
import seedu.eventfulnus.model.Model;
import seedu.eventfulnus.model.event.Event;
import seedu.eventfulnus.model.event.Venue;
import seedu.eventfulnus.model.person.Person;
import seedu.eventfulnus.model.person.role.Faculty;
import seedu.eventfulnus.model.person.role.athlete.Sport;

/**
 * Edits the details of an existing {@link Event} in the address book.
 */
public class EditEventCommand extends Command {
    public static final String COMMAND_WORD = "editevent";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
                                               + "by the index number used in the displayed event list. "
                                               + "Existing values will be overwritten by the input values.\n"
                                               + "Parameters: INDEX (must be a positive integer) "
                                               + "[" + PREFIX_NAME + "NAME\n]"
                                               + "Example: " + COMMAND_WORD + " 1 "
                                               + PREFIX_SPORT + "Chess"
                                               + PREFIX_TEAM + "COM"
                                               + PREFIX_TEAM + "FASS"
                                               + PREFIX_VENUE + "Stadium"
                                               + PREFIX_PARTICIPANT + "Alice";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book.";

    private final Index index;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param index of the event in the filtered event list to edit.
     * @param editEventDescriptor details to edit the event with.
     */
    public EditEventCommand(Index index, EditEventDescriptor editEventDescriptor) {
        requireNonNull(index);
        requireNonNull(editEventDescriptor);

        this.index = index;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = lastShownList.get(index.getZeroBased());
        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);

        if (!eventToEdit.equals(editedEvent) && model.hasEvent(editedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.setEvent(eventToEdit, editedEvent);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, Messages.formatEvent(editedEvent)));
    }

    /**
     * Creates and returns a {@link Event} with the details of {@code eventToEdit}.
     */
    private static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        Sport updatedSport = editEventDescriptor.getSport().orElse(eventToEdit.getSport());
        Pair<Faculty, Faculty> updatedTeams = editEventDescriptor.getTeams().orElse(eventToEdit.getTeams());
        Venue updatedVenue = editEventDescriptor.getVenue().orElse(eventToEdit.getVenue());
        LocalDateTime updatedDateTime = editEventDescriptor.getDateTime().orElse(eventToEdit.getDateTime());
        Set<Person> updatedParticipants = editEventDescriptor.getParticipants().orElse(eventToEdit.getParticipants());

        return new Event(updatedSport, updatedTeams, updatedVenue, updatedDateTime, updatedParticipants);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditEventCommand otherEditEventCommand)) {
            return false;
        }

        return index.equals(otherEditEventCommand.index)
                && editEventDescriptor.equals(otherEditEventCommand.editEventDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editEventDescriptor", editEventDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the {@link Event} with. Each non-empty field value will replace the
     * corresponding field value of the {@link Event}.
     */
    public static class EditEventDescriptor {
        private Sport sport;
        private Pair<Faculty, Faculty> teams;
        private Venue venue;
        private LocalDateTime dateTime;
        private Set<Person> participants;

        public EditEventDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setSport(toCopy.sport);
            setTeams(toCopy.teams);
            setVenue(toCopy.venue);
            setDateTime(toCopy.dateTime);
            setParticipants(toCopy.participants);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(sport, teams, venue, dateTime, participants);
        }

        public void setSport(Sport sport) {
            this.sport = sport;
        }

        public Optional<Sport> getSport() {
            return Optional.ofNullable(sport);
        }

        /**
         * Sets the teams of the {@link Event} that we are building.
         * A defensive copy of {@code teams} is used internally.
         */
        public void setTeams(Pair<Faculty, Faculty> teams) {
            this.teams = teams != null ? new Pair<>(teams.getKey(), teams.getValue()) : null;
        }

        /**
         * Returns an {@link Optional} of the teams of the {@link Event} that we are building.
         */
        public Optional<Pair<Faculty, Faculty>> getTeams() {
            return teams != null ? Optional.of(new Pair<>(teams.getKey(), teams.getValue())) : Optional.empty();
        }

        public void setVenue(Venue venue) {
            this.venue = venue;
        }

        public Optional<Venue> getVenue() {
            return Optional.ofNullable(venue);
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }

        public Optional<LocalDateTime> getDateTime() {
            return Optional.ofNullable(dateTime);
        }

        /**
         * Sets the {@code participants} of the {@link Event} that we are building.
         * A defensive copy of {@code participants} is used internally.
         */
        public void setParticipants(Set<Person> participants) {
            this.participants = participants != null ? new HashSet<>(participants) : null;
        }


        /**
         * Returns an unmodifiable {@link Set} of {@link Person} of the {@link Event} that we are building,
         * which throws {@link UnsupportedOperationException} if modification is attempted.
         * Returns {@code Optional#empty()} if {@code participants} is null.
         */
        public Optional<Set<Person>> getParticipants() {
            return participants != null ? Optional.of(Collections.unmodifiableSet(participants)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEventDescriptor otherEditEventDescriptor)) {
                return false;
            }

            return Objects.equals(sport, otherEditEventDescriptor.sport)
                    && Objects.equals(teams, otherEditEventDescriptor.teams)
                    && Objects.equals(venue, otherEditEventDescriptor.venue)
                    && Objects.equals(dateTime, otherEditEventDescriptor.dateTime)
                    && Objects.equals(participants, otherEditEventDescriptor.participants);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("sport", sport)
                    .add("teams", teams)
                    .add("venue", venue)
                    .add("dateTime", dateTime)
                    .add("participants", participants)
                    .toString();
        }
    }
}
