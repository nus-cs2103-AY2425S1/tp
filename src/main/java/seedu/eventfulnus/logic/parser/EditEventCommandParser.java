package seedu.eventfulnus.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.eventfulnus.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_PARTICIPANT;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_SPORT;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_TEAM;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.util.Pair;
import seedu.eventfulnus.commons.core.index.Index;
import seedu.eventfulnus.logic.commands.EditEventCommand;
import seedu.eventfulnus.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.eventfulnus.logic.parser.exceptions.ParseException;
import seedu.eventfulnus.model.person.Person;
import seedu.eventfulnus.model.person.role.Faculty;

/**
 * Parses input arguments and creates a new {@link EditEventCommand} object.
 */
public class EditEventCommandParser implements Parser<EditEventCommand> {
    @Override
    public EditEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SPORT, PREFIX_TEAM, PREFIX_VENUE,
                PREFIX_DATE, PREFIX_PARTICIPANT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SPORT, PREFIX_VENUE, PREFIX_DATE);

        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();

        if (argMultimap.getValue(PREFIX_SPORT).isPresent()) {
            editEventDescriptor.setSport(ParserUtil.parseSport(argMultimap.getValue(PREFIX_SPORT).get()));
        }
        if (argMultimap.getValue(PREFIX_VENUE).isPresent()) {
            editEventDescriptor.setVenue(ParserUtil.parseVenue(argMultimap.getValue(PREFIX_VENUE).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editEventDescriptor.setDateTime(ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATE).get()));
        }
        parseTeamsForEdit(argMultimap.getAllValues(PREFIX_TEAM)).ifPresent(editEventDescriptor::setTeams);
        parseParticipantsForEdit(argMultimap.getAllValues(PREFIX_PARTICIPANT))
                .ifPresent(editEventDescriptor::setParticipants);

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEventCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEventCommand(index, editEventDescriptor);
    }

    /**
     * Parses {@code Collection<String> teams} into a {@code Set<Team>} if {@code teams} is non-empty.
     * If {@code teams} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Team>} containing zero teams.
     */
    private Optional<Pair<Faculty, Faculty>> parseTeamsForEdit(List<String> teams) throws ParseException {
        assert teams != null;

        if (teams.isEmpty()) {
            return Optional.empty();
        }
        List<String> teamSet = teams.size() == 1 && teams.contains("") ? Collections.emptyList() : teams;
        return Optional.of(ParserUtil.parseTeams(teamSet));
    }

    /**
     * Parses {@code Collection<String> participants} into a {@code Set<Person>} if {@code participants} is non-empty.
     * If {@code participants} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Person>} containing zero participants.
     */
    private Optional<Set<Person>> parseParticipantsForEdit(Collection<String> participants)
            throws ParseException {
        assert participants != null;

        if (participants.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> participantSet = participants.size() == 1 && participants.contains("")
                ? Collections.emptySet()
                : participants;
        return Optional.of(ParserUtil.parseParticipants(participantSet));
    }
}
