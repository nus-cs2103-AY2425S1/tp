package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commons.NameContainsKeywordsPredicate;
import seedu.address.model.commons.RoleContainsKeywordPredicate;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new FindPersonCommand object
 */
public class FindPersonCommandParser implements Parser<FindPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindPersonCommand
     * and returns a FindPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPersonCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ROLE);

        if (!(argMultimap.getValue(PREFIX_NAME).isPresent()) && !(argMultimap.getValue(PREFIX_ROLE).isPresent())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ROLE);

        String[] nameKeywords = null;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String names = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()).toString();
            nameKeywords = names.split("\\s+");
        }

        String role = null;
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get()).toString();
        }

        Predicate<Person> predicate = null;
        if (nameKeywords != null && nameKeywords.length > 0 && role != null) {
            predicate = new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords))
                    .and(new RoleContainsKeywordPredicate(role));
        } else if (nameKeywords != null && nameKeywords.length > 0) {
            predicate = new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords));
        } else if (role != null) {
            predicate = new RoleContainsKeywordPredicate(role);
        }

        return new FindPersonCommand(predicate);
    }
}
