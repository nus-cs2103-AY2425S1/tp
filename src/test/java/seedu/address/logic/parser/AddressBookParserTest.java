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
import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListBackupsCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.UpdatePersonDescriptorBuilder;

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
        Nric nric = new Nric("S1234567A");

        // Update the command to pass the NRIC instead of the index
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + "S1234567A");

        // Update the assertion to expect a DeleteCommand constructed with the NRIC
        assertEquals(new DeleteCommand(nric), command);
    }

    @Test
    public void parseCommand_update() throws Exception {
        Person person = new PersonBuilder().build();
        UpdateCommand.UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder(person).build();
        UpdateCommand command = (UpdateCommand) parser.parseCommand(UpdateCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new UpdateCommand(INDEX_FIRST_PERSON, descriptor), command);
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

    /*@Test
    public void parseCommand_backup() throws Exception {
        BackupCommand command = (BackupCommand) parser.parseCommand(BackupCommand.COMMAND_WORD + " ");
        assertEquals(new BackupCommand(), command);
    }*/

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
    public void parseCommandBackupNoArgs() throws Exception {
        BackupCommand command = (BackupCommand) parser.parseCommand(BackupCommand.COMMAND_WORD);
        assertEquals(new BackupCommand(null), command);
    }

    @Test
    public void parseCommandBackupWithFileName() throws Exception {
        String fileName = "myBackup";
        BackupCommand command = (BackupCommand) parser.parseCommand(BackupCommand.COMMAND_WORD + " " + fileName);
        assertEquals(new BackupCommand(fileName), command);
    }

    @Test
    public void parseCommandListBackups_noArgs_success() throws Exception {
        // Check if listbackups command without arguments is parsed correctly
        Command command = parser.parseCommand(ListBackupsCommand.COMMAND_WORD);
        assertTrue(command instanceof ListBackupsCommand);
    }

    @Test
    public void parseCommandListBackups_withArgs_throwsParseException() {
        // Ensure listbackups command fails if arguments are provided
        assertThrows(ParseException.class, () -> parser.parseCommand(ListBackupsCommand.COMMAND_WORD + " extraArg"));
    }

}
