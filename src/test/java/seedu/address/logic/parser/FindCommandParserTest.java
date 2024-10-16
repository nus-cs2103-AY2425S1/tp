package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.student.IsStudentOfCoursePredicate;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.student.Student;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validSingleArg_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(List.of("Alice"))
        );

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "Alice", expectedFindCommand);

        // multiple whitespaces around keyword
        assertParseSuccess(parser, " \n Alice \n  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<Predicate<Student>> predicates = List.of(
                new NameContainsKeywordsPredicate(List.of("Alice")),
                new NameContainsKeywordsPredicate(List.of("Bob"))
        );
        FindCommand expectedFindCommand = new FindCommand(predicates);
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validCourseArg_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(
                new IsStudentOfCoursePredicate(List.of("CS2103T"))
        );

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "c/CS2103T", expectedFindCommand);

        // multiple whitespaces around keyword
        assertParseSuccess(parser, " \n c/CS2103T \n  \t", expectedFindCommand);
    }

    @Test
    public void parse_validMultipleCourseArgs_returnsFindCommand() {
        List<Predicate<Student>> predicates = List.of(
                new IsStudentOfCoursePredicate(List.of("CS2103T")),
                new IsStudentOfCoursePredicate(List.of("CS2101"))
        );
        FindCommand expectedFindCommand = new FindCommand(predicates);

        assertParseSuccess(parser, "c/CS2103T c/CS2101", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n c/CS2103T \n \t c/CS2101  \t", expectedFindCommand);
    }

    @Test
    public void parse_validMixedArgs_returnsFindCommand() {
        List<Predicate<Student>> predicates = List.of(
                new NameContainsKeywordsPredicate(List.of("Alice")),
                new IsStudentOfCoursePredicate(List.of("CS2103T")),
                new NameContainsKeywordsPredicate(List.of("Bob"))
        );
        FindCommand expectedFindCommand = new FindCommand(predicates);

        assertParseSuccess(parser, "n/Alice c/CS2103T n/Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Alice \n \t c/CS2103T  \t n/Bob", expectedFindCommand);
    }

    @Test
    public void parse_validMixedArgsWithoutPrefixes_returnsFindCommand() {
        List<Predicate<Student>> predicates = List.of(
                new NameContainsKeywordsPredicate(List.of("Alice")),
                new IsStudentOfCoursePredicate(List.of("CS2103T")),
                new NameContainsKeywordsPredicate(List.of("Bob"))
        );
        FindCommand expectedFindCommand = new FindCommand(predicates);

        assertParseSuccess(parser, "Alice c/CS2103T Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t c/CS2103T  \t Bob", expectedFindCommand);
    }
}
