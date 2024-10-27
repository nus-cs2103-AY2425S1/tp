package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindAddressCommand;
import seedu.address.logic.commands.FindClientTypeCommand;
import seedu.address.logic.commands.FindNameCommand;
import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.ClientTypeContainsKeywordsPredicate;
import seedu.address.model.person.NameComparator;
import seedu.address.model.person.NameContainsKeywordsDeletePredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneBeginsWithKeywordPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.TypicalPersons;

public class ClientHubParserTest {

    private final ClientHubParser parser = new ClientHubParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseShortCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddShortCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + TypicalPersons.ALICE.getName().fullName);
        assertEquals(new DeleteCommand(new
                NameContainsKeywordsDeletePredicate(
                        Arrays.asList(TypicalPersons.ALICE.getName().fullName.split("\\s+")))), command);
    }

    @Test
    public void parseShortCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.SHORT_COMMAND_WORD + " " + TypicalPersons.ALICE.getName().fullName);
        assertEquals(new DeleteCommand(new
                NameContainsKeywordsDeletePredicate(
                Arrays.asList(TypicalPersons.ALICE.getName().fullName.split("\\s+")))), command);
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
        FindNameCommand command = (FindNameCommand) parser.parseCommand(
                FindNameCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindNameCommand(new NameContainsKeywordsPredicate(keywords)), command);
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
    public void parseshortCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.SHORT_COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.SHORT_COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_findPhone() throws Exception {
        String keyword = "9876";
        FindPhoneCommand command = (FindPhoneCommand) parser.parseCommand(
                FindPhoneCommand.COMMAND_WORD + " " + keyword);
        assertEquals(new FindPhoneCommand(new PhoneBeginsWithKeywordPredicate(keyword)), command);
    }

    @Test
    public void parseCommand_findAddress() throws Exception {
        String keywords = "Tampines";
        FindAddressCommand command = (FindAddressCommand) parser.parseCommand(
                FindAddressCommand.COMMAND_WORD + " " + keywords);
        assertEquals(new FindAddressCommand(new AddressContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findClientType() throws Exception {
        String keyword = "Investment";
        FindClientTypeCommand command = (FindClientTypeCommand) parser.parseCommand(
                FindClientTypeCommand.COMMAND_WORD + " " + keyword);
        assertEquals(new FindClientTypeCommand(new ClientTypeContainsKeywordsPredicate(List.of(keyword))), command);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        String criteria = "n/";
        SortCommand command = (SortCommand) parser.parseCommand(
                SortCommand.COMMAND_WORD + " " + criteria);
        assertEquals(new SortCommand(new NameComparator()), command);

        SortCommand shortCommand = (SortCommand) parser.parseCommand(
                SortCommand.SHORT_COMMAND_WORD + " " + criteria);
        assertEquals(new SortCommand(new NameComparator()), command);
    }

    @Test
    public void parseCommand_viewCommand() throws Exception {
        String keyword = "Alice";
        ViewCommand command = (ViewCommand) parser.parseCommand(
                ViewCommand.COMMAND_WORD + " " + keyword);
        assertEquals(new ViewCommand(new NameContainsKeywordsPredicate(List.of(keyword))), command);
    }

    @Test
    public void parseShortCommand_viewCommand() throws Exception {
        String keyword = "Alice";
        ViewCommand command = (ViewCommand) parser.parseCommand(
                ViewCommand.SHORT_COMMAND_WORD + " " + keyword);
        assertEquals(new ViewCommand(new NameContainsKeywordsPredicate(List.of(keyword))), command);
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
