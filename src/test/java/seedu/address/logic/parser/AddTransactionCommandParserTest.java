package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EMPTY_AMOUNT;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_DATE;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_DESCRIPTION;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_OTHER_PARTY;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_FORMAT;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.INCORRECT_DATE_FORMAT_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AMOUNT_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OTHER_PARTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OTHER_PARTY_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.WHITE_SPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OTHER_PARTY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.model.client.Transaction;

public class AddTransactionCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTransactionCommand.MESSAGE_USAGE);
    private AddTransactionCommandParser parser = new AddTransactionCommandParser();

    @Test
    public void parse_missingParts_failure() {

        //no index specified
        String userInputWithNoIndex = VALID_DESCRIPTION_INPUT + VALID_AMOUNT_INPUT + VALID_OTHER_PARTY_INPUT
                + VALID_DATE_INPUT;
        assertParseFailure(parser, userInputWithNoIndex, MESSAGE_INVALID_FORMAT);

        //no description specified
        String userInputWithNoDescription = "1" + VALID_AMOUNT_INPUT + VALID_OTHER_PARTY_INPUT
                + VALID_DATE_INPUT;
        assertParseFailure(parser, userInputWithNoDescription, MESSAGE_INVALID_FORMAT);

        //no amount specified
        String userInputWithNoAmount = "1" + VALID_DESCRIPTION_INPUT + VALID_OTHER_PARTY_INPUT
                + VALID_DATE_INPUT;
        assertParseFailure(parser, userInputWithNoAmount, MESSAGE_INVALID_FORMAT);

        //no other party specified
        String userInputWithNoOtherParty = "1" + VALID_DESCRIPTION_INPUT + VALID_AMOUNT_INPUT
                + VALID_DATE_INPUT;
        assertParseFailure(parser, userInputWithNoOtherParty, MESSAGE_INVALID_FORMAT);

        //no date specified
        String userInputWithNoDate = "1" + VALID_DESCRIPTION_INPUT + VALID_AMOUNT_INPUT
                + VALID_OTHER_PARTY_INPUT;
        assertParseFailure(parser, userInputWithNoDate, MESSAGE_INVALID_FORMAT);

        //no index and no fields specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_blankParts_failure() {

        //blank description
        String userInputWithBlankDescription = "1" + WHITE_SPACE + PREFIX_DESCRIPTION + WHITE_SPACE + VALID_AMOUNT_INPUT
                + VALID_OTHER_PARTY_INPUT + VALID_DATE_INPUT;
        assertParseFailure(parser, userInputWithBlankDescription, MESSAGE_EMPTY_DESCRIPTION);

        //blank amount
        String userInputWithBlankAmount = "1" + VALID_DESCRIPTION_INPUT + WHITE_SPACE + PREFIX_AMOUNT + WHITE_SPACE
                + VALID_OTHER_PARTY_INPUT + VALID_DATE_INPUT;
        assertParseFailure(parser, userInputWithBlankAmount, MESSAGE_EMPTY_AMOUNT);

        //blank other party
        String userInputWithBlankOtherParty = "1" + VALID_DESCRIPTION_INPUT + VALID_AMOUNT_INPUT
                + WHITE_SPACE + PREFIX_OTHER_PARTY + WHITE_SPACE + VALID_DATE_INPUT;
        assertParseFailure(parser, userInputWithBlankOtherParty, MESSAGE_EMPTY_OTHER_PARTY);

        //blank date
        String userInputWithBlankDate = "1" + VALID_DESCRIPTION_INPUT + VALID_AMOUNT_INPUT
                + VALID_OTHER_PARTY_INPUT + WHITE_SPACE + PREFIX_DATE + WHITE_SPACE;
        assertParseFailure(parser, userInputWithBlankDate, MESSAGE_EMPTY_DATE);
    }


    @Test
    public void parse_invalidPreamble_failure() {

        String userInputWithNoIndex = " " + VALID_DESCRIPTION_INPUT + VALID_AMOUNT_INPUT + VALID_OTHER_PARTY_INPUT
                + VALID_DATE_INPUT;

        // negative index
        assertParseFailure(parser, "-5" + userInputWithNoIndex, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + userInputWithNoIndex, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string" + userInputWithNoIndex, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string" + userInputWithNoIndex, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidAmount_failure() {
        Index targetIndex = INDEX_SECOND_CLIENT;
        String userInputWithInvalidAmount = targetIndex.getOneBased() + VALID_DESCRIPTION_INPUT + INVALID_AMOUNT_INPUT
                + VALID_OTHER_PARTY_INPUT + VALID_DATE_INPUT;
        assertParseFailure(parser, userInputWithInvalidAmount, Transaction.MESSAGE_CONSTRAINTS);
    }


    @Test
    public void parse_incorrectDateFormat_failure() {
        Index targetIndex = INDEX_SECOND_CLIENT;
        String userInputWithInvalidDate = targetIndex.getOneBased() + VALID_DESCRIPTION_INPUT + VALID_AMOUNT_INPUT
                + VALID_OTHER_PARTY_INPUT + INCORRECT_DATE_FORMAT_INPUT;
        assertParseFailure(parser, userInputWithInvalidDate, MESSAGE_INVALID_DATE_FORMAT);
    }


    @Test
    public void parse_invalidDate_failure() {
        Index targetIndex = INDEX_SECOND_CLIENT;
        String userInputWithInvalidDate = targetIndex.getOneBased() + VALID_DESCRIPTION_INPUT + VALID_AMOUNT_INPUT
                + VALID_OTHER_PARTY_INPUT + INVALID_DATE_INPUT;
        assertParseFailure(parser, userInputWithInvalidDate, MESSAGE_INVALID_DATE_FORMAT);
    }


    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_CLIENT;
        String userInput = targetIndex.getOneBased() + VALID_DESCRIPTION_INPUT + VALID_AMOUNT_INPUT
                + VALID_OTHER_PARTY_INPUT + VALID_DATE_INPUT;

        Transaction transaction = new Transaction(VALID_DESCRIPTION, VALID_AMOUNT, VALID_OTHER_PARTY, VALID_DATE);

        AddTransactionCommand expectedCommand = new AddTransactionCommand(targetIndex, transaction);

        assertParseSuccess(parser, userInput, expectedCommand);
    }


}
