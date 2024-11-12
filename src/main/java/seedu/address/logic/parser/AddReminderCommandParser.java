package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAST_SEEN;
import static seedu.address.model.person.LastSeen.FORMATTER;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddReminderCommand object
 */
public class AddReminderCommandParser implements Parser<AddReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddReminderCommand
     * and returns an AddReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LAST_SEEN, PREFIX_DESCRIPTION);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddReminderCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_LAST_SEEN, PREFIX_DESCRIPTION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DESCRIPTION, PREFIX_LAST_SEEN);

        String reminderDate = ParserUtil.parseReminderDate(argMultimap.getValue(PREFIX_LAST_SEEN).get());

        if (LocalDate.now().isAfter(LocalDate.parse(reminderDate, FORMATTER))) {
            throw new ParseException("Reminder date cannot be in the past");
        }

        String reminderDescription = ParserUtil
                .parseReminderDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());

        return new AddReminderCommand(index, reminderDate, reminderDescription);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
