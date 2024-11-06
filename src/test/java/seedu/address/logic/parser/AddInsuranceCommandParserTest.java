package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSURANCE_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddInsuranceCommand;

/**
 * Contains unit tests for {@link AddInsuranceCommandParser}.
 */
public class AddInsuranceCommandParserTest {

    private static final int VALID_INSURANCE_ID = 0;
    private final AddInsuranceCommandParser parser = new AddInsuranceCommandParser();

    /**
     * Tests if {@code AddInsuranceCommandParser} correctly parses valid input
     * and returns a {@code AddInsuranceCommand}.
     */
    @Test
    public void parse_validArgs_returnsAddInsuranceCommand() {
        String validUserInput = INDEX_FIRST_CLIENT.getOneBased() + " " + PREFIX_INSURANCE_ID + VALID_INSURANCE_ID;
        AddInsuranceCommand expectedCommand = new AddInsuranceCommand(INDEX_FIRST_CLIENT, VALID_INSURANCE_ID);

        assertParseSuccess(parser, validUserInput, expectedCommand);
    }

    /**
     * Tests the execution of {@code AddInsuranceCommandParser} with an invalid client index.
     * This test verifies that when the specified client index is invalid,
     * the command throws a {@code ParseException}.
     */
    @Test
    public void parse_invalidIndex_throwsParseException() {
        String invalidUserInput = "invalidIndex " + PREFIX_INSURANCE_ID + VALID_INSURANCE_ID;
        assertParseFailure(parser, invalidUserInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInsuranceCommand.MESSAGE_USAGE));
    }

    /**
     * Tests the execution of {@code AddInsuranceCommandParser} with missing insurance ID.
     * This test verifies that when insurance ID is not specified,
     * the command throws a {@code ParseException}.
     */
    @Test
    public void parse_missingInsuranceId_throwsParseException() {
        String missingInsuranceIdInput = INDEX_FIRST_CLIENT.getOneBased() + " " + PREFIX_INSURANCE_ID;
        assertParseFailure(parser, missingInsuranceIdInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInsuranceCommand.MESSAGE_USAGE));
    }

    /**
     * Tests the execution of {@code AddInsuranceCommandParser} with an invalid insurance ID.
     * This test verifies that when the specified insurance ID is invalid,
     * the command throws a {@code ParseException}.
     */
    @Test
    public void parse_invalidInsuranceId_throwsParseException() {
        String invalidInsuranceIdInput = INDEX_FIRST_CLIENT.getOneBased() + " " + PREFIX_INSURANCE_ID + "invalidId";
        assertParseFailure(parser, invalidInsuranceIdInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInsuranceCommand.MESSAGE_USAGE));
    }

    /**
     * Tests the execution of {@code AddInsuranceCommandParser} with missing PREFIX_INSURANCE_ID.
     * This test verifies that when PREFIX_INSURANCE_ID is not specified,
     * the command throws a {@code ParseException}.
     */
    @Test
    public void parse_missingInsuranceIdPrefix_throwsParseException() {
        String userInputWithoutInsuranceIdPrefix = INDEX_FIRST_CLIENT.getOneBased() + " ";
        assertParseFailure(parser, userInputWithoutInsuranceIdPrefix,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInsuranceCommand.MESSAGE_USAGE));
    }
}
