package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.stream.Stream;

import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;

/**
 * Parses input arguments and creates a new UpdateCommand object
 */
public class UpdateCommandParser implements Parser<UpdateCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns an UpdateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_NAME, PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_NAME, PREFIX_DATE)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_INDEX, PREFIX_NAME, PREFIX_DATE);
        int indexToUpdate = -1;
        try {
            indexToUpdate = Integer.parseInt(argMultimap.getValue(PREFIX_INDEX).get().trim());
        } catch (NumberFormatException e) {
            throw new ParseException(String.format("Index must be an integer! \n%1$s", UpdateCommand.MESSAGE_USAGE));
        }

        String name = argMultimap.getValue(PREFIX_NAME).get().trim();
        LocalDate date = LocalDate.parse(argMultimap.getValue(PREFIX_DATE).get().trim());
        indexToUpdate -= 1; // subtract 1 because user's event list is not 0-indexed

        Event event = new Event(name, date, new HashSet<>());

        return new UpdateCommand(event, indexToUpdate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
