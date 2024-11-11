package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.FieldContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, " c/ dave", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidPrefix_returnsFindCommand() {
        // name
        FindCommand expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("Amy"), "name"));
        assertParseSuccess(parser, " n/ Amy", expectedFindCommand);

        // id
        expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("4517"), "id"));
        assertParseSuccess(parser, " i/ 4517", expectedFindCommand);

        // ward
        expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("B09"), "ward"));
        assertParseSuccess(parser, " w/ B09", expectedFindCommand);

        // diagnosis
        expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("fever"), "diagnosis"));
        assertParseSuccess(parser, " d/ fever", expectedFindCommand);

        // medication
        expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("paracetamol"), "medication"));
        assertParseSuccess(parser, " m/ paracetamol", expectedFindCommand);

        // patient notes
        expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("handicap"), "patientnotes"));
        assertParseSuccess(parser, " pn/ handicap", expectedFindCommand);

        // appointment description
        expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("surgery"), "apptdesc"));
        assertParseSuccess(parser, " a/ surgery", expectedFindCommand);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new FieldContainsKeywordsPredicate(Arrays.asList("Amy", "Ben"), "name"));
        assertParseSuccess(parser, " n/ Amy Ben  ", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "    n/  \n Amy \n \t Ben  \t", expectedFindCommand);
    }

    @Test
    public void parse_validPrefixEmptyKeyword_throwsParseException() {
        assertParseFailure(parser, " i/        ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

}
