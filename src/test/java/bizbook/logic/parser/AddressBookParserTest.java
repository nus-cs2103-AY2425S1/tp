package bizbook.logic.parser;

import static bizbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static bizbook.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static bizbook.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static bizbook.logic.parser.CliSyntax.PREFIX_TAG;
import static bizbook.testutil.Assert.assertThrows;
import static bizbook.testutil.TypicalFileTypes.FILE_TYPE_CSV;
import static bizbook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import bizbook.logic.commands.AddCommand;
import bizbook.logic.commands.ClearCommand;
import bizbook.logic.commands.DeleteCommand;
import bizbook.logic.commands.DeleteTagCommand;
import bizbook.logic.commands.EditCommand;
import bizbook.logic.commands.EditCommand.EditPersonDescriptor;
import bizbook.logic.commands.ExitCommand;
import bizbook.logic.commands.ExportCommand;
import bizbook.logic.commands.FindCommand;
import bizbook.logic.commands.HelpCommand;
import bizbook.logic.commands.ListCommand;
import bizbook.logic.commands.PinCommand;
import bizbook.logic.commands.RedoCommand;
import bizbook.logic.commands.UndoCommand;
import bizbook.logic.commands.UnpinCommand;
import bizbook.logic.commands.ViewCommand;
import bizbook.logic.parser.exceptions.ParseException;
import bizbook.model.person.NameContainsKeywordsPredicate;
import bizbook.model.person.Person;
import bizbook.model.tag.Tag;
import bizbook.testutil.EditPersonDescriptorBuilder;
import bizbook.testutil.PersonBuilder;
import bizbook.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        AddCommand commandUpperCase = (AddCommand) parser.parseCommand(
                AddCommand.COMMAND_WORD.toUpperCase() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new AddCommand(person), command);
        assertEquals(new AddCommand(person), commandUpperCase);
    }


    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD.toUpperCase()) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD.toUpperCase() + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        DeleteCommand commandUpperCase = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD.toUpperCase() + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), commandUpperCase);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        EditCommand commandUpperCase = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD.toUpperCase() + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), commandUpperCase);
    }

    @Test
    public void parseCommand_deleteTag() throws Exception {
        DeleteTagCommand command = (DeleteTagCommand) parser.parseCommand(
                DeleteTagCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + " " + PREFIX_TAG + VALID_TAG_FRIEND);
        DeleteTagCommand commandUpperCase = (DeleteTagCommand) parser.parseCommand(
                DeleteTagCommand.COMMAND_WORD.toUpperCase() + " " + INDEX_FIRST_PERSON.getOneBased()
                        + " " + PREFIX_TAG + VALID_TAG_FRIEND);
        assertEquals(new DeleteTagCommand(INDEX_FIRST_PERSON, new Tag(VALID_TAG_FRIEND)), command);
        assertEquals(new DeleteTagCommand(INDEX_FIRST_PERSON, new Tag(VALID_TAG_FRIEND)), commandUpperCase);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD.toUpperCase()) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD.toUpperCase() + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        FindCommand commandUpperCase = (FindCommand) parser.parseCommand(FindCommand.COMMAND_WORD.toUpperCase()
                + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), commandUpperCase);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD.toUpperCase()) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD.toUpperCase() + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD.toUpperCase()) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD.toUpperCase() + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_view() throws Exception {
        assertTrue(parser.parseCommand(ViewCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased()) instanceof ViewCommand);
        assertTrue(parser.parseCommand(ViewCommand.COMMAND_WORD.toUpperCase()
                + " " + INDEX_FIRST_PERSON.getOneBased()) instanceof ViewCommand);
    }

    @Test
    public void parseCommand_export() throws Exception {
        ExportCommand command = (ExportCommand) parser.parseCommand(
                ExportCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_FILE + FILE_TYPE_CSV);
        ExportCommand commandUpperCase = (ExportCommand) parser.parseCommand(
                ExportCommand.COMMAND_WORD.toUpperCase() + " " + CliSyntax.PREFIX_FILE + FILE_TYPE_CSV);
        assertEquals(new ExportCommand(FILE_TYPE_CSV), command);
        assertEquals(new ExportCommand(FILE_TYPE_CSV), commandUpperCase);
    }

    @Test
    public void parseCommand_pin() throws Exception {
        PinCommand command = (PinCommand) parser.parseCommand(
                PinCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        PinCommand commandUpperCase = (PinCommand) parser.parseCommand(
                PinCommand.COMMAND_WORD.toUpperCase() + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new PinCommand(INDEX_FIRST_PERSON), command);
        assertEquals(new PinCommand(INDEX_FIRST_PERSON), commandUpperCase);
    }

    @Test
    public void parseCommand_unpin() throws Exception {
        UnpinCommand command = (UnpinCommand) parser.parseCommand(
                UnpinCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        UnpinCommand commandUpperCase = (UnpinCommand) parser.parseCommand(
                UnpinCommand.COMMAND_WORD.toUpperCase() + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new UnpinCommand(INDEX_FIRST_PERSON), command);
        assertEquals(new UnpinCommand(INDEX_FIRST_PERSON), commandUpperCase);
    }

    @Test
    public void parseCommand_undo() throws Exception {
        UndoCommand command = (UndoCommand) parser.parseCommand(
            UndoCommand.COMMAND_WORD);
        UndoCommand commandUpperCase = (UndoCommand) parser.parseCommand(
            UndoCommand.COMMAND_WORD.toUpperCase());
        assertEquals(new UndoCommand(), command);
        assertEquals(new UndoCommand(), commandUpperCase);
    }

    @Test
    public void parseCommand_redo() throws Exception {
        RedoCommand command = (RedoCommand) parser.parseCommand(
            RedoCommand.COMMAND_WORD);
        RedoCommand commandUpperCase = (RedoCommand) parser.parseCommand(
            RedoCommand.COMMAND_WORD.toUpperCase());
        assertEquals(new RedoCommand(), command);
        assertEquals(new RedoCommand(), commandUpperCase);
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
