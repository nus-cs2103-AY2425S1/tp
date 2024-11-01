package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COURSE_DESC_CS2030;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SPACED_PREFIX_COURSE;
import static seedu.address.logic.commands.CommandTestUtil.SPACED_PREFIX_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_CS2030;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_CS2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COURSE_LOWERCASE_CS2030;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CliSyntax.DEFAULT_DELIMITER;
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
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validSingleNameArg_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(List.of(VALID_NAME_AMY))
        );

        // no leading and trailing whitespaces
        assertParseSuccess(parser, NAME_DESC_AMY, expectedFindCommand);

        // multiple whitespaces around keyword
        assertParseSuccess(parser, " \n " + NAME_DESC_AMY + "\n  \t", expectedFindCommand);
    }

    @Test
    public void parse_validSpacedNameArg_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(List.of(VALID_NAME_AMY))
        );

        // no leading and trailing whitespaces
        assertParseSuccess(parser, SPACED_PREFIX_NAME + " " + VALID_NAME_AMY, expectedFindCommand);

        // multiple whitespaces around keyword
        assertParseSuccess(parser, " \n" + SPACED_PREFIX_NAME + "\t" + VALID_NAME_AMY
                + "\n  \t", expectedFindCommand);
    }

    @Test
    public void parse_validSingleCourseArg_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(
                new IsStudentOfCoursePredicate(List.of(VALID_COURSE_CS2030))
        );

        // no trailing whitespaces
        assertParseSuccess(parser, COURSE_DESC_CS2030, expectedFindCommand);

        // lowercase
        assertParseSuccess(parser, SPACED_PREFIX_COURSE + VALID_COURSE_LOWERCASE_CS2030, expectedFindCommand);

        // multiple whitespaces around keyword
        assertParseSuccess(parser, " \n" + SPACED_PREFIX_COURSE + "  " + VALID_COURSE_LOWERCASE_CS2030
                + " \n  \t", expectedFindCommand);
    }

    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<Predicate<Student>> predicates = List.of(
                new NameContainsKeywordsPredicate(List.of(VALID_NAME_AMY)),
                new NameContainsKeywordsPredicate(List.of(VALID_NAME_BOB))
        );
        FindCommand expectedFindCommand = new FindCommand(predicates);
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB, expectedFindCommand);

        //multiple whitespaces between keywords
        assertParseSuccess(parser, " \n" + SPACED_PREFIX_NAME + VALID_NAME_AMY + " \n \t "
                + SPACED_PREFIX_NAME + VALID_NAME_BOB + "  \t", expectedFindCommand);
    }

    @Test
    public void parse_validMultipleTypeArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<Predicate<Student>> predicates = List.of(
                new NameContainsKeywordsPredicate(List.of(VALID_NAME_AMY)),
                new IsStudentOfCoursePredicate(List.of(VALID_COURSE_CS2030))
        );
        FindCommand expectedFindCommand = new FindCommand(predicates);
        assertParseSuccess(parser, NAME_DESC_AMY + COURSE_DESC_CS2030, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n " + SPACED_PREFIX_NAME + VALID_NAME_AMY + " \n \t "
                + SPACED_PREFIX_COURSE + VALID_COURSE_LOWERCASE_CS2030 + "  \t", expectedFindCommand);
    }

    @Test
    public void parse_validMultipleArgsWithDelimiter_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<Predicate<Student>> predicates = List.of(
                new NameContainsKeywordsPredicate(List.of(VALID_NAME_AMY, VALID_NAME_BOB)),
                new IsStudentOfCoursePredicate(List.of(VALID_COURSE_CS2030,
                        VALID_COURSE_CS2101, VALID_COURSE_CS2103T))
        );
        FindCommand expectedFindCommand = new FindCommand(predicates);

        // white spaces before and after commands
        assertParseSuccess(parser, SPACED_PREFIX_NAME + " " + VALID_NAME_AMY + DEFAULT_DELIMITER
                + VALID_NAME_BOB + " "
                + SPACED_PREFIX_COURSE + " " + VALID_COURSE_LOWERCASE_CS2030 + DEFAULT_DELIMITER
                + VALID_COURSE_CS2101 + " " + DEFAULT_DELIMITER
                + " " + VALID_COURSE_CS2103T + " ", expectedFindCommand);

        // line breaks and tabs between keywords
        assertParseSuccess(parser, SPACED_PREFIX_NAME + "\n" + VALID_NAME_AMY + "\t" + DEFAULT_DELIMITER
                + "\t" + VALID_NAME_BOB + "\t"
                + SPACED_PREFIX_COURSE + " " + VALID_COURSE_CS2030 + "\n" + DEFAULT_DELIMITER
                + "\t " + VALID_COURSE_CS2101 + DEFAULT_DELIMITER
                + " " + VALID_COURSE_CS2103T + "\t", expectedFindCommand);
    }
}
