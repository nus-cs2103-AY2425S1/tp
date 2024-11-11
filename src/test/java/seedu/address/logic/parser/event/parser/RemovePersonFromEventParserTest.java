package seedu.address.logic.parser.event.parser;

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
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // empty input
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // blank input
        assertParseFailure(parser, " ", MESSAGE_INVALID_FORMAT);

        // keyword "remove" not specified
        assertParseFailure(parser, "ei/1 ci/1", MESSAGE_INVALID_FORMAT);

        // keyword "remove" is the only thing specified
        assertParseFailure(parser, "remove", MESSAGE_INVALID_FORMAT);

        // ci missing
        assertParseFailure(parser, "remove ei/1", MESSAGE_INVALID_FORMAT);

        // ei missing
        assertParseFailure(parser, "remove ci/1", MESSAGE_INVALID_FORMAT);

        // ci index missing
        assertParseFailure(parser, "remove ei/1 ci/", MESSAGE_INVALID_FORMAT);

        // ei index missing
        assertParseFailure(parser, "remove ei/ ci/1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidParts_failure() {
        // negative integers
        assertParseFailure(parser, "remove ei/1 ci/-1", MESSAGE_INVALID_FORMAT);

        // decimal numbers
        assertParseFailure(parser, "remove ei/1.5 ci/1", MESSAGE_INVALID_FORMAT);

        // letters
        assertParseFailure(parser, "remove ei/a ci/1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsRemovePersonFromEventCommand() {
        assertParseSuccess(parser, "remove ei/1 ci/1",
                 new RemovePersonFromEventCommand(Index.fromOneBased(1),
                         Index.fromOneBased(1)));
    }

    @Test
    public void parse_validArgs_differentOrder() {
        assertParseSuccess(parser, "remove ci/1 ei/2",
                new RemovePersonFromEventCommand(Index.fromOneBased(2),
                        Index.fromOneBased(1)));
    }
}
