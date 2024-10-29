package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
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
            throw new ParseException(ListCommand.INVALID_MULTIPLE_ARGUMENTS_MESSAGE);
        }

        if (argMultimap.getValue(PREFIX_LIST_ALL).isPresent()) {
            return build(argMultimap.getValue(PREFIX_LIST_ALL).get(), PREFIX_LIST_ALL, ListCommand::ofAll);
        }

        if (argMultimap.getValue(PREFIX_LIST_ARCHIVE).isPresent()) {
            return build(argMultimap.getValue(PREFIX_LIST_ARCHIVE).get(), PREFIX_LIST_ARCHIVE, ListCommand::ofArchive);
        }

        return ListCommand.ofCurrent();
    }

    private ListCommand build(String argumentValue, Prefix prefix,
                              Supplier<ListCommand> supplier) throws ParseException {
        requireNonNull(argumentValue);

        String trimmedArgumentValue = argumentValue.trim();

        if (!trimmedArgumentValue.isEmpty()) {
            throw new ParseException(String.format(
                    ListCommand.INVALID_NON_EMPTY_ARGUMENT_VALUE_MESSAGE_FORMAT, prefix));
        }

        return supplier.get();
    }
}
