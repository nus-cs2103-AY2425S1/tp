package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NEW;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.Email;
import seedu.address.model.person.FullNameMatchesPredicate;
import seedu.address.model.person.JobCode;
import seedu.address.model.person.JobCodePredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameEmailPredicate;
import seedu.address.model.person.NamePhonePredicate;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TagPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsInvalidCommandFormat() {
        // Test invalid input (missing expected format)
        assertParseFailure(parser, " invalidarg",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " e/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " p/ ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " p/123abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsName_returnsFindCommand() {
        // Test parsing by name
        FindCommand expectedCommand = new FindCommand(new FullNameMatchesPredicate("JOHN DOE"));
        assertParseSuccess(parser, " n/JOHN DOE", expectedCommand);
        assertParseSuccess(parser, " n/John Doe", expectedCommand);
    }

    @Test
    public void parse_invalidArgsName_throwsParseException() {
        // Testing an invalid name format, expecting the name constraints error
        assertParseFailure(parser, " n/", Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " n/  ", Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " n/invalid*name", Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " n/abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz",
                Name.MESSAGE_LENGTH_CONSTRAINTS);
    }

    @Test
    public void parse_validArgsNamePhone_returnsFindCommand() {
        // Test parsing by name and phone
        FindCommand expectedCommand = new FindCommand(new NamePhonePredicate("JOHN DOE", "12345678"));
        assertParseSuccess(parser, " n/JOHN DOE p/12345678", expectedCommand);
        assertParseSuccess(parser, " n/John Doe p/12345678", expectedCommand);
        assertParseSuccess(parser, " n/john doe p/12345678", expectedCommand);
        assertParseSuccess(parser, " n/john doe p/12345678", expectedCommand);
        assertParseSuccess(parser, " p/12345678 n/john doe", expectedCommand);
    }

    @Test
    public void parse_invalidPhone_throwsParseException() {
        // Invalid phone format
        assertParseFailure(parser, " n/ p/", Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " n/ p/12121212", Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " n/J@hn p/1212 1212", Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " p/1212 1212 n/J@hn", Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " n/JOHN DOE p/123abc", Phone.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " n/JOHN DOE p/1234 1234", Phone.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " n/JOHN DOE p/1", Phone.MESSAGE_LENGTH_CONSTRAINTS);
        assertParseFailure(parser, " n/JOHN DOE p/+1234567890123456", Phone.MESSAGE_LENGTH_CONSTRAINTS);
    }

    @Test
    public void parse_validArgsNameEmail_returnsFindCommand() {
        // Test parsing by name and email
        FindCommand expectedCommand = new FindCommand(new NameEmailPredicate("JOHN DOE", "john@example.com"));
        assertParseSuccess(parser, " n/JOHN DOE e/john@example.com", expectedCommand);
        assertParseSuccess(parser, " n/John Doe e/JOHN@EXAMPLE.COM", expectedCommand);
        assertParseSuccess(parser, " n/john doe e/John@example.com", expectedCommand);
        assertParseSuccess(parser, " e/john@example.com n/JOHN DOE", expectedCommand);
    }

    @Test
    public void parse_invalidArgsNameEmail_returnsFindCommand() {
        // Test parsing by name and email
        assertParseFailure(parser, " n/ e/", Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " n/ e/john@example.com", Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " n/JOHN DOE e/", Email.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " n/JOHN DOE e/  ", Email.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " n/JOHN DOE e/johnexample.com", Email.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validArgsJobCode_returnsFindCommand() {
        // Test parsing by name
        FindCommand expectedCommand = new FindCommand(new JobCodePredicate("SWE2024"));
        assertParseSuccess(parser, " j/SWE2024", expectedCommand);
    }

    @Test
    public void parse_invalidJobCode_throwsParseException() {
        // Invalid job code format
        assertParseFailure(parser, " j/ ", JobCode.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validTag_returnsFindCommand() {
        // Test parsing by tag
        FindCommand expectedCommand = new FindCommand(new TagPredicate(Arrays.asList(VALID_TAG_NEW)));
        assertParseSuccess(parser, " t/N", expectedCommand);
    }


}
