package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST_ARCHIVE;

import java.util.function.Supplier;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    @Override
    public ListCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LIST_ALL, PREFIX_LIST_ARCHIVE);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LIST_ALL, PREFIX_LIST_ARCHIVE);

        if (argMultimap.getValue(PREFIX_LIST_ALL).isPresent()
                && argMultimap.getValue(PREFIX_LIST_ARCHIVE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_LIST_ALL).isPresent()) {
            return build(argMultimap.getValue(PREFIX_LIST_ALL).get(), ListCommand::ofAll);
        }

        if (argMultimap.getValue(PREFIX_LIST_ARCHIVE).isPresent()) {
            return build(argMultimap.getValue(PREFIX_LIST_ARCHIVE).get(), ListCommand::ofArchive);
        }

        return ListCommand.ofCurrent();
    }

    private ListCommand build(String argumentValue, Supplier<ListCommand> supplier) throws ParseException {
        requireNonNull(argumentValue);

        String trimmedArgumentValue = argumentValue.trim();

        if (!trimmedArgumentValue.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        return supplier.get();
    }
}
