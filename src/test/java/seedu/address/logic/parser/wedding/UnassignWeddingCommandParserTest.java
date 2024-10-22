package seedu.address.logic.parser.wedding;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.wedding.UnassignWeddingCommand;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

public class UnassignWeddingCommandParserTest {

    private UnassignWeddingCommandParser parser = new UnassignWeddingCommandParser();

    @Test
    public void parse_validArgs_returnsUnassignWeddingCommand() {
        Index targetIndex = Index.fromOneBased(1);

        // Expected weddings
        Wedding wedding1 = new Wedding(new WeddingName("Jeslyn's Wedding"));
        Wedding wedding2 = new Wedding(new WeddingName("Wedding April 17th 2025"));

        UnassignWeddingCommand expectedCommand = new UnassignWeddingCommand(targetIndex,
                new HashSet<>(Arrays.asList(wedding1, wedding2)));

        String userInput = "1 w/Jeslyn's Wedding w/Wedding April 17th 2025";

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid index (non-numeric)
        assertParseFailure(parser, "a w/Wedding April 17th 2025", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnassignWeddingCommand.MESSAGE_USAGE));

        // Missing weddings (no weddings specified)
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnassignWeddingCommand.MESSAGE_USAGE));

        // Index missing
        assertParseFailure(parser, "w/Wedding April 17th 2025 w/Jeslyn's Wedding",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        UnassignWeddingCommand.MESSAGE_USAGE));

        // Invalid wedding (contains non-alphanumeric, apostrophe, or space values)
        assertParseFailure(parser, "1 w/Wedding April 17th 2025 w/Jeslyn's_Wedding",
                WeddingName.MESSAGE_CONSTRAINTS);

        // Invalid wedding (blank)
        assertParseFailure(parser, "1 w/ w/Jeslyn's_Wedding",
                WeddingName.MESSAGE_CONSTRAINTS);
    }
}
