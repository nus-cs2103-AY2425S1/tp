package seedu.ddd.logic.parser;

import static seedu.ddd.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.ddd.logic.commands.ListCommand;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.Model;
import seedu.ddd.model.person.ContactContainsTagPredicate;
import seedu.ddd.model.tag.Tag;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        } else if (argMultimap.getValue(PREFIX_TAG).isEmpty()) {
            return new ListCommand(Model.PREDICATE_SHOW_ALL_CONTACTS);
        }

        Set<Tag> tagSet = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        return new ListCommand(new ContactContainsTagPredicate(tagSet));
    }
}
