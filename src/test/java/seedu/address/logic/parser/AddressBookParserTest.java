package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DEADLINE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TASK_DESCRIPTION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TASK_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DEADLINE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESCRIPTION_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalPersons.AMY;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateCommand.UpdatePersonDescriptor;
import seedu.address.logic.commands.UpdateTaskCommand;
import seedu.address.logic.commands.UpdateTaskCommand.UpdateTaskDescriptor;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.ViewTasksCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicate.NameContainsKeywordsPredicate;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.UpdatePersonDescriptorBuilder;
import seedu.address.testutil.UpdateTaskDescriptorBuilder;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser(new ModelManager());

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_addTask() throws Exception {
        String userInput = AddTaskCommand.COMMAND_WORD + NAME_DESC_AMY + TASK_DESCRIPTION_DESC_AMY
                + TASK_DEADLINE_DESC_AMY;
        AddTaskCommand command = (AddTaskCommand) parser.parseCommand(userInput);
        assertEquals(new AddTaskCommand(new Name(VALID_NAME_AMY), ParserUtil.parseTask(VALID_TASK_DESCRIPTION_AMY,
                VALID_TASK_DEADLINE_AMY)), command);
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
    public void parseCommand_deleteTask() throws Exception {
        DeleteTaskCommand command = (DeleteTaskCommand) parser.parseCommand(
                DeleteTaskCommand.COMMAND_WORD + " " + NAME_DESC_AMY + TASK_INDEX_DESC);
        assertEquals(new DeleteTaskCommand(new Name(VALID_NAME_AMY), INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_update() throws Exception {
        Person person = new PersonBuilder().build();
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder(person).build();
        UpdateCommand command = (UpdateCommand) parser.parseCommand(UpdateCommand.COMMAND_WORD + " "
                + person.getName() + " " + PersonUtil.getUpdatePersonDescriptorDetails(descriptor));
        assertEquals(new UpdateCommand(person.getName(), descriptor), command);
    }

    @Test
    public void parseCommand_updateTask() throws Exception {
        String userInput = UpdateTaskCommand.COMMAND_WORD + NAME_DESC_AMY + TASK_INDEX_DESC + TASK_DESCRIPTION_DESC_BOB
                + TASK_DEADLINE_DESC_AMY;
        UpdateTaskDescriptor descriptor = new UpdateTaskDescriptorBuilder(
                ParserUtil.parseTask(VALID_TASK_DESCRIPTION_PROJECT, VALID_TASK_DEADLINE_AMY)).build();
        UpdateTaskCommand command = (UpdateTaskCommand) parser.parseCommand(userInput);
        assertEquals(new UpdateTaskCommand(new Name(VALID_NAME_AMY), INDEX_FIRST_TASK, descriptor), command);
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
                FindCommand.COMMAND_WORD + " n/"
                        + keywords.stream().collect(Collectors.joining(" ")));
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
    public void parseCommand_note() throws Exception {
        final Note note = new Note("Some note.");
        NoteCommand command = (NoteCommand) parser.parseCommand(NoteCommand.COMMAND_WORD
                + NAME_DESC_AMY + " " + PREFIX_NOTE + note.value);
        assertEquals(new NoteCommand(AMY.getName(), note), command);
    }

    @Test
    public void parseCommand_viewTasks() throws Exception {
        assertTrue(parser.parseCommand(ViewTasksCommand.COMMAND_WORD) instanceof ViewTasksCommand);
        assertTrue(parser.parseCommand(ViewTasksCommand.COMMAND_WORD + " 3") instanceof ViewTasksCommand);
    }

    @Test
    public void parseCommand_view() throws Exception {
        ViewCommand command = (ViewCommand) parser.parseCommand(ViewCommand.COMMAND_WORD + " "
                + NAME_DESC_AMY);
        assertEquals(new ViewCommand(AMY.getName()), command);
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
