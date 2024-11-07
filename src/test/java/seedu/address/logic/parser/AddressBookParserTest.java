package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteScheduleCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditScheduleCommand;
import seedu.address.logic.commands.EditScheduleCommand.EditScheduleDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MeetingContactsCommand;
import seedu.address.logic.commands.SeeAllScheduleCommand;
import seedu.address.logic.commands.SeeScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FieldContainsKeywordsPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.schedule.SameWeekAsDatePredicate;
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
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
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
                FindCommand.COMMAND_WORD + " " + keywords.stream()
                        .map(s -> "n/" + s)
                        .collect(Collectors.joining(" ")));
        ArgumentMultimap argumentMultimap = new ArgumentMultimap();
        argumentMultimap.put(new Prefix(""), "");
        argumentMultimap.put(PREFIX_NAME, "foo");
        argumentMultimap.put(PREFIX_NAME, "bar");
        argumentMultimap.put(PREFIX_NAME, "baz");
        assertEquals(new FindCommand(new FieldContainsKeywordsPredicate(argumentMultimap)), command);
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
    public void parseCommand_meetingContacts() throws Exception {
        String userInput = MeetingContactsCommand.COMMAND_WORD + " 1";
        assertEquals(new MeetingContactsCommand(Index.fromOneBased(1)),
                (MeetingContactsCommand) parser.parseCommand(userInput));
    }

    @Test
    public void parseCommand_seeSchedule() throws Exception {
        String userInput = SeeScheduleCommand.COMMAND_WORD + " d/05-11-2024";
        assertEquals(new SeeScheduleCommand(new SameWeekAsDatePredicate(LocalDate
                .parse("2024-11-05"))), (SeeScheduleCommand) parser.parseCommand(userInput));
    }

    @Test void parseCommand_seeAllSchedule() throws Exception {
        assertEquals(new SeeAllScheduleCommand(),
            (SeeAllScheduleCommand) parser.parseCommand(SeeAllScheduleCommand.COMMAND_WORD));
    }

    @Test
    public void parseCommand_favourite() throws Exception {
        String userInput = FavouriteCommand.COMMAND_WORD + " c/1 2";
        assertEquals(new FavouriteCommand(List.of(Index.fromOneBased(1), Index.fromOneBased(2))),
                (FavouriteCommand) parser.parseCommand(userInput));
    }

    @Test
    public void parseCommand_deleteSchedule() throws Exception {
        String userInput = DeleteScheduleCommand.COMMAND_WORD + " 1";
        assertEquals(new DeleteScheduleCommand(Index.fromOneBased(1)),
                (DeleteScheduleCommand) parser.parseCommand(userInput));
    }

    @Test
    public void parseCommand_editSchedule() throws Exception {
        EditScheduleDescriptor descriptor = new EditScheduleDescriptor();
        descriptor.setName(new Name("Dinner"));
        descriptor.setDate(LocalDate.parse("2024-10-10"));
        descriptor.setTime(LocalTime.parse("18:00"));
        String userInput = EditScheduleCommand.COMMAND_WORD + " 1 n/Dinner d/10-10-2024 t/1800";
        assertEquals(new EditScheduleCommand(Index.fromOneBased(1), descriptor),
                (EditScheduleCommand) parser.parseCommand(userInput));
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
