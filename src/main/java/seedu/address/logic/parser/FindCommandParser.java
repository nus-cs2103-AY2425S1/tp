package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.IsStudentOfCoursePredicate;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.student.Student;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    public static final Prefix[] PREFIXES = {PREFIX_NAME, PREFIX_COURSE};

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIXES);
        boolean allEmpty = true;
        for (Prefix pre : PREFIXES) {
            if (!argMultimap.getAllValues(pre).isEmpty()) {
                allEmpty = false;
            }
        }
        if (allEmpty) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<Predicate<Student>> predicateList = new ArrayList<>();
        for (Prefix pre : PREFIXES) {
            combinePredicates(argMultimap, pre, predicateList);
        }

        return new FindCommand(predicateList);
    }

    /**
     * Creates a combined predicate for the given prefix from the ArgumentMultimap.
     */
    private void combinePredicates(ArgumentMultimap argMultimap,
                                                 Prefix prefix,
                                                 List<Predicate<Student>> predicateList) {
        if (argMultimap.getValue(prefix).isPresent()) {
            if (prefix.equals(PREFIX_NAME)) {
                argMultimap.getAllValues(prefix)
                        .forEach(pre -> predicateList.add(
                                new NameContainsKeywordsPredicate(ArgumentTokenizer.tokenizeWithDefault(pre))));
            } else if (prefix.equals(PREFIX_COURSE)) {
                argMultimap.getAllValues(prefix)
                        .forEach(pre -> predicateList.add(
                                new IsStudentOfCoursePredicate(ArgumentTokenizer.tokenizeWithDefault(pre))));
            }
        }
    }
}

