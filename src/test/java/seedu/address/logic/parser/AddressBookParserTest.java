package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INCOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MULTIPLE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalParams.PARAMS_ARRAY_FIRST;
import static seedu.address.testutil.TypicalParams.PARAMS_INPUT_FIRST;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GetCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListAppointmentCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.StatisticsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        Command command = parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_addAppointment() throws Exception {
        LocalDate date = LocalDate.of(2024, 11, 1);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 0);

        String input = AddAppointmentCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_DATE + date + " " + PREFIX_FROM + startTime + " " + PREFIX_TO + endTime;

        Command command = parser.parseCommand(input);
        assertEquals(new AddAppointmentCommand(INDEX_FIRST_PERSON, date, startTime, endTime), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        Command command = parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        Command command = parser.parseCommand(DeleteCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_MULTIPLE), command);
    }

    @Test
    public void parseCommand_deleteAppointment() throws Exception {
        Command command = parser.parseCommand(DeleteAppointmentCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteAppointmentCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertInstanceOf(ClearCommand.class, parser.parseCommand(ClearCommand.COMMAND_WORD));
        assertInstanceOf(ClearCommand.class, parser.parseCommand(ClearCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> nameKeywords = Arrays.asList("foo", "bar");
        List<String> addressKeywords = Arrays.asList("clementi", "tampines");
        List<String> priorities = Arrays.asList("high", "medium");
        List<String> incomes = List.of("1500");

        Command command = parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + PREFIX_NAME + nameKeywords.get(0) + " "
                        + PREFIX_NAME + nameKeywords.get(1) + " "
                        + PREFIX_ADDRESS + addressKeywords.get(0) + " " + PREFIX_ADDRESS + addressKeywords.get(1) + " "
                        + PREFIX_PRIORITY + priorities.get(0) + " " + PREFIX_PRIORITY + priorities.get(1) + " "
                        + PREFIX_INCOME + incomes.get(0));

        assertEquals(new FindCommand(nameKeywords, addressKeywords, priorities, incomes), command);
    }

    @Test
    public void parseCommand_get() throws Exception {
        Command command = parser.parseCommand(GetCommand.COMMAND_WORD + " " + PARAMS_INPUT_FIRST);
        assertEquals(new GetCommand(PARAMS_ARRAY_FIRST), command);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertInstanceOf(ListCommand.class, parser.parseCommand(ListCommand.COMMAND_WORD));
        assertInstanceOf(ListCommand.class, parser.parseCommand(ListCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_listAppointment() throws Exception {
        assertInstanceOf(ListAppointmentCommand.class, parser.parseCommand(ListAppointmentCommand.COMMAND_WORD));
        assertInstanceOf(ListAppointmentCommand.class,
                parser.parseCommand(ListAppointmentCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertInstanceOf(ExitCommand.class, parser.parseCommand(ExitCommand.COMMAND_WORD));
        assertInstanceOf(ExitCommand.class, parser.parseCommand(ExitCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertInstanceOf(HelpCommand.class, parser.parseCommand(HelpCommand.COMMAND_WORD));
        assertInstanceOf(HelpCommand.class, parser.parseCommand(HelpCommand.COMMAND_WORD + " add"));
    }

    @Test
    public void parseCommand_statistics() throws Exception {
        assertInstanceOf(StatisticsCommand.class, parser.parseCommand(StatisticsCommand.COMMAND_WORD));
        assertInstanceOf(StatisticsCommand.class, parser.parseCommand(StatisticsCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_sort() throws Exception {
        Command command = parser.parseCommand(SortCommand.COMMAND_WORD + " name");
        assertEquals(command, new SortCommand("name"));
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE);
        assertThrows(ParseException.class, expectedMessage, () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
