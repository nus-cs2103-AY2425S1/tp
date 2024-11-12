package seedu.address.logic.parser.meetup;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDED_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.meetup.EditCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meetup.AddedBuyer;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT, PREFIX_INFO, PREFIX_FROM, PREFIX_TO,
                        PREFIX_ADDED_BUYER);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SUBJECT, PREFIX_INFO, PREFIX_FROM, PREFIX_TO);

        EditCommand.EditMeetUpDescriptor editMeetUpDescriptor = new EditCommand.EditMeetUpDescriptor();

        if (argMultimap.getValue(PREFIX_SUBJECT).isPresent()) {
            editMeetUpDescriptor.setSubject(ParserUtil.parseMeetUpSubject(argMultimap.getValue(PREFIX_SUBJECT).get()));
        }
        if (argMultimap.getValue(PREFIX_INFO).isPresent()) {
            editMeetUpDescriptor.setInfo(ParserUtil.parseMeetUpInfo(argMultimap.getValue(PREFIX_INFO).get()));
        }
        if (argMultimap.getValue(PREFIX_FROM).isPresent()) {
            editMeetUpDescriptor.setFrom(ParserUtil.parseMeetUpFrom(argMultimap.getValue(PREFIX_FROM).get()));
        }
        if (argMultimap.getValue(PREFIX_TO).isPresent()) {
            editMeetUpDescriptor.setTo(ParserUtil.parseMeetUpTo(argMultimap.getValue(PREFIX_TO).get()));
        }
        parseAddedBuyersForEdit(argMultimap.getAllValues(PREFIX_ADDED_BUYER))
                .ifPresent(editMeetUpDescriptor::setAddedBuyers);

        if (!editMeetUpDescriptor.isAnyMeetUpFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_MEETUP_NOT_EDITED);
        }

        return new EditCommand(index, editMeetUpDescriptor);
    }

    /**
     * Parses {@code Collection<String> addedBuyers} into a {@code Set<AddedBuyer>}
     * if {@code addedBuyers} is non-empty.
     *
     */
    private Optional<Set<AddedBuyer>> parseAddedBuyersForEdit(Collection<String> addedBuyers) throws ParseException {
        assert addedBuyers != null;

        if (addedBuyers.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(ParserUtil.parseAddedBuyers(addedBuyers));
    }

    /**
     * Returns true if at least one of the prefixes contains a non-empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean atLeastOnePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
