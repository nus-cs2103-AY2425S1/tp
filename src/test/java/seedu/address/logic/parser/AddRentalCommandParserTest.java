package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.CUSTOMER_LIST_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.CUSTOMER_LIST_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.DEPOSIT_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.DEPOSIT_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CUSTOMER_LIST_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEPOSIT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MONTHLY_RENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RENTAL_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RENTAL_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RENT_DUE_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MONTHLY_RENT_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.MONTHLY_RENT_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.RENTAL_END_DATE_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.RENTAL_END_DATE_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.RENTAL_START_DATE_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.RENTAL_START_DATE_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.RENT_DUE_DATE_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.RENT_DUE_DATE_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CUSTOMER_LIST_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEPOSIT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MONTHLY_RENT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENTAL_END_DATE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENTAL_START_DATE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RENT_DUE_DATE_ONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER_LIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPOSIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHLY_RENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTAL_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENT_DUE_DATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalRentalInformation.RENTAL_ONE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddRentalCommand;
import seedu.address.model.rentalinformation.Address;
import seedu.address.model.rentalinformation.CustomerList;
import seedu.address.model.rentalinformation.Deposit;
import seedu.address.model.rentalinformation.MonthlyRent;
import seedu.address.model.rentalinformation.RentDueDate;
import seedu.address.model.rentalinformation.RentalDate;
import seedu.address.model.rentalinformation.RentalInformation;
import seedu.address.testutil.RentalInformationBuilder;

public class AddRentalCommandParserTest {
    private AddRentalCommandParser parser = new AddRentalCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        RentalInformation expectedRental = new RentalInformationBuilder(RENTAL_ONE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " 1" + ADDRESS_DESC_ONE + RENTAL_START_DATE_DESC_ONE
                + RENTAL_END_DATE_DESC_ONE + RENT_DUE_DATE_DESC_ONE + MONTHLY_RENT_DESC_ONE + DEPOSIT_DESC_ONE
                + CUSTOMER_LIST_DESC_ONE, new AddRentalCommand(INDEX_FIRST_PERSON, expectedRental));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedRentalString = ADDRESS_DESC_ONE + RENTAL_START_DATE_DESC_ONE
                + RENTAL_END_DATE_DESC_ONE + RENT_DUE_DATE_DESC_ONE + MONTHLY_RENT_DESC_ONE + DEPOSIT_DESC_ONE
                + CUSTOMER_LIST_DESC_ONE;

        // multiple address
        assertParseFailure(parser, "1" + ADDRESS_DESC_TWO + validExpectedRentalString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple rental start date
        assertParseFailure(parser, "1" + RENTAL_START_DATE_DESC_TWO + validExpectedRentalString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RENTAL_START_DATE));

        // multiple rental end date
        assertParseFailure(parser, "1" + RENTAL_END_DATE_DESC_TWO + validExpectedRentalString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RENTAL_END_DATE));

        // multiple rent due date
        assertParseFailure(parser, "1" + RENT_DUE_DATE_DESC_TWO + validExpectedRentalString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RENT_DUE_DATE));

        // multiple monthly rent
        assertParseFailure(parser, "1" + MONTHLY_RENT_DESC_TWO + validExpectedRentalString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MONTHLY_RENT));

        // multiple deposit
        assertParseFailure(parser, "1" + DEPOSIT_DESC_TWO + validExpectedRentalString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DEPOSIT));

        // multiple customer list
        assertParseFailure(parser, "1" + CUSTOMER_LIST_DESC_TWO + validExpectedRentalString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CUSTOMER_LIST));

        // multiple fields repeated
        assertParseFailure(parser,
                "1" + validExpectedRentalString + DEPOSIT_DESC_TWO + MONTHLY_RENT_DESC_ONE + RENT_DUE_DATE_DESC_ONE
                        + validExpectedRentalString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS, PREFIX_RENTAL_START_DATE,
                        PREFIX_RENTAL_END_DATE, PREFIX_RENT_DUE_DATE, PREFIX_MONTHLY_RENT, PREFIX_DEPOSIT,
                        PREFIX_CUSTOMER_LIST));

        // invalid value followed by valid value

        // invalid address
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC + validExpectedRentalString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid rental start date
        assertParseFailure(parser, "1" + INVALID_RENTAL_START_DATE_DESC + validExpectedRentalString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RENTAL_START_DATE));

        // invalid rental end date
        assertParseFailure(parser, "1" + INVALID_RENTAL_END_DATE_DESC + validExpectedRentalString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RENTAL_END_DATE));

        // invalid rent due date
        assertParseFailure(parser, "1" + INVALID_RENT_DUE_DATE_DESC + validExpectedRentalString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RENT_DUE_DATE));

        // invalid monthly rent
        assertParseFailure(parser, "1" + INVALID_MONTHLY_RENT_DESC + validExpectedRentalString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MONTHLY_RENT));

        // invalid deposit
        assertParseFailure(parser, "1" + INVALID_DEPOSIT_DESC + validExpectedRentalString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DEPOSIT));

        // invalid customer list
        assertParseFailure(parser, "1" + INVALID_CUSTOMER_LIST_DESC + validExpectedRentalString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CUSTOMER_LIST));

        // valid value followed by invalid value

        // invalid address
        assertParseFailure(parser, "1" + validExpectedRentalString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid rental start date
        assertParseFailure(parser, "1" + validExpectedRentalString + INVALID_RENTAL_START_DATE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RENTAL_START_DATE));

        // invalid rental end date
        assertParseFailure(parser, "1" + validExpectedRentalString + INVALID_RENTAL_END_DATE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RENTAL_END_DATE));

        // invalid rent due date
        assertParseFailure(parser, "1" + validExpectedRentalString + INVALID_RENT_DUE_DATE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_RENT_DUE_DATE));

        // invalid monthly rent
        assertParseFailure(parser, "1" + validExpectedRentalString + INVALID_MONTHLY_RENT_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MONTHLY_RENT));

        // invalid deposit
        assertParseFailure(parser, "1" + validExpectedRentalString + INVALID_DEPOSIT_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DEPOSIT));

        // invalid customer list
        assertParseFailure(parser, "1" + validExpectedRentalString + INVALID_CUSTOMER_LIST_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CUSTOMER_LIST));

    }

    @Test
    public void parse_missingAddressPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRentalCommand.MESSAGE_USAGE);

        // missing address prefix
        assertParseFailure(parser, "1" + VALID_ADDRESS_ONE, expectedMessage);

        assertParseFailure(parser, "1" + VALID_ADDRESS_ONE + RENTAL_START_DATE_DESC_ONE, expectedMessage);

        assertParseFailure(parser, "1" + VALID_ADDRESS_ONE + RENTAL_START_DATE_DESC_ONE
                + RENTAL_END_DATE_DESC_ONE, expectedMessage);

        assertParseFailure(parser, "1" + VALID_ADDRESS_ONE + RENTAL_START_DATE_DESC_ONE
                + RENTAL_END_DATE_DESC_ONE + RENT_DUE_DATE_DESC_ONE, expectedMessage);

        assertParseFailure(parser, "1" + VALID_ADDRESS_ONE + RENTAL_START_DATE_DESC_ONE
                + RENTAL_END_DATE_DESC_ONE + RENT_DUE_DATE_DESC_ONE + MONTHLY_RENT_DESC_ONE, expectedMessage);

        assertParseFailure(parser, "1" + VALID_ADDRESS_ONE + RENTAL_START_DATE_DESC_ONE
                + RENTAL_END_DATE_DESC_ONE + RENT_DUE_DATE_DESC_ONE + MONTHLY_RENT_DESC_ONE + DEPOSIT_DESC_ONE,
                expectedMessage);

        assertParseFailure(parser, "1" + VALID_ADDRESS_ONE + RENTAL_START_DATE_DESC_ONE
                + RENTAL_END_DATE_DESC_ONE + RENT_DUE_DATE_DESC_ONE + MONTHLY_RENT_DESC_ONE + DEPOSIT_DESC_ONE
                + CUSTOMER_LIST_DESC_ONE, expectedMessage);
    }

    @Test
    public void parse_missingOtherFields_success() {
        RentalInformation expectedRentalInformation;

        // missing rental start date
        expectedRentalInformation = new RentalInformationBuilder().withAddress(VALID_ADDRESS_ONE)
                .withRentalEndDate(VALID_RENTAL_END_DATE_ONE).withRentDueDate(VALID_RENT_DUE_DATE_ONE)
                .withMonthlyRent(VALID_MONTHLY_RENT_ONE).withDeposit(VALID_DEPOSIT_ONE)
                .withCustomerList(VALID_CUSTOMER_LIST_ONE).build();
        assertParseSuccess(parser, "1" + ADDRESS_DESC_ONE + RENTAL_END_DATE_DESC_ONE + RENT_DUE_DATE_DESC_ONE
                + MONTHLY_RENT_DESC_ONE + DEPOSIT_DESC_ONE + CUSTOMER_LIST_DESC_ONE,
                new AddRentalCommand(INDEX_FIRST_PERSON, expectedRentalInformation));

        // missing rental end date
        expectedRentalInformation = new RentalInformationBuilder().withAddress(VALID_ADDRESS_ONE)
                .withRentalStartDate(VALID_RENTAL_START_DATE_ONE).withRentDueDate(VALID_RENT_DUE_DATE_ONE)
                .withMonthlyRent(VALID_MONTHLY_RENT_ONE).withDeposit(VALID_DEPOSIT_ONE)
                .withCustomerList(VALID_CUSTOMER_LIST_ONE).build();
        assertParseSuccess(parser, "1" + ADDRESS_DESC_ONE + RENTAL_START_DATE_DESC_ONE + RENT_DUE_DATE_DESC_ONE
                + MONTHLY_RENT_DESC_ONE + DEPOSIT_DESC_ONE + CUSTOMER_LIST_DESC_ONE,
                new AddRentalCommand(INDEX_FIRST_PERSON, expectedRentalInformation));

        // missing rent due date
        expectedRentalInformation = new RentalInformationBuilder().withAddress(VALID_ADDRESS_ONE)
                .withRentalStartDate(VALID_RENTAL_START_DATE_ONE).withRentalEndDate(VALID_RENTAL_END_DATE_ONE)
                .withMonthlyRent(VALID_MONTHLY_RENT_ONE).withDeposit(VALID_DEPOSIT_ONE)
                .withCustomerList(VALID_CUSTOMER_LIST_ONE).build();
        assertParseSuccess(parser, "1" + ADDRESS_DESC_ONE + RENTAL_START_DATE_DESC_ONE
                + RENTAL_END_DATE_DESC_ONE + MONTHLY_RENT_DESC_ONE + DEPOSIT_DESC_ONE + CUSTOMER_LIST_DESC_ONE,
                new AddRentalCommand(INDEX_FIRST_PERSON, expectedRentalInformation));

        // missing monthly rent
        expectedRentalInformation = new RentalInformationBuilder().withAddress(VALID_ADDRESS_ONE)
                .withRentalStartDate(VALID_RENTAL_START_DATE_ONE).withRentalEndDate(VALID_RENTAL_END_DATE_ONE)
                .withRentDueDate(VALID_RENT_DUE_DATE_ONE).withDeposit(VALID_DEPOSIT_ONE)
                .withCustomerList(VALID_CUSTOMER_LIST_ONE).build();
        assertParseSuccess(parser, "1" + ADDRESS_DESC_ONE + RENTAL_START_DATE_DESC_ONE
                + RENTAL_END_DATE_DESC_ONE + RENT_DUE_DATE_DESC_ONE + DEPOSIT_DESC_ONE + CUSTOMER_LIST_DESC_ONE,
                new AddRentalCommand(INDEX_FIRST_PERSON, expectedRentalInformation));

        // missing deposit
        expectedRentalInformation = new RentalInformationBuilder().withAddress(VALID_ADDRESS_ONE)
                .withRentalStartDate(VALID_RENTAL_START_DATE_ONE).withRentalEndDate(VALID_RENTAL_END_DATE_ONE)
                .withRentDueDate(VALID_RENT_DUE_DATE_ONE).withMonthlyRent(VALID_MONTHLY_RENT_ONE)
                .withCustomerList(VALID_CUSTOMER_LIST_ONE).build();
        assertParseSuccess(parser, "1" + ADDRESS_DESC_ONE + RENTAL_START_DATE_DESC_ONE
                + RENTAL_END_DATE_DESC_ONE + RENT_DUE_DATE_DESC_ONE + MONTHLY_RENT_DESC_ONE + CUSTOMER_LIST_DESC_ONE,
                new AddRentalCommand(INDEX_FIRST_PERSON, expectedRentalInformation));

        // missing customer list
        expectedRentalInformation = new RentalInformationBuilder().withAddress(VALID_ADDRESS_ONE)
                .withRentalStartDate(VALID_RENTAL_START_DATE_ONE).withRentalEndDate(VALID_RENTAL_END_DATE_ONE)
                .withRentDueDate(VALID_RENT_DUE_DATE_ONE).withMonthlyRent(VALID_MONTHLY_RENT_ONE)
                .withDeposit(VALID_DEPOSIT_ONE).build();
        assertParseSuccess(parser, "1" + ADDRESS_DESC_ONE + RENTAL_START_DATE_DESC_ONE
                + RENTAL_END_DATE_DESC_ONE + RENT_DUE_DATE_DESC_ONE + MONTHLY_RENT_DESC_ONE + DEPOSIT_DESC_ONE,
                new AddRentalCommand(INDEX_FIRST_PERSON, expectedRentalInformation));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid address
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC + RENTAL_START_DATE_DESC_ONE
                + RENTAL_END_DATE_DESC_ONE + RENT_DUE_DATE_DESC_ONE + MONTHLY_RENT_DESC_ONE + DEPOSIT_DESC_ONE
                + CUSTOMER_LIST_DESC_ONE, Address.MESSAGE_CONSTRAINTS);

        // invalid rental start date
        assertParseFailure(parser, "1" + ADDRESS_DESC_ONE + INVALID_RENTAL_START_DATE_DESC
                + RENTAL_END_DATE_DESC_ONE + RENT_DUE_DATE_DESC_ONE + MONTHLY_RENT_DESC_ONE + DEPOSIT_DESC_ONE
                + CUSTOMER_LIST_DESC_ONE, RentalDate.MESSAGE_CONSTRAINTS);

        // invalid rental end date
        assertParseFailure(parser, "1" + ADDRESS_DESC_ONE + RENTAL_START_DATE_DESC_ONE
                + INVALID_RENTAL_END_DATE_DESC + RENT_DUE_DATE_DESC_ONE + MONTHLY_RENT_DESC_ONE + DEPOSIT_DESC_ONE
                + CUSTOMER_LIST_DESC_ONE, RentalDate.MESSAGE_CONSTRAINTS);

        // invalid rent due date
        assertParseFailure(parser, "1" + ADDRESS_DESC_ONE + RENTAL_START_DATE_DESC_ONE
                + RENTAL_END_DATE_DESC_ONE + INVALID_RENT_DUE_DATE_DESC + MONTHLY_RENT_DESC_ONE + DEPOSIT_DESC_ONE
                + CUSTOMER_LIST_DESC_ONE, RentDueDate.MESSAGE_CONSTRAINTS);

        // invalid monthly rent
        assertParseFailure(parser, "1" + ADDRESS_DESC_ONE + RENTAL_START_DATE_DESC_ONE
                + RENTAL_END_DATE_DESC_ONE + RENT_DUE_DATE_DESC_ONE + INVALID_MONTHLY_RENT_DESC + DEPOSIT_DESC_ONE
                + CUSTOMER_LIST_DESC_ONE, MonthlyRent.MESSAGE_CONSTRAINTS);

        // invalid deposit
        assertParseFailure(parser, "1" + ADDRESS_DESC_ONE + RENTAL_START_DATE_DESC_ONE
                + RENTAL_END_DATE_DESC_ONE + RENT_DUE_DATE_DESC_ONE + MONTHLY_RENT_DESC_ONE + INVALID_DEPOSIT_DESC
                + CUSTOMER_LIST_DESC_ONE, Deposit.MESSAGE_CONSTRAINTS);

        // invalid customer list
        assertParseFailure(parser, "1" + ADDRESS_DESC_ONE + RENTAL_START_DATE_DESC_ONE
                + RENTAL_END_DATE_DESC_ONE + RENT_DUE_DATE_DESC_ONE + MONTHLY_RENT_DESC_ONE + DEPOSIT_DESC_ONE
                + INVALID_CUSTOMER_LIST_DESC, CustomerList.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + "1" + ADDRESS_DESC_ONE + RENTAL_START_DATE_DESC_ONE
                        + RENTAL_END_DATE_DESC_ONE + RENT_DUE_DATE_DESC_ONE + MONTHLY_RENT_DESC_ONE + DEPOSIT_DESC_ONE
                        + INVALID_CUSTOMER_LIST_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRentalCommand.MESSAGE_USAGE));
    }
}
