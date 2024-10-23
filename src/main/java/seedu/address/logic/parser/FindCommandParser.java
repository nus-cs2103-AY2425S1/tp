package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Level;
import seedu.address.model.person.Subject;
import seedu.address.model.person.predicate.LevelContainsKeywordsPredicate;
import seedu.address.model.person.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicate.SubjectContainsKeywordsPredicate;

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
            String[] levelKeywords = {toFind};

            if (Arrays.stream(levelKeywords).anyMatch(level -> !Level.isValidLevelName(level))) {
                throw new ParseException(Level.MESSAGE_CONSTRAINTS);
            }
            return new FindCommand(new LevelContainsKeywordsPredicate(Arrays.asList(levelKeywords)));
        } else {
            String toFind = argMultimap.getValue(PREFIX_SUBJECT).get();
            String[] subjectKeywords = toFind.split("\\s+");
            if (Arrays.stream(subjectKeywords).anyMatch(subject -> !Subject.isValidSubjectName(subject))) {
                throw new ParseException(Subject.MESSAGE_CONSTRAINTS);
            }
            return new FindCommand(new SubjectContainsKeywordsPredicate(Arrays.asList(subjectKeywords)));
        }
    }
}
