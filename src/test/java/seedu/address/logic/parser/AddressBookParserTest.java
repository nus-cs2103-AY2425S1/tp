package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListAppointmentsCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ReminderCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;
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
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + "John");
        assertEquals(new DeleteCommand(new Name("John")), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + VALID_NAME_AMY + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(new Name(VALID_NAME_AMY), descriptor), command);
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
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords), null), command);
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
    public void parseCommand_schedule() throws Exception {
        String dateTime = "2024-10-04 1000";
        Set<Schedule> scheduleSet = new HashSet<>();
        scheduleSet.add(new Schedule(dateTime, ""));
        ScheduleCommand expectedCommand = new ScheduleCommand("Jane", scheduleSet);

        ScheduleCommand actualCommand = (ScheduleCommand) parser.parseCommand(
                ScheduleCommand.COMMAND_WORD + " Jane" + " d/" + dateTime);

        // Assert that the expected command equals the actual command
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parseCommand_listAppointments() throws Exception {
        // Test without parameters
        assertTrue(parser.parseCommand(ListAppointmentsCommand.COMMAND_WORD) instanceof ListAppointmentsCommand);

        // Test with date parameter
        ListAppointmentsCommand dateCommand = (ListAppointmentsCommand) parser.parseCommand(
                ListAppointmentsCommand.COMMAND_WORD + " 2024-10-15");
        assertEquals(new ListAppointmentsCommand(Optional.of(LocalDate.of(2024, 10, 15)),
                Optional.empty()), dateCommand);

        // Test with date and time parameters
        ListAppointmentsCommand dateTimeCommand = (ListAppointmentsCommand) parser.parseCommand(
                ListAppointmentsCommand.COMMAND_WORD + " 2024-10-15 14:30");
        assertEquals(new ListAppointmentsCommand(
                        Optional.of(LocalDate.of(2024, 10, 15)),
                        Optional.of(LocalTime.of(14, 30))),
                dateTimeCommand);
    }

    @Test
    public void parseCommand_reminder() throws Exception {
        String reminderTime = "1 day";
        ReminderCommand expectedCommand = new ReminderCommand("Jane", reminderTime);

        ReminderCommand actualCommand = (ReminderCommand) parser.parseCommand(
                ReminderCommand.COMMAND_WORD + " Jane" + " r/" + reminderTime);

        // Assert that the expected command equals the actual command
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () ->
                parser.parseCommand("unknownCommand"));
    }
}
