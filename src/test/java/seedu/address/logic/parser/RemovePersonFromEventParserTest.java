package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.commands.RemovePersonFromEventCommand;


public class RemovePersonFromEventParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovePersonFromEventCommand.MESSAGE_USAGE);
    private RemovePersonFromEventParser parser = new RemovePersonFromEventParser();

    @Test
    public void parse_MissingParts_Failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsRemovePersonFromEventCommand() {
         assertParseSuccess(parser, "remove ei/1 pi/1",
                 new RemovePersonFromEventCommand(Index.fromOneBased(1),
                         Index.fromOneBased(1)));
    }

    @Test
    public void parse_validArgs_differentOrder() {
        assertParseSuccess(parser, "remove pi/1 ei/2",
                new RemovePersonFromEventCommand(Index.fromOneBased(2),
                        Index.fromOneBased(1)));
    }
}
