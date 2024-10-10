package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code int} index argument in the context of the ViewCommand
     * and returns an ViewCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME);

        return new ViewCommand(index);
    }
}