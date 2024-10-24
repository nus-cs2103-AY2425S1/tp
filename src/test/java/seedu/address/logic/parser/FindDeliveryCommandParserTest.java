package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindDeliveryCommand;
import seedu.address.logic.commands.MarkSupplierCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.Cost;
import seedu.address.model.delivery.DateTime;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryDatePredicate;
import seedu.address.model.delivery.DeliveryProductPredicate;
import seedu.address.model.delivery.DeliveryStatusMatchInputPredicate;
import seedu.address.model.delivery.Quantity;
import seedu.address.model.delivery.Status;
import seedu.address.model.delivery.SupplierIndex;
import seedu.address.model.product.Product;
import seedu.address.testutil.TypicalPersons;

public class FindDeliveryCommandParserTest {

    private final FindDeliveryCommandParser parser = new FindDeliveryCommandParser();

    @Test
    public void parse_noFilter_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeliveryCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDateTime_throwsParseException() {
        String userInput = " " + PREFIX_DATETIME + "20-10-2024 1000"; // Invalid date format
        assertParseFailure(parser, userInput, DateTime.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidSupplierIndex_throwsParseException() {
        String userInput = " " + PREFIX_SUPPLIER_INDEX + "invalidIndex"; // Invalid supplier index format
        assertParseFailure(parser, userInput, SupplierIndex.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidProduct_throwsParseException() {
        String userInput = " " + PREFIX_PRODUCT + "%%%"; // Invalid product format
        assertParseFailure(parser, userInput, Product.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validDateFilter_success() {
        String userInput = " " + PREFIX_DATETIME + "20-10-2024 10:00";
        DateTime expectedDateTime = new DateTime("20-10-2024 10:00");
        Predicate<Delivery> expectedPredicate = new DeliveryDatePredicate(expectedDateTime);

        FindDeliveryCommand expectedCommand = new FindDeliveryCommand(expectedPredicate, Optional.empty());

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidDateFilter_throwsParseException() {
        String userInput = " " + PREFIX_DATETIME + "invalid_date";
        assertParseFailure(parser, userInput, DateTime.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validStatusFilter_success() {
        String userInput = " " + PREFIX_STATUS + "PENDING";
        Predicate<Delivery> expectedPredicate = new DeliveryStatusMatchInputPredicate(Status.PENDING);

        FindDeliveryCommand expectedCommand = new FindDeliveryCommand(expectedPredicate, Optional.empty());

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidStatusFilter_throwsParseException() {
        String userInput = " " + PREFIX_STATUS + "INVALID";
        assertParseFailure(
                parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkSupplierCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validProductFilter_success() {
        String userInput = " " + PREFIX_PRODUCT + "Apples";
        Product expectedProduct = new Product("Apples");
        Predicate<Delivery> expectedPredicate = new DeliveryProductPredicate(expectedProduct);

        FindDeliveryCommand expectedCommand = new FindDeliveryCommand(expectedPredicate, Optional.empty());

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidProductFilter_throwsParseException() {
        String userInput = " " + PREFIX_PRODUCT + ""; // empty product name
        assertParseFailure(parser, userInput, Product.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validSupplierIndexFilter_success() throws ParseException {
        String userInput = " " + PREFIX_SUPPLIER_INDEX + "1";
        SupplierIndex expectedSupplierIndex = new SupplierIndex("1");

        FindDeliveryCommand command = new FindDeliveryCommand(x -> true, Optional.of(expectedSupplierIndex));

        FindDeliveryCommand parsedCommand = parser.parse(userInput);

        assertEquals(Optional.of(expectedSupplierIndex), parsedCommand.getSupplierIndex());

        assertTrue(parsedCommand.getPredicate() != null);
    }


    @Test
    public void parse_invalidSupplierIndexFilter_throwsParseException() {
        String userInput = " " + PREFIX_SUPPLIER_INDEX + "invalid_index";
        assertParseFailure(parser, userInput, SupplierIndex.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_multipleFilters_success() throws ParseException {
        String userInput = " " + PREFIX_DATETIME + " 20-10-2024 10:00 "
                + PREFIX_STATUS + " DELIVERED "
                + PREFIX_PRODUCT + " Apples";

        DateTime expectedDateTime = new DateTime("20-10-2024 10:00");
        Status expectedStatus = Status.DELIVERED;
        Product expectedProduct = new Product("Apples");

        Predicate<Delivery> expectedPredicate = new DeliveryDatePredicate(expectedDateTime)
                .and(new DeliveryStatusMatchInputPredicate(expectedStatus))
                .and(new DeliveryProductPredicate(expectedProduct));

        // Parse the user input to get the actual command
        FindDeliveryCommand actualCommand = parser.parse(userInput);

        // Create a mock delivery object that would match the expected filters
        Delivery matchingDelivery = new Delivery(
                expectedProduct, TypicalPersons.ALICE, expectedStatus, expectedDateTime,
                new Cost("10.00"), new Quantity("5 kg"));

        // Verify that the parsed command's predicate works as expected
        assertTrue(actualCommand.getPredicate().test(matchingDelivery),
                "The command's predicate should return true for a matching delivery");

        // Create another delivery that does not match the filters
        Delivery nonMatchingDelivery = new Delivery(new Product("Oranges"), TypicalPersons.BOB, Status.PENDING,
                new DateTime("19-10-2024 10:00"), new Cost("10.00"), new Quantity("5 kg"));

        // Verify that the parsed command's predicate correctly filters out non-matching deliveries
        assertTrue(!actualCommand.getPredicate().test(nonMatchingDelivery),
                "The command's predicate should return false for a non-matching delivery");
    }

}

