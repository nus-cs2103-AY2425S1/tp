package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.role.Role;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        // no prefix
        assertParseFailure(parser, "friends family", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));

        // one valid prefix, one missing prefix
        assertParseFailure(parser, "n/John family", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyPrefixValue_throwsParseException() {
        // single empty prefix
        assertParseFailure(parser, "n/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));

        // multiple prefixes, one empty
        assertParseFailure(parser, "n/John r/", FilterCommandParser.MESSAGE_ROLE_CANNOT_BE_EMPTY);

        // all empty prefixes
        assertParseFailure(parser, "n/ r/ e/ p/ a/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // single field
        FilterCommand expectedCommand = new FilterCommand(new Name("John"), null, null, null, null);
        assertParseSuccess(parser, " n/John", expectedCommand);

        // multiple fields
        expectedCommand = new FilterCommand(new Name("John"),
                Optional.of(new Role("vendor")), new Email("john@example.com"),
                null, null);
        assertParseSuccess(parser, " n/John r/vendor e/john@example.com", expectedCommand);

        // all fields
        expectedCommand = new FilterCommand(new Name("John"),
                Optional.of(new Role("vendor")), new Email("john@example.com"),
                new Phone("91234567"), new Address("123 Main St"));
        assertParseSuccess(parser, " n/John r/vendor e/john@example.com p/91234567 a/123 Main St",
                expectedCommand);

        // repeated fields (should take last value)
        expectedCommand = new FilterCommand(new Name("John"),
                Optional.of(new Role("vendor")), null, null, null);
        assertParseSuccess(parser, " n/Jane n/John r/vendor", expectedCommand);
    }

    @Test
    public void parse_extraSpaces_returnsFilterCommand() {
        FilterCommand expectedCommand = new FilterCommand(new Name("John"), Optional.of(new Role("vendor")), null,
                null, null);

        // extra spaces between prefix and value
        assertParseSuccess(parser, " n/   John r/   vendor", expectedCommand);

        // extra spaces between different prefixes
        assertParseSuccess(parser, " n/John    r/vendor", expectedCommand);

        // extra spaces at start and end
        assertParseSuccess(parser, "     n/John r/vendor    ", expectedCommand);
    }
}
