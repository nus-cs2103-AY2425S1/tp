package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.BatchDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.predicates.PersonContainsTagsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parse input arguments and creates a new BatchDeleteCommand object
 */
public class BatchDeleteCommandParser implements Parser<BatchDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BatchDeleteCommand
     * and returns an BatchDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BatchDeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BatchDeleteCommand.MESSAGE_USAGE));
        }

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        return new BatchDeleteCommand(tagList, new PersonContainsTagsPredicate(tagList));
    }

}
