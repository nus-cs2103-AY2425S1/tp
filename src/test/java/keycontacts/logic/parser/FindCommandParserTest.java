package keycontacts.logic.parser;

import static keycontacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static keycontacts.logic.parser.CommandParserTestUtil.assertParseFailure;
import static keycontacts.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import keycontacts.logic.commands.FindCommand;
import keycontacts.logic.commands.FindCommand.FindStudentDescriptor;
import keycontacts.model.student.StudentDescriptorMatchesPredicate;
import keycontacts.testutil.FindStudentDescriptorBuilder;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withName("Alice").withAddress("Bob")
                .withPhone("88197184")
                .build();
        FindCommand expectedFindCommand = new FindCommand(new StudentDescriptorMatchesPredicate(descriptor));

        assertParseSuccess(parser, " n/Alice a/Bob p/88197184", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Alice \n \t a/Bob  \t p/88197184", expectedFindCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no prefix
        assertParseFailure(parser, " Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
}
