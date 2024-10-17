package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RENTAL;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteRentalCommand;

public class DeleteRentalCommandParserTest {
    private DeleteRentalCommandParser parser = new DeleteRentalCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteRentalCommand() {
        assertParseSuccess(parser, " " + PREFIX_CLIENT_INDEX + "1 " + PREFIX_RENTAL_INDEX + "1",
                new DeleteRentalCommand(INDEX_FIRST_PERSON, INDEX_FIRST_RENTAL));
    }

    @Test
    public void parse_invalidClientIndex_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_CLIENT_INDEX + "a " + PREFIX_RENTAL_INDEX + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRentalCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidRentalIndex_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_CLIENT_INDEX + "1 " + PREFIX_RENTAL_INDEX + "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRentalCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingClientIndex_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_RENTAL_INDEX + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRentalCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingRentalIndex_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_CLIENT_INDEX + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRentalCommand.MESSAGE_USAGE));
    }
}
