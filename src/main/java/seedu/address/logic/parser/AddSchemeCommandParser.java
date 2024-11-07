package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddSchemeCommand;
import seedu.address.logic.commands.SchemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddSchemeCommand object
 */
public class AddSchemeCommandParser implements Parser<AddSchemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddSchemeCommand
     * and returns an AddSchemeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddSchemeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_INDEX);
        Index personIndex;
        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SchemeCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_INDEX).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SchemeCommand.MESSAGE_USAGE));
        }

        Index schemeIndex = ParserUtil.parseIndex(argMultimap.getValue(CliSyntax.PREFIX_INDEX).get());
        return new AddSchemeCommand(personIndex, schemeIndex);
    }
}
