package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.BatchEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsTagsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parse input arguments and creates a new BatchEditCommand object
 */
public class BatchEditCommandParser implements Parser<BatchEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BatchEditCommand
     * and returns an BatchEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public BatchEditCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BatchEditCommand.MESSAGE_USAGE));
        }
        List<String> unparsedTag = argMultimap.getAllValues(PREFIX_TAG);
        if (unparsedTag.size() != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BatchEditCommand.MESSAGE_USAGE));
        }

        Tag oldTag = ParserUtil.parseTag(unparsedTag.get(0));
        Tag newTag = ParserUtil.parseTag(unparsedTag.get(1));

        return new BatchEditCommand(oldTag, newTag, new PersonContainsTagsPredicate(Set.of(oldTag)));
    }
}
