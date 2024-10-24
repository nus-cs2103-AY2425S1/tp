package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONCERT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONCERT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddConcertCommand;
import seedu.address.logic.commands.AddConcertContactCommand;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteConcertCommand;
import seedu.address.logic.commands.DeleteConcertContactCommand;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindConcertCommand;
import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListConcertCommand;
import seedu.address.logic.commands.ListConcertContactCommand;
import seedu.address.logic.commands.ListPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commons.NameContainsKeywordsPredicate;
import seedu.address.model.concert.Concert;
import seedu.address.model.person.Person;
import seedu.address.testutil.ConcertBuilder;
import seedu.address.testutil.ConcertUtil;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddPersonCommand command = (AddPersonCommand) parser.parseCommand(PersonUtil.getAddCommand(
                person));
        assertEquals(new AddPersonCommand(person), command);
    }

    @Test
    public void parseCommand_addConcert() throws Exception {
        Concert concert = new ConcertBuilder().build();
        AddConcertCommand command = (AddConcertCommand) parser.parseCommand(ConcertUtil
                .getAddCommand(concert));
        assertEquals(new AddConcertCommand(concert), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_deletePerson() throws Exception {
        DeletePersonCommand command = (DeletePersonCommand) parser.parseCommand(DeletePersonCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeletePersonCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deleteConcert() throws Exception {
        DeleteConcertCommand command = (DeleteConcertCommand) parser.parseCommand(
                DeleteConcertCommand.COMMAND_WORD + " " + INDEX_FIRST_CONCERT.getOneBased());
        assertEquals(new DeleteConcertCommand(INDEX_FIRST_CONCERT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil
                        .getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_link() throws Exception {
        AddConcertContactCommand command = (AddConcertContactCommand) parser
                .parseCommand(AddConcertContactCommand.COMMAND_WORD + " " + " " + PREFIX_PERSON
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_CONCERT + INDEX_FIRST_CONCERT.getOneBased());
        assertEquals(new AddConcertContactCommand(INDEX_FIRST_PERSON, INDEX_FIRST_CONCERT), command);
    }

    @Test
    public void parseCommand_deleteConcertContact() throws Exception {
        DeleteConcertContactCommand command = (DeleteConcertContactCommand) parser.parseCommand(
                DeleteConcertContactCommand.COMMAND_WORD + " " + " " + PREFIX_PERSON
                        + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_CONCERT + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteConcertContactCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        String userInput = FindPersonCommand.COMMAND_WORD + " " + PREFIX_NAME + keywords.stream()
                .collect(Collectors.joining(" "));
        FindPersonCommand command = (FindPersonCommand) parser.parseCommand(userInput);
        assertEquals(new FindPersonCommand(new NameContainsKeywordsPredicate<>(keywords)), command);
    }

    @Test
    public void parseCommand_findConcert() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        String userInput = FindConcertCommand.COMMAND_WORD + " "
                + PREFIX_NAME + keywords.stream().collect(Collectors.joining(" "));
        FindConcertCommand command = (FindConcertCommand) parser.parseCommand(
                userInput);
        assertEquals(new FindConcertCommand(new NameContainsKeywordsPredicate<>(keywords)), command);
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
    public void parseCommand_listConcert() throws Exception {
        assertTrue(parser.parseCommand(ListConcertCommand.COMMAND_WORD) instanceof ListConcertCommand);
        assertTrue(parser.parseCommand(ListConcertCommand.COMMAND_WORD + " 3") instanceof ListConcertCommand);
    }

    @Test
    public void parseCommand_listPerson() throws Exception {
        assertTrue(parser.parseCommand(ListPersonCommand.COMMAND_WORD) instanceof ListPersonCommand);
        assertTrue(parser.parseCommand(ListPersonCommand.COMMAND_WORD + " 3") instanceof ListPersonCommand);
    }

    @Test
    public void parseCommand_listConcertContact() throws Exception {
        assertTrue(parser.parseCommand(ListConcertContactCommand.COMMAND_WORD) instanceof ListConcertContactCommand);
        assertTrue(parser.parseCommand(ListConcertContactCommand.COMMAND_WORD + " 3")
                instanceof ListConcertContactCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand(
                "unknownCommand"));
    }

}
