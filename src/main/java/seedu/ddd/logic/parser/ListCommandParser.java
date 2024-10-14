package seedu.ddd.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_VENDOR;

import java.util.Arrays;
import java.util.Set;

import seedu.ddd.logic.commands.ListCommand;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.Model;
import seedu.ddd.model.contact.common.ContactContainsTagPredicate;
import seedu.ddd.model.contact.common.NameContainsKeywordsPredicate;
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
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG, PREFIX_CLIENT, PREFIX_VENDOR);
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.TAG_MESSAGE_USAGE));
        } else if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            Set<Tag> tagSet = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            return new ListCommand(new ContactContainsTagPredicate(tagSet));
        } else if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String trimmedArgs = argMultimap.getValue(PREFIX_NAME).get().trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.NAME_MESSAGE_USAGE));
            }
            String[] nameKeywords = trimmedArgs.split("\\s+");
            return new ListCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        } else {
            return new ListCommand(Model.PREDICATE_SHOW_ALL_CONTACTS);
        }
    }
}
