package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONCERT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LinkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LinkCommand object
 */
public class LinkCommandParser implements Parser<LinkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the LinkCommand
     * and returns an LinkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LinkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CONCERT);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CONCERT);

        Index indexP;
        Index indexC;
        try {
            indexP = ParserUtil.parseIndex(argMultimap.getPreamble());
            if (argMultimap.getValue(PREFIX_CONCERT).isEmpty()) {
                throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
            }
            indexC = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CONCERT).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE), pe);
        }

        return new LinkCommand(indexP, indexC);
    }

}
