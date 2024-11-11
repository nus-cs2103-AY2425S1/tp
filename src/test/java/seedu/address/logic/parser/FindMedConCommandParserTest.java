package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindMedConCommand;
import seedu.address.model.person.MedConContainsKeywordsPredicate;

public class FindMedConCommandParserTest {
    private FindMedConCommandParser parser = new FindMedConCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindMedConCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindMedConCommand() {
        MedConContainsKeywordsPredicate predicate = new MedConContainsKeywordsPredicate(Arrays.asList("myopia", "flu"));
        // no leading and trailing whitespaces
        FindMedConCommand expectedFindMedConCommand = new FindMedConCommand(predicate);
        assertParseSuccess(parser, "myopia flu", expectedFindMedConCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n myopia \n \t flu  \t", expectedFindMedConCommand);
    }
}
