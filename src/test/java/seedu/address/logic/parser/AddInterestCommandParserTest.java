package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddInterestCommand;
import seedu.address.model.person.Interest;

public class AddInterestCommandParserTest {

    private final AddInterestCommandParser parser = new AddInterestCommandParser();
    @Test
    public void parse_validArgs_returnsAddInterestCommand() {
        // Test valid input with all fields
        Index index = Index.fromOneBased(1);
        Set<Interest> interest = Set.of(new Interest("Swimming"));
        AddInterestCommand expectedCommand = new AddInterestCommand(index, interest);
        assertParseSuccess(parser, " in/1 i/Swimming", expectedCommand);
    }

    @Test
    public void parseInvalidArgs_missingIndex_throwsParseException() {
        // Test input with missing index
        assertParseFailure(parser, " i/Hiking", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddInterestCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Invalid index provided
        assertParseFailure(parser, "in/a i/Swimming",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInterestCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noInterestsProvided_throwsParseException() {
        // Missing interests in the command
        assertParseFailure(parser, "in/1 i/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInterestCommand.MESSAGE_USAGE));
    }
}
