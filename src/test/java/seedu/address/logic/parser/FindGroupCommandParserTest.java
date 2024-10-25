package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindGroupCommand;
import seedu.address.model.group.GroupContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindGroupCommandParserTest {

    private FindGroupCommandParser parser = new FindGroupCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindGroupCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_validGroupArgs_returnsFindCommand() {

        List<String> keyWord = new ArrayList<>();
        keyWord.add("Gooners");
        // no leading and trailing whitespaces
        FindGroupCommand expectedFindGroupCommand =
                new FindGroupCommand(new GroupContainsKeywordsPredicate(keyWord));
        assertParseSuccess(parser, "Gooners", expectedFindGroupCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\n Gooners \n \t", expectedFindGroupCommand);
    }

    @Test
    public void parse_invalidGroupArgs_throwsParseException() {
        // Missing group name after /group
        assertParseFailure(parser, "    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindGroupCommand.MESSAGE_USAGE));
    }

}
