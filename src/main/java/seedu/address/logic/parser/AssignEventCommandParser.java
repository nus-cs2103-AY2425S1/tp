package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignEventByPersonIndexEventIndexCommand;
import seedu.address.logic.commands.AssignEventByPersonIndexEventNameCommand;
import seedu.address.logic.commands.AssignEventByPersonNameEventIndexCommand;
import seedu.address.logic.commands.AssignEventByPersonNameEventNameCommand;
import seedu.address.logic.commands.AssignEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new AssignEventCommand object
 */
public class AssignEventCommandParser implements Parser<AssignEventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AssignEventCommand
     * and returns an AssignEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_EVENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_PERSON, PREFIX_EVENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignEventCommand.MESSAGE_USAGE));
        }

        String eventArgs = argMultimap.getValue(PREFIX_EVENT).get();
        String personArgs = argMultimap.getValue(PREFIX_PERSON).get();

        Character eventArgsFirstChar = eventArgs.trim().isEmpty() ? ' ' : eventArgs.trim().charAt(0);
        Character personArgsFirstChar = personArgs.trim().isEmpty() ? ' ' : personArgs.trim().charAt(0);

        if (Character.isDigit(eventArgsFirstChar) && Character.isDigit(personArgsFirstChar)) {
            return parsePersonIndexEventIndex(personArgs, eventArgs);
        } else if (Character.isDigit(eventArgsFirstChar)) {
            return parsePersonNameEventIndex(personArgs, eventArgs);
        } else if (Character.isDigit(personArgsFirstChar)) {
            return parsePersonIndexEventName(personArgs, eventArgs);
        } else {
            return parsePersonNameEventName(personArgs, eventArgs);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private AssignEventByPersonIndexEventIndexCommand parsePersonIndexEventIndex(
            String personArgs, String eventArgs) throws ParseException {
        try {
            Index personIndex = ParserUtil.parseIndex(personArgs);
            Index eventIndex = ParserUtil.parseIndex(eventArgs);
            return new AssignEventByPersonIndexEventIndexCommand(personIndex, eventIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignEventCommand.MESSAGE_USAGE), pe);
        }
    }

    private AssignEventByPersonNameEventNameCommand parsePersonNameEventName(
            String personArgs, String eventArgs) throws ParseException {
        try {
            Name personName = ParserUtil.parseName(personArgs);
            EventName eventName = ParserUtil.parseEventName(eventArgs);
            return new AssignEventByPersonNameEventNameCommand(personName, eventName);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignEventCommand.MESSAGE_USAGE), pe);
        }
    }

    private AssignEventCommand parsePersonIndexEventName(String personArgs, String eventArgs) throws ParseException {
        try {
            Index personIndex = ParserUtil.parseIndex(personArgs);
            EventName eventName = ParserUtil.parseEventName(eventArgs);
            return new AssignEventByPersonIndexEventNameCommand(personIndex, eventName);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignEventCommand.MESSAGE_USAGE), pe);
        }
    }

    private AssignEventByPersonNameEventIndexCommand parsePersonNameEventIndex(
            String personArgs, String eventArgs) throws ParseException {
        try {
            Name personName = ParserUtil.parseName(personArgs);
            Index eventIndex = ParserUtil.parseIndex(eventArgs);
            return new AssignEventByPersonNameEventIndexCommand(personName, eventIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignEventCommand.MESSAGE_USAGE), pe);
        }
    }
}
