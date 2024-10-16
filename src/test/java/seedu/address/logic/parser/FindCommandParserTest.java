package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
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
    public void parse_validSingleNameArg_returnsFindCommand() throws ParseException {
        FindCommand expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(List.of("Alice"))
        );

        // no leading and trailing whitespaces
        assertParseSuccess(parser, " n/Alice", expectedFindCommand);

        // multiple whitespaces around keyword
        assertParseSuccess(parser, " \n n/Alice \n  \t", expectedFindCommand);
    }

    @Test
    public void parse_validSingleCourseArg_returnsFindCommand() throws ParseException {
        FindCommand expectedFindCommand = new FindCommand(
                new IsStudentOfCoursePredicate(List.of("CS2030S"))
        );

        // no trailing whitespaces
        assertParseSuccess(parser, " c/CS2030S", expectedFindCommand);

        // lowercase
        assertParseSuccess(parser, " c/cs2030s", expectedFindCommand);

        // multiple whitespaces around keyword
        assertParseSuccess(parser, " \n c/cs2030s \n  \t", expectedFindCommand);
    }

    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<Predicate<Student>> predicates = List.of(
                new NameContainsKeywordsPredicate(List.of("Alice")),
                new NameContainsKeywordsPredicate(List.of("Bob"))
        );
        FindCommand expectedFindCommand = new FindCommand(predicates);
        assertParseSuccess(parser, " n/Alice n/Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Alice \n \t n/Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validMultipleTypeArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<Predicate<Student>> predicates = List.of(
                new NameContainsKeywordsPredicate(List.of("Alice")),
                new IsStudentOfCoursePredicate(List.of("CS2030S"))
        );
        FindCommand expectedFindCommand = new FindCommand(predicates);
        assertParseSuccess(parser, " n/Alice c/cs2030s", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Alice \n \t c/cs2030s  \t", expectedFindCommand);
    }
}
