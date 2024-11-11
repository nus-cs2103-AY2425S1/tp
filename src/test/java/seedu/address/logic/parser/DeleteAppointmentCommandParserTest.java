package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteAppointmentCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteAppointmentCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteAppointmentCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteAppointmentCommandParserTest {

    private DeleteAppointmentCommandParser parser = new DeleteAppointmentCommandParser();

    // EP [1..INT_MAX]
    @Test
    public void parse_validArgsSpecificIndex_returnsDeleteAppointmentCommand() {
        assertParseSuccess(parser, "627642", new DeleteAppointmentCommand(Index.fromOneBased(627642)));
    }

    // EP [1..INT_MAX], BV test (1)
    @Test
    public void parse_validArgsFirstIndex_returnsDeleteAppointmentCommand() {
        assertParseSuccess(parser, "1", new DeleteAppointmentCommand(INDEX_FIRST_PERSON));
    }

    // EP [1..INT_MAX], BV test (INT_MAX)
    @Test
    public void parse_validArgsIntMax_returnsDeleteAppointmentCommand() {
        assertParseSuccess(parser, "2147483647", new DeleteAppointmentCommand(Index.fromOneBased(2147483647)));
    }

    // EP [-MAX_INT..0]
    @Test
    public void parse_invalidArgsSpecificIndex_throwsParseException() {
        assertParseFailure(parser, "-74214",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    // EP [-MAX_INT..0], BV test (-INT_MAX)
    @Test
    public void parse_invalidArgsNegativeIntMax_throwsParseException() {
        assertParseFailure(parser, "-2147483647",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    // EP [-MAX_INT..0], BV test (0)
    @Test
    public void parse_invalidArgsZero_throwsParseException() {
        assertParseFailure(parser, "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    // EP [<MAX_INT]
    @Test
    public void parse_invalidArgsLessThanMaxInt_throwsParseException() {
        assertParseFailure(parser, "-2147483648",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    // EP [>MAX_INT]
    @Test
    public void parse_invalidArgsZeroMoreThanMaxInt_throwsParseException() {
        assertParseFailure(parser, "2147483648",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE));
    }

    // EP non-integers parsed
    @Test
    public void parse_invalidArgsNonInteger_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE));
    }
}
