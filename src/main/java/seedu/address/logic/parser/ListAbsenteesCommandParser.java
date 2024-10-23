package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDateTime;

import seedu.address.logic.commands.ListAbsenteesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DateAbsentPredicate;

/**
 * Parses input arguments and creates a new ListAbsenteesCommand object
 */
public class ListAbsenteesCommandParser implements Parser<ListAbsenteesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListAbsenteesCommand
     * and returns a ListAbsenteesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListAbsenteesCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAbsenteesCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DATE);
        if (!argMultimap.getValue(PREFIX_DATE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            ListAbsenteesCommand.MESSAGE_USAGE));
        }

        LocalDateTime date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());

        return new ListAbsenteesCommand(new DateAbsentPredicate(date));
    }

}
