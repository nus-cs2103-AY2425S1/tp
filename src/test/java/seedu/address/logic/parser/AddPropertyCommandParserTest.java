package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddPropertyCommand;
import seedu.address.model.person.Property;


public class AddPropertyCommandParserTest {
    private AddPropertyCommandParser parser = new AddPropertyCommandParser();
    private Property property = new Property("Pasir Ris");

    @Test
    public void parse_validArgs_returnsAddPropertyCommand() {
        assertParseSuccess(parser, INDEX_FIRST_PERSON.toStringOneBased() + " " + PREFIX_PROPERTY + property,
                new AddPropertyCommand(INDEX_FIRST_PERSON, property));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyCommand.MESSAGE_USAGE));
    }
}
