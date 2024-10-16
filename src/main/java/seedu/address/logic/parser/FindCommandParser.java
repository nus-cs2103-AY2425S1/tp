package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<String> keywords = List.of(trimmedArgs.split("\\s+"));

        List<? extends Predicate<Student>> predicateList =
                keywords.stream().map(this::getPredicate).toList();

        return new FindCommand(predicateList);
    }

    /**
     * Returns a Predicate based on the given keyword.
     * @param keyword the keyword to be parsed
     * @return the appropriate Predicate based on the keyword prefix
     */
    private Predicate<Student> getPredicate(String keyword) {
        String[] parts = keyword.split("/", 2);
        if (parts.length != 2) {
            // Default to name search if no prefix is provided
            return new NameContainsKeywordsPredicate(List.of(keyword));
        }

        String prefix = parts[0];
        String value = parts[1];

        switch (prefix) {
        case "n":
            return new NameContainsKeywordsPredicate(List.of(value));
        case "c":
            return new IsStudentOfCoursePredicate(List.of(value));
        default:
            // Default to name search for unknown prefixes
            return new NameContainsKeywordsPredicate(List.of(value));
        }
    }
}
