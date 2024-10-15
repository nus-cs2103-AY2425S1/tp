package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

//import java.util.Arrays;
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

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    //    public FindCommand parse(String args) throws ParseException {
    //        String trimmedArgs = args.trim();
    //        if (trimmedArgs.isEmpty()) {
    //            throw new ParseException(
    //                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    //        }
    //
    //        String[] nameKeywords = trimmedArgs.split("\\s+");
    //
    //        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    //    }
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");
        List<String> nameKeywords = new ArrayList<>();
        List<String> courseKeywords = new ArrayList<>();

        for (String keyword : keywords) {
            if (keyword.startsWith("n/")) {
                nameKeywords.add(keyword.substring(2));
            } else if (keyword.startsWith("c/")) {
                courseKeywords.add(keyword.substring(2));
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
        }

        Predicate<Student> namePredicate = new NameContainsKeywordsPredicate(nameKeywords);
        Predicate<Student> coursePredicate = new IsStudentOfCoursePredicate(courseKeywords);

        return new FindCommand(namePredicate.or(coursePredicate));
    }
}
