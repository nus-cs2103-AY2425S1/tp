package seedu.sellsavvy.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sellsavvy.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.sellsavvy.testutil.Assert.assertThrows;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.logic.commands.generalcommands.ClearCommand;
import seedu.sellsavvy.logic.commands.generalcommands.ExitCommand;
import seedu.sellsavvy.logic.commands.generalcommands.HelpCommand;
import seedu.sellsavvy.logic.commands.ordercommands.AddOrderCommand;
import seedu.sellsavvy.logic.commands.ordercommands.DeleteOrderCommand;
import seedu.sellsavvy.logic.commands.ordercommands.EditOrderCommand;
import seedu.sellsavvy.logic.commands.ordercommands.EditOrderCommand.EditOrderDescriptor;
import seedu.sellsavvy.logic.commands.ordercommands.FilterOrderCommand;
import seedu.sellsavvy.logic.commands.ordercommands.ListOrderCommand;
import seedu.sellsavvy.logic.commands.ordercommands.MarkOrderCommand;
import seedu.sellsavvy.logic.commands.ordercommands.UnmarkOrderCommand;
import seedu.sellsavvy.logic.commands.personcommands.AddPersonCommand;
import seedu.sellsavvy.logic.commands.personcommands.DeletePersonCommand;
import seedu.sellsavvy.logic.commands.personcommands.EditPersonCommand;
import seedu.sellsavvy.logic.commands.personcommands.EditPersonCommand.EditPersonDescriptor;
import seedu.sellsavvy.logic.commands.personcommands.FindPersonCommand;
import seedu.sellsavvy.logic.commands.personcommands.ListPersonCommand;
import seedu.sellsavvy.logic.parser.exceptions.ParseException;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.Status;
import seedu.sellsavvy.model.order.StatusEqualsKeywordPredicate;
import seedu.sellsavvy.model.person.NameContainsKeywordsPredicate;
import seedu.sellsavvy.model.person.Person;
import seedu.sellsavvy.testutil.EditOrderDescriptorBuilder;
import seedu.sellsavvy.testutil.EditPersonDescriptorBuilder;
import seedu.sellsavvy.testutil.OrderBuilder;
import seedu.sellsavvy.testutil.OrderUtil;
import seedu.sellsavvy.testutil.PersonBuilder;
import seedu.sellsavvy.testutil.PersonUtil;
import seedu.sellsavvy.testutil.TypicalIndexes;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_addPerson() throws Exception {
        Person person = new PersonBuilder().build();

        // using command word
        AddPersonCommand command = (AddPersonCommand) parser.parseCommand(PersonUtil.getAddPersonCommand(person));
        assertEquals(new AddPersonCommand(person), command);

        // using command alias
        command = (AddPersonCommand) parser.parseCommand(PersonUtil.getAddPersonCommandAlias(person));
        assertEquals(new AddPersonCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_deletePerson() throws Exception {
        String parameters = " " + INDEX_FIRST.getOneBased();

        // using command word
        DeletePersonCommand command = (DeletePersonCommand) parser.parseCommand(
                DeletePersonCommand.COMMAND_WORD + parameters);
        assertEquals(new DeletePersonCommand(INDEX_FIRST), command);

        // using command alias
        command = (DeletePersonCommand) parser.parseCommand(
                DeletePersonCommand.COMMAND_ALIAS + parameters);
        assertEquals(new DeletePersonCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_editPerson() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        String parameters = " " + INDEX_FIRST.getOneBased()
                + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor);

        // using command word
        EditPersonCommand command = (EditPersonCommand) parser.parseCommand(EditPersonCommand.COMMAND_WORD
                + parameters);
        assertEquals(new EditPersonCommand(INDEX_FIRST, descriptor), command);

        // using command alias
        command = (EditPersonCommand) parser.parseCommand(EditPersonCommand.COMMAND_ALIAS + parameters);
        assertEquals(new EditPersonCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findPerson() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        String parameters = " " + keywords.stream().collect(Collectors.joining(" "));

        // using command word
        FindPersonCommand command = (FindPersonCommand) parser.parseCommand(
                FindPersonCommand.COMMAND_WORD + parameters);
        assertEquals(new FindPersonCommand(new NameContainsKeywordsPredicate(keywords)), command);

        // using command alias
        command = (FindPersonCommand) parser.parseCommand(
                FindPersonCommand.COMMAND_ALIAS + parameters);
        assertEquals(new FindPersonCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_listPerson() throws Exception {
        // using command word
        assertTrue(parser.parseCommand(ListPersonCommand.COMMAND_WORD) instanceof ListPersonCommand);
        assertTrue(parser.parseCommand(ListPersonCommand.COMMAND_WORD + " 3") instanceof ListPersonCommand);

        // using command alias
        assertTrue(parser.parseCommand(ListPersonCommand.COMMAND_ALIAS) instanceof ListPersonCommand);
        assertTrue(parser.parseCommand(ListPersonCommand.COMMAND_ALIAS + " 3") instanceof ListPersonCommand);
    }

    @Test
    public void parseCommand_addOrder() throws Exception {
        Order order = new OrderBuilder().build();

        // using command word
        String commandString = OrderUtil.getAddOrderCommand(INDEX_FIRST, order);
        AddOrderCommand command = (AddOrderCommand) parser.parseCommand(commandString);
        assertEquals(new AddOrderCommand(INDEX_FIRST, order), command);

        // using command alias
        commandString = OrderUtil.getAddOrderCommandAlias(INDEX_FIRST, order);
        command = (AddOrderCommand) parser.parseCommand(commandString);
        assertEquals(new AddOrderCommand(INDEX_FIRST, order), command);
    }

    @Test
    public void parseCommand_listOrder() throws Exception {
        String parameters = " " + INDEX_FIRST.getOneBased();

        // using command word
        ListOrderCommand command = (ListOrderCommand) parser.parseCommand(
                ListOrderCommand.COMMAND_WORD + parameters);
        assertEquals(new ListOrderCommand(INDEX_FIRST), command);

        // using command alias
        command = (ListOrderCommand) parser.parseCommand(
                ListOrderCommand.COMMAND_ALIAS + parameters);
        assertEquals(new ListOrderCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_deleteOrder() throws Exception {
        String parameters = " " + TypicalIndexes.INDEX_FIRST.getOneBased();

        // using command word
        DeleteOrderCommand command = (DeleteOrderCommand) parser.parseCommand(
                DeleteOrderCommand.COMMAND_WORD + parameters);
        assertEquals(new DeleteOrderCommand(TypicalIndexes.INDEX_FIRST), command);

        // using command alias
        command = (DeleteOrderCommand) parser.parseCommand(
                DeleteOrderCommand.COMMAND_ALIAS + parameters);
        assertEquals(new DeleteOrderCommand(TypicalIndexes.INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_editOrder() throws Exception {
        Order order = new OrderBuilder().build();
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(order).build();
        String parameters = " " + TypicalIndexes.INDEX_FIRST.getOneBased() + " "
                + OrderUtil.getEditOrderDescriptorDetails(descriptor);

        // using command word
        EditOrderCommand command = (EditOrderCommand) parser.parseCommand(
                EditOrderCommand.COMMAND_WORD + parameters);
        assertEquals(new EditOrderCommand(TypicalIndexes.INDEX_FIRST, descriptor), command);

        // using command alias
        command = (EditOrderCommand) parser.parseCommand(
                EditOrderCommand.COMMAND_ALIAS + parameters);
        assertEquals(new EditOrderCommand(TypicalIndexes.INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_markOrder() throws Exception {
        String parameters = " " + TypicalIndexes.INDEX_FIRST.getOneBased();

        // using command word
        MarkOrderCommand command = (MarkOrderCommand) parser.parseCommand(
                MarkOrderCommand.COMMAND_WORD + parameters);
        assertEquals(new MarkOrderCommand(TypicalIndexes.INDEX_FIRST), command);

        // using command alias
        command = (MarkOrderCommand) parser.parseCommand(
                MarkOrderCommand.COMMAND_ALIAS + parameters);
        assertEquals(new MarkOrderCommand(TypicalIndexes.INDEX_FIRST), command);

    }

    @Test
    public void parseCommand_unmarkOrder() throws Exception {
        String parameters = " " + TypicalIndexes.INDEX_FIRST.getOneBased();

        // using command word
        UnmarkOrderCommand command = (UnmarkOrderCommand) parser.parseCommand(
                UnmarkOrderCommand.COMMAND_WORD + parameters);
        assertEquals(new UnmarkOrderCommand(TypicalIndexes.INDEX_FIRST), command);

        // using command alias
        command = (UnmarkOrderCommand) parser.parseCommand(
                UnmarkOrderCommand.COMMAND_ALIAS + parameters);
        assertEquals(new UnmarkOrderCommand(TypicalIndexes.INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_filterOrder() throws Exception {
        // using command word
        FilterOrderCommand command = (FilterOrderCommand) parser.parseCommand(
                FilterOrderCommand.COMMAND_WORD + " Completed");
        assertEquals(new FilterOrderCommand(new StatusEqualsKeywordPredicate(Status.COMPLETED)), command);

        // using command alias
        command = (FilterOrderCommand) parser.parseCommand(
                FilterOrderCommand.COMMAND_ALIAS + " Completed");
        assertEquals(new FilterOrderCommand(new StatusEqualsKeywordPredicate(Status.COMPLETED)), command);
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
}
