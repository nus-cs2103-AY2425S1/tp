package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_APPLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER;
import static seedu.address.model.delivery.Status.DELIVERED;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DELIVERY;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddDeliveryCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteDeliveryCommand;
import seedu.address.logic.commands.DeleteSupplierCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MarkDeliveryCommand;
import seedu.address.logic.commands.MarkSupplierCommand;
import seedu.address.logic.commands.SortSupplierCommand;
import seedu.address.logic.commands.UpcomingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.DateTime;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryIsUpcomingPredicate;
import seedu.address.model.delivery.Status;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.SupplierSortComparator;
import seedu.address.model.person.SupplierSortNameComparator;
import seedu.address.model.person.SupplierStatus;
import seedu.address.testutil.DeliveryBuilder;
import seedu.address.testutil.DeliveryUtil;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_add_delivery() throws Exception {
        Delivery delivery = new DeliveryBuilder().buildWithNullSender();
        AddDeliveryCommand command = (AddDeliveryCommand) parser.parseCommand(DeliveryUtil
                .getDeliveryCommand(delivery));
        assertEquals(new AddDeliveryCommand(delivery), command);
    }
    @Test
    public void parseCommand_upcoming() throws Exception {
        DateTime time = new DateTime(VALID_DATE_APPLE);
        DeliveryIsUpcomingPredicate predicate = new DeliveryIsUpcomingPredicate(time, Status.PENDING);
        UpcomingCommand command = (UpcomingCommand) parser.parseCommand(
                UpcomingCommand.COMMAND_WORD + " " + VALID_DATE_APPLE);
        assertEquals(new UpcomingCommand(predicate), command);
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
    public void parseCommand_delete() throws Exception {
        DeleteSupplierCommand command = (DeleteSupplierCommand) parser.parseCommand(
                DeleteSupplierCommand.COMMAND_WORD + " " + PREFIX_SUPPLIER + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteSupplierCommand(INDEX_FIRST_PERSON), command);
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
                DeleteCommand.COMMAND_WORD + " -s " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteSupplierCommand(INDEX_FIRST_PERSON), command);
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
                MarkSupplierCommand.COMMAND_WORD + " -s " + INDEX_FIRST_PERSON.getOneBased() + status);
        assertEquals(new MarkSupplierCommand(INDEX_FIRST_PERSON, new SupplierStatus(status)), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
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
}
