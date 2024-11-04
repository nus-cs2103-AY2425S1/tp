package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.personcommands.LinkPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.types.common.Name;

/**
 * Parses input arguments and creates a new LinkPersonCommand object
 */
public class LinkCommandParser implements Parser<LinkPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LinkPersonCommand
     * and returns a LinkPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LinkPersonCommand parse(ModelType model, String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    LinkPersonCommand.MESSAGE_USAGE),
                    pe);
        }

        if (!argMultimap.getValue(PREFIX_EVENT).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkPersonCommand.MESSAGE_USAGE));
        }

        Name eventName = ParserUtil.parseName(argMultimap.getValue(PREFIX_EVENT).get());

        return new LinkPersonCommand(index, eventName);
    }
}
