package seedu.address.logic.parser.wedding;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.wedding.AssignWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

public class AssignWeddingCommandParserTest {
    private final AssignWeddingCommandParser parser = new AssignWeddingCommandParser();

    @Test
    public void parse_validArgs_returnsAssignWeddingCommand() {
        Index targetIndex = Index.fromOneBased(1);

        // Expected weddings
        Wedding wedding1 = new Wedding(new WeddingName("Jeslyn's Wedding"));
        Wedding wedding2 = new Wedding(new WeddingName("Wedding April 17th 2025"));

        AssignWeddingCommand expectedCommand = new AssignWeddingCommand(targetIndex,
                new HashMap<>() {
                    { put(wedding1, "g"); }
                    { put(wedding2, "g"); }
                },
                false);

        String userInput = "1 w/Jeslyn's Wedding w/Wedding April 17th 2025";

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Invalid index (non-numeric)
        assertParseFailure(parser, "a w/Wedding April 17th 2025", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AssignWeddingCommand.MESSAGE_USAGE));

        // Missing weddings (no weddings specified)
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AssignWeddingCommand.MESSAGE_USAGE));

        // Index missing
        assertParseFailure(parser, "w/Wedding April 17th 2025 w/Jeslyn's Wedding",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AssignWeddingCommand.MESSAGE_USAGE));

        // Invalid wedding (contains non-alphanumeric, apostrophe, or space values)
        assertParseFailure(parser, "1 w/Wedding April 17th 2025 w/Jeslyn's_Wedding",
                WeddingName.MESSAGE_CONSTRAINTS);

        // Invalid wedding (blank)
        assertParseFailure(parser, "1 w/ w/Jeslyn's_Wedding",
                WeddingName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validArgsWithForce_returnsAssignWeddingCommand() {
        Index targetIndex = Index.fromOneBased(1);

        // Expected weddings
        Wedding wedding1 = new Wedding(new WeddingName("Jeslyn's Wedding"));
        Wedding wedding2 = new Wedding(new WeddingName("Wedding April 17th 2025"));

        AssignWeddingCommand expectedCommand = new AssignWeddingCommand(targetIndex,
                new HashMap<>() {
                    { put(wedding1, "g"); }
                    { put(wedding2, "g"); }
                }, true);

        String userInput = "1 w/Jeslyn's Wedding w/Wedding April 17th 2025 f/";

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleWeddingsWithDifferentTypes_returnsAssignWeddingCommand() {
        String userInput = "2 w/Alice's Wedding w/Bob's Wedding p1/ w/Charlie's Wedding p2/";
        try {
            new AssignWeddingCommandParser().parse(userInput);
        } catch (ParseException e) {
            assert(false);
        }
        assert(true);
    }

    @Test
    public void parse_multipleValidWeddingsWithForce_returnsAssignWeddingCommand() {
        Index targetIndex = Index.fromOneBased(3);

        // Expected weddings
        Wedding wedding1 = new Wedding(new WeddingName("Xavier's Wedding"));
        Wedding wedding2 = new Wedding(new WeddingName("Yvonne's Wedding"));

        AssignWeddingCommand expectedCommand = new AssignWeddingCommand(targetIndex,
                new HashMap<>() {
                    { put(wedding1, "g"); }
                    { put(wedding2, "g"); }
                },
                true);

        String userInput = "3 w/Xavier's Wedding w/Yvonne's Wedding f/";

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_duplicateWeddings_throwsCommandException() {
        String userInput = "3 w/Xavier's Wedding w/Xavier's Wedding";
        assertParseFailure(parser, userInput, Messages.MESSAGE_DUPLICATED_WEDDING_IN_ASSIGN);
    }
}
