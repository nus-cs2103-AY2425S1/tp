package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.eventcommands.FindEventCommand;
import seedu.address.logic.commands.eventcommands.ScheduleCommand;
import seedu.address.logic.commands.eventcommands.SearchEventCommand;
import seedu.address.logic.commands.personcommands.AddPersonCommand;
import seedu.address.logic.commands.personcommands.DeletePersonCommand;
import seedu.address.logic.commands.personcommands.EditPersonCommand;
import seedu.address.logic.commands.personcommands.EditPersonCommand.EditPersonDescriptor;
import seedu.address.logic.commands.personcommands.ExitCommand;
import seedu.address.logic.commands.personcommands.FindPersonCommand;
import seedu.address.logic.commands.personcommands.ListCommand;
import seedu.address.logic.commands.personcommands.SearchPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.types.common.DateTime;
import seedu.address.model.types.common.EventInSchedulePredicate;
import seedu.address.model.types.common.EventNameContainsKeywordsPredicate;
import seedu.address.model.types.common.EventTagContainsKeywordsPredicate;
import seedu.address.model.types.common.NameContainsKeywordsPredicate;
import seedu.address.model.types.common.PersonTagContainsKeywordsPredicate;
import seedu.address.model.types.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddPersonCommand command = (AddPersonCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddPersonCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeletePersonCommand command = (DeletePersonCommand) parser.parseCommand(
                DeletePersonCommand.COMMAND_WORD + " p " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeletePersonCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditPersonCommand command = (EditPersonCommand) parser.parseCommand(EditPersonCommand.COMMAND_WORD + " p "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditPersonCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        // find person
        List<String> firstKeywords = Arrays.asList("foo", "bar", "baz");
        FindPersonCommand firstCommand = (FindPersonCommand) parser.parseCommand(
                FindPersonCommand.COMMAND_WORD + " p " + firstKeywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPersonCommand(new NameContainsKeywordsPredicate(firstKeywords)), firstCommand);

        // find event
        List<String> secondKeywords = Arrays.asList("foo", "bar", "baz");
        FindEventCommand secondCommand = (FindEventCommand) parser.parseCommand(
                FindEventCommand.COMMAND_WORD + " e " + secondKeywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindEventCommand(new EventNameContainsKeywordsPredicate(secondKeywords)), secondCommand);
    }

    @Test
    public void parseCommand_search() throws Exception {
        // search person
        List<String> firstKeywords = Arrays.asList("foo", "bar", "baz");
        SearchPersonCommand firstCommand = (SearchPersonCommand) parser.parseCommand(
                SearchPersonCommand.COMMAND_WORD + " p " + firstKeywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchPersonCommand(new PersonTagContainsKeywordsPredicate(firstKeywords)), firstCommand);

        // search event
        List<String> secondKeywords = Arrays.asList("foo", "bar", "baz");
        SearchEventCommand secondCommand = (SearchEventCommand) parser.parseCommand(
                SearchPersonCommand.COMMAND_WORD + " e " + secondKeywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchEventCommand(new EventTagContainsKeywordsPredicate(secondKeywords)), secondCommand);
    }

    @Test
    public void parseCommand_schedule() throws Exception {
        ScheduleCommand firstCommand = (ScheduleCommand) parser.parseCommand(
                ScheduleCommand.COMMAND_WORD + " 7");
        assertEquals(new ScheduleCommand(new EventInSchedulePredicate(7)), firstCommand);

        ScheduleCommand secondCommand = (ScheduleCommand) parser.parseCommand(
                ScheduleCommand.COMMAND_WORD + " 2024-10-15");
        assertEquals(new ScheduleCommand(new EventInSchedulePredicate(new DateTime("2024-10-15 00:00"))),
                secondCommand);
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
}
