package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_LIST_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BREAD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER;
import static seedu.address.model.delivery.Status.DELIVERED;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TestUtil.prepareAfterPredicate;
import static seedu.address.testutil.TestUtil.prepareBeforePredicate;
import static seedu.address.testutil.TypicalDeliveries.APPLE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DELIVERY;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddDeliveryCommand;
import seedu.address.logic.commands.AddSupplierCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteDeliveryCommand;
import seedu.address.logic.commands.DeleteSupplierCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindSupplierCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListAllCommand;
import seedu.address.logic.commands.ListDeliveryCommand;
import seedu.address.logic.commands.ListSupplierCommand;
import seedu.address.logic.commands.MarkDeliveryCommand;
import seedu.address.logic.commands.MarkSupplierCommand;
import seedu.address.logic.commands.SortDeliveryCommand;
import seedu.address.logic.commands.SortSupplierCommand;
import seedu.address.logic.commands.UpcomingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryIsUpcomingAfterPredicate;
import seedu.address.model.delivery.DeliveryIsUpcomingBeforePredicate;
import seedu.address.model.delivery.DeliverySortCostComparator;
import seedu.address.model.delivery.DeliveryWrapper;
import seedu.address.model.delivery.Status;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.SupplierSortComparator;
import seedu.address.model.supplier.SupplierSortNameComparator;
import seedu.address.model.supplier.SupplierStatus;
import seedu.address.model.supplier.predicates.NameContainsKeywordPredicate;
import seedu.address.model.supplier.predicates.ProductContainsKeywordPredicate;
import seedu.address.testutil.DeliveryUtil;
import seedu.address.testutil.SupplierBuilder;
import seedu.address.testutil.SupplierUtil;
import seedu.address.testutil.TypicalDeliveryWrappers;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add_supplier() throws Exception {
        Supplier supplier = new SupplierBuilder().build();
        AddSupplierCommand command = (AddSupplierCommand) parser.parseCommand(SupplierUtil.getAddCommand(supplier));
        assertEquals(new AddSupplierCommand(supplier), command);
    }

    @Test
    public void parseCommand_add_delivery() throws Exception {
        DeliveryWrapper deliveryWrapper = TypicalDeliveryWrappers.getNullWrapper();
        AddDeliveryCommand command = (AddDeliveryCommand) parser.parseCommand(DeliveryUtil
                .getDeliveryCommand(APPLE));
        assertEquals(new AddDeliveryCommand(deliveryWrapper), command);
    }
    @Test
    public void parseCommand_upcoming() throws Exception {
        DeliveryIsUpcomingBeforePredicate predicateBefore = prepareBeforePredicate(VALID_DATE_BREAD, Status.PENDING);
        DeliveryIsUpcomingAfterPredicate predicateAfter = prepareAfterPredicate(VALID_DATE_APPLE, Status.PENDING);
        List<Predicate<Delivery>> predicates = new ArrayList<>();
        predicates.add(predicateAfter);
        predicates.add(predicateBefore);
        UpcomingCommand command = (UpcomingCommand) parser.parseCommand(
                UpcomingCommand.COMMAND_WORD + " " + PREFIX_START_DATE + VALID_DATE_APPLE + " " + PREFIX_END_DATE
                        + " " + VALID_DATE_BREAD);
        assertEquals(new UpcomingCommand(predicates), command);
    }

    @Test
    public void parseCommand_upcomingWithNoarguments() throws Exception {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UpcomingCommand.MESSAGE_USAGE), () -> parser.parseCommand(UpcomingCommand.COMMAND_WORD));
    }
    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete_delivery() throws Exception {
        DeleteDeliveryCommand command = (DeleteDeliveryCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + PREFIX_DELIVERY + " " + INDEX_FIRST_DELIVERY.getOneBased());
        assertEquals(new DeleteDeliveryCommand(INDEX_FIRST_DELIVERY), command);
    }
    @Test
    public void parseCommand_delete_supplier() throws Exception {
        DeleteSupplierCommand command = (DeleteSupplierCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " -s " + INDEX_FIRST_SUPPLIER.getOneBased());
        assertEquals(new DeleteSupplierCommand(INDEX_FIRST_SUPPLIER), command);
    }
    @Test
    public void parseCommand_mark_delivery() throws Exception {
        MarkDeliveryCommand command = (MarkDeliveryCommand) parser.parseCommand(
                MarkDeliveryCommand.COMMAND_WORD + " -d " + INDEX_FIRST_DELIVERY.getOneBased() + " DELIVERED");
        assertEquals(new MarkDeliveryCommand(INDEX_FIRST_DELIVERY, DELIVERED), command);
    }
    @Test
    public void parseCommand_mark_supplier() throws Exception {
        final String status = " active";
        MarkSupplierCommand command = (MarkSupplierCommand) parser.parseCommand(
                MarkSupplierCommand.COMMAND_WORD + " -s " + INDEX_FIRST_SUPPLIER.getOneBased() + status);
        assertEquals(new MarkSupplierCommand(INDEX_FIRST_SUPPLIER, new SupplierStatus(status)), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findSupplier() throws Exception {
        String commandInput = "find -s n/Linkes pro/Iphone";
        List<Predicate<Supplier>> listOfPredicates = new ArrayList<>();
        listOfPredicates.add(new NameContainsKeywordPredicate("Linkes"));
        listOfPredicates.add(new ProductContainsKeywordPredicate("Iphone"));

        FindSupplierCommand command = (FindSupplierCommand) parser.parseCommand(commandInput);
        assertEquals(new FindSupplierCommand(listOfPredicates), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListAllCommand.COMMAND_WORD + " -a") instanceof ListAllCommand);
        assertThrows(ParseException.class, MESSAGE_INVALID_LIST_COMMAND_FORMAT, () ->
                parser.parseCommand(ListAllCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_list_supplier() throws Exception {
        assertTrue(parser.parseCommand(ListSupplierCommand.COMMAND_WORD + " " + PREFIX_SUPPLIER)
                instanceof ListSupplierCommand);
    }

    @Test
    public void parseCommand_list_delivery() throws Exception {
        assertTrue(parser.parseCommand(ListDeliveryCommand.COMMAND_WORD + " " + PREFIX_DELIVERY)
                instanceof ListDeliveryCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
    @Test
    public void parseCommand_sortSupplier() throws Exception {
        SupplierSortComparator comparator = new SupplierSortNameComparator(new SortOrder("a"));
        SortSupplierCommand command = (SortSupplierCommand) parser.parseCommand(
                SortSupplierCommand.COMMAND_WORD + " " + PREFIX_SUPPLIER + " "
                        + PREFIX_SORT_ORDER + "a " + PREFIX_SORT_BY + "n");
        assertEquals(new SortSupplierCommand(comparator), command);
    }
    @Test
    public void parseCommand_sortDelivery() throws Exception {
        DeliverySortCostComparator comparator = new DeliverySortCostComparator(new SortOrder("a"));
        SortDeliveryCommand command = (SortDeliveryCommand) parser.parseCommand(
                SortDeliveryCommand.COMMAND_WORD + " " + PREFIX_DELIVERY + " "
                        + PREFIX_SORT_ORDER + "a " + PREFIX_SORT_BY + "c");
        assertEquals(new SortDeliveryCommand(comparator), command);
    }
}
