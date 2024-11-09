package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORTORDER;

import java.util.stream.Stream;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.comparator.ComparatorManager;
import seedu.address.model.person.comparator.PersonComparator;
import seedu.address.model.person.comparator.SortField;
import seedu.address.model.person.comparator.SortOrder;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of SortCommand
     * and returns a sortCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SortCommand parse(String args) throws ParseException {
        boolean isReset;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SORTORDER);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        SortField sortField = parseSortField(argMultimap.getPreamble());
        isReset = sortField == SortField.NONE;

        if (isReset && arePrefixesPresent(argMultimap, PREFIX_SORTORDER)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        if (sortField == SortField.NONE) {
            return new SortCommand(null);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_SORTORDER)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SORTORDER);

        SortOrder sortOrder = parseSortOrder(argMultimap.getValue(PREFIX_SORTORDER).get());
        PersonComparator comparator = new ComparatorManager().getComparator(sortField, sortOrder);

        return new SortCommand(comparator);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} for SortOrder and returns the specified SortOrder
     * @throws ParseException if the user input does not conform the expected format
     */
    private static SortOrder parseSortOrder(String userInput) throws ParseException {
        if (userInput.trim().equalsIgnoreCase("asc")) {
            return SortOrder.ASC;
        } else if (userInput.trim().equalsIgnoreCase("desc")) {
            return SortOrder.DESC;
        } else {
            throw new ParseException(SortCommand.MESSAGE_INVALID_ORDER);
        }
    }

    /**
     * Parses the given {@code String} for SortField and returns the specified SortField
     * @throws ParseException if the user input does not conform the expected format
     */
    private static SortField parseSortField(String userInput) throws ParseException {
        String trimmedInput = userInput.trim().toLowerCase();
        return switch (trimmedInput) {
        case "name" -> SortField.NAME;
        case "github" -> SortField.GITHUB;
        case "telegram" -> SortField.TELEGRAM;
        case "reset" -> SortField.NONE;
        default ->
                throw new ParseException(SortCommand.MESSAGE_INVALID_FIELD);
        };

    }
}
