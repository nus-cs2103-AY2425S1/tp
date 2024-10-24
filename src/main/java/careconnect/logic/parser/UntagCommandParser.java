package careconnect.logic.parser;

import static careconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static java.util.Objects.requireNonNull;

import careconnect.commons.core.index.Index;
import careconnect.logic.commands.UntagCommand;
import careconnect.logic.parser.exceptions.ParseException;
import careconnect.model.tag.Tag;

/**
 * Parses input arguments and creates a new UntagCommand object
 */
public class UntagCommandParser implements Parser<UntagCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the UntagCommand
     * and returns a UntagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UntagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_TAG);

        Index personIndex;
        Tag tag;
        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE),
                    pe);
        }

        if (!ParserUtil.arePrefixesPresent(argMultimap, CliSyntax.PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UntagCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(CliSyntax.PREFIX_TAG);

        tag = ParserUtil.parseTag(argMultimap.getValue(CliSyntax.PREFIX_TAG).get());
        return new UntagCommand(personIndex, tag);
    }

}
