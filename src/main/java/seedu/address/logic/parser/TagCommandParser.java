package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagCommand;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the given {@code String} of arguments in the context of the TagCommand
 * and returns a TagCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class TagCommandParser implements Parser<TagCommand> {
    @Override
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap multimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        try {
            Index index = ParserUtil.parseIndex(multimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT));
        }
        throw new ParseException("Tag Command Parser not implemented yet.");
    }
}
