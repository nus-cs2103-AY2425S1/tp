package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.predicates.LevelContainsKeywordsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.SubjectContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_LEVEL, PREFIX_SUBJECT);

        boolean isByName = argMultimap.getValue(PREFIX_NAME).isPresent();
        boolean isByLevel = argMultimap.getValue(PREFIX_LEVEL).isPresent();
        boolean isBySubject = argMultimap.getValue(PREFIX_SUBJECT).isPresent();

        if ((isByName && isByLevel) || (isByName && isBySubject)
                || (isByLevel && isBySubject) || (!isByName && !isByLevel && !isBySubject)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_LEVEL, PREFIX_SUBJECT);

        if (isByName) {
            String toFind = argMultimap.getValue(PREFIX_NAME).get();
            String[] nameKeywords = toFind.split("\\s+");
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        } else if (isByLevel) {
            String toFind = argMultimap.getValue(PREFIX_LEVEL).get();
            String[] levelKeywords = toFind.split("\\s+");
            return new FindCommand(new LevelContainsKeywordsPredicate(Arrays.asList(levelKeywords)));
        } else if (isBySubject) {
            String toFind = argMultimap.getValue(PREFIX_SUBJECT).get();
            String[] subjectKeywords = toFind.split("\\s+");
            return new FindCommand(new SubjectContainsKeywordsPredicate(Arrays.asList(subjectKeywords)));
        }
        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

    }
}
