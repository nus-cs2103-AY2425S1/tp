package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Level;
import seedu.address.model.person.Subject;
import seedu.address.model.person.predicate.LevelContainsKeywordsPredicate;
import seedu.address.model.person.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicate.SubjectContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tooManyArg_throwsParseException() {
        assertParseFailure(parser, " n/alice l/S1 s/MATH",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " n/alice l/S1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " n/alice s/MATH",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " l/S1 s/MATH",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validLevelArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new LevelContainsKeywordsPredicate(Arrays.asList("S1 NA")));
        assertParseSuccess(parser, " l/S1 NA", expectedFindCommand);
    }

    @Test
    public void parse_invalidLevelArgs_throwsParseException() {
        assertParseFailure(parser, " l/Sec1", Level.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validSubjectArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new SubjectContainsKeywordsPredicate(Arrays.asList("MATH")));
        assertParseSuccess(parser, " s/MATH", expectedFindCommand);
    }

    @Test
    public void parse_invalidSubjectArgs_throwsParseException() {
        assertParseFailure(parser, " s/MATHEMATIC", Subject.MESSAGE_CONSTRAINTS);
    }

}
