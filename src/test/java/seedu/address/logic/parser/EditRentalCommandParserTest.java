package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.CUSTOMER_LIST_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.CUSTOMER_LIST_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.DEPOSIT_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.DEPOSIT_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEPOSIT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MONTHLY_RENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RENTAL_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RENTAL_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RENT_DUE_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MONTHLY_RENT_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.MONTHLY_RENT_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.RENTAL_END_DATE_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.RENTAL_START_DATE_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.RENT_DUE_DATE_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CUSTOMER_LIST_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEPOSIT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTHLY_RENT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENTAL_END_DATE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENTAL_START_DATE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENT_DUE_DATE_ONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPOSIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHLY_RENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENT_DUE_DATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RENTAL;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditRentalCommand;
import seedu.address.logic.commands.EditRentalCommand.EditRentalDescriptor;
import seedu.address.model.rentalinformation.Deposit;
import seedu.address.model.rentalinformation.MonthlyRent;
import seedu.address.model.rentalinformation.RentDueDate;
import seedu.address.model.rentalinformation.RentalDate;
import seedu.address.testutil.EditRentalDescriptorBuilder;

public class EditRentalCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRentalCommand.MESSAGE_USAGE);

    private EditRentalCommandParser parser = new EditRentalCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_ADDRESS_ONE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 r/1" + ADDRESS_DESC_ONE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 r/1" + ADDRESS_DESC_ONE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid rental index
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRentalCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "1 r/a" + VALID_RENTAL_START_DATE_ONE, expectedMessage);
        // invalid rental start date
        assertParseFailure(parser, "1 r/1" + INVALID_RENTAL_START_DATE_DESC, RentalDate.MESSAGE_CONSTRAINTS);
        // invalid rental end date
        assertParseFailure(parser, "1 r/1" + INVALID_RENTAL_END_DATE_DESC, RentalDate.MESSAGE_CONSTRAINTS);
        // invalid rent due date
        assertParseFailure(parser, "1 r/1" + INVALID_RENT_DUE_DATE_DESC, RentDueDate.MESSAGE_CONSTRAINTS);
        // invalid monthly rent
        assertParseFailure(parser, "1 r/1" + INVALID_MONTHLY_RENT_DESC, MonthlyRent.MESSAGE_CONSTRAINTS);
        // invalid deposit
        assertParseFailure(parser, "1 r/1" + INVALID_DEPOSIT_DESC, Deposit.MESSAGE_CONSTRAINTS);

        // invalid rental start date followed by valid rental end date
        assertParseFailure(parser, "1 r/1" + INVALID_RENTAL_START_DATE_DESC + RENTAL_END_DATE_DESC_ONE,
                RentalDate.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " r/1" + ADDRESS_DESC_ONE + RENTAL_START_DATE_DESC_ONE
                + RENTAL_END_DATE_DESC_ONE + RENT_DUE_DATE_DESC_ONE + MONTHLY_RENT_DESC_ONE + DEPOSIT_DESC_ONE
                + CUSTOMER_LIST_DESC_ONE;

        EditRentalDescriptor descriptor = new EditRentalDescriptorBuilder().withAddress(VALID_ADDRESS_ONE)
                .withRentalStartDate(VALID_RENTAL_START_DATE_ONE).withRentalEndDate(VALID_RENTAL_END_DATE_ONE)
                .withRentDueDate(VALID_RENT_DUE_DATE_ONE).withMonthlyRent(VALID_MONTHLY_RENT_ONE)
                .withDeposit(VALID_DEPOSIT_ONE).withCustomerList(VALID_CUSTOMER_LIST_ONE).build();
        EditRentalCommand expectedCommand = new EditRentalCommand(targetIndex, INDEX_FIRST_RENTAL, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " r/1" + RENTAL_START_DATE_DESC_ONE + MONTHLY_RENT_DESC_ONE;

        EditRentalDescriptor descriptor = new EditRentalDescriptorBuilder()
                .withRentalStartDate(VALID_RENTAL_START_DATE_ONE).withMonthlyRent(VALID_MONTHLY_RENT_ONE).build();
        EditRentalCommand expectedCommand = new EditRentalCommand(targetIndex, INDEX_FIRST_RENTAL, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // address
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + " r/1" + ADDRESS_DESC_ONE;
        EditRentalDescriptor descriptor = new EditRentalDescriptorBuilder().withAddress(VALID_ADDRESS_ONE).build();
        EditRentalCommand expectedCommand = new EditRentalCommand(targetIndex, INDEX_FIRST_RENTAL, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // rental start date
        userInput = targetIndex.getOneBased() + " r/1" + RENTAL_START_DATE_DESC_ONE;
        descriptor = new EditRentalDescriptorBuilder().withRentalStartDate(VALID_RENTAL_START_DATE_ONE).build();
        expectedCommand = new EditRentalCommand(targetIndex, INDEX_FIRST_RENTAL, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // rental end date
        userInput = targetIndex.getOneBased() + " r/1" + RENTAL_END_DATE_DESC_ONE;
        descriptor = new EditRentalDescriptorBuilder().withRentalEndDate(VALID_RENTAL_END_DATE_ONE).build();
        expectedCommand = new EditRentalCommand(targetIndex, INDEX_FIRST_RENTAL, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // rent due date
        userInput = targetIndex.getOneBased() + " r/1" + RENT_DUE_DATE_DESC_ONE;
        descriptor = new EditRentalDescriptorBuilder().withRentDueDate(VALID_RENT_DUE_DATE_ONE).build();
        expectedCommand = new EditRentalCommand(targetIndex, INDEX_FIRST_RENTAL, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // monthly rent
        userInput = targetIndex.getOneBased() + " r/1" + MONTHLY_RENT_DESC_ONE;
        descriptor = new EditRentalDescriptorBuilder().withMonthlyRent(VALID_MONTHLY_RENT_ONE).build();
        expectedCommand = new EditRentalCommand(targetIndex, INDEX_FIRST_RENTAL, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // deposit
        userInput = targetIndex.getOneBased() + " r/1" + DEPOSIT_DESC_ONE;
        descriptor = new EditRentalDescriptorBuilder().withDeposit(VALID_DEPOSIT_ONE).build();
        expectedCommand = new EditRentalCommand(targetIndex, INDEX_FIRST_RENTAL, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // customer list
        userInput = targetIndex.getOneBased() + " r/1" + CUSTOMER_LIST_DESC_ONE;
        descriptor = new EditRentalDescriptorBuilder().withCustomerList(VALID_CUSTOMER_LIST_ONE).build();
        expectedCommand = new EditRentalCommand(targetIndex, INDEX_FIRST_RENTAL, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " r/1";

        assertParseFailure(parser, userInput, EditRentalCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " r/1" + RENTAL_START_DATE_DESC_ONE
                + INVALID_RENTAL_START_DATE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RENTAL_START_DATE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + " r/1" + INVALID_RENTAL_START_DATE_DESC + RENTAL_START_DATE_DESC_ONE;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RENTAL_START_DATE));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + MONTHLY_RENT_DESC_ONE + DEPOSIT_DESC_ONE + CUSTOMER_LIST_DESC_ONE
                + MONTHLY_RENT_DESC_TWO + DEPOSIT_DESC_TWO + CUSTOMER_LIST_DESC_TWO;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MONTHLY_RENT, PREFIX_DEPOSIT,
                        PREFIX_CUSTOMER_LIST));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_RENT_DUE_DATE_DESC + INVALID_DEPOSIT_DESC
                + INVALID_RENT_DUE_DATE_DESC + INVALID_DEPOSIT_DESC;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DEPOSIT, PREFIX_RENT_DUE_DATE));
    }
}
