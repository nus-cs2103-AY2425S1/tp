package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgsIndex_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "j/XYZ1235", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " j/123ABC", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "t/A", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " t/A", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                DeleteCommand.MESSAGE_USAGE));
    }


    @Test
    public void parseOtherAttributes_nameOnly_returnsDeleteCommand() throws ParseException {
        //tests whether deletecommand returned when a certain name and phone are inputted is the same as the expected
        Name name = new Name("Alex Yeoh");
        String args = " n/Alex Yeoh";
        String args2 = " n/ Alex Yeoh";
        String args3 = " n/ Alex Yeoh ";
        DeleteCommand expectedCommand = new DeleteCommand(name);
        assertParseSuccess(parser, args, expectedCommand);
        assertParseSuccess(parser, args2, expectedCommand);
        assertParseSuccess(parser, args3, expectedCommand);
    }

    @Test
    public void parseOtherAttributes_emailOnly_returnsDeleteCommand() throws ParseException {
        //tests whether deletecommand returned when a certain name and phone are inputted is the same as the expected
        Email name = new Email("alexyeoh@example.com");
        String args = " e/alexyeoh@example.com";
        String args2 = " e/ alexyeoh@example.com";
        String args3 = " e/alexyeoh@example.com ";
        DeleteCommand expectedCommand = new DeleteCommand(name);
        assertParseSuccess(parser, args, expectedCommand);
        assertParseSuccess(parser, args2, expectedCommand);
        assertParseSuccess(parser, args3, expectedCommand);
    }

    @Test
    public void parseOtherAttributes_phoneOnly_returnsDeleteCommand() throws ParseException {
        //tests whether deletecommand returned when a certain name and phone are inputted is the same as the expected
        Phone name = new Phone("12345678");
        String args = " p/12345678";
        String args2 = " p/ 12345678";
        String args3 = " p/12345678 ";
        DeleteCommand expectedCommand = new DeleteCommand(name);
        assertParseSuccess(parser, args, expectedCommand);
        assertParseSuccess(parser, args2, expectedCommand);
        assertParseSuccess(parser, args3, expectedCommand);
    }

}
