package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FULLTAGNAME;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddShortCutCommand;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.ArchiveCommand.ArchivePersonDescriptor;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DelShortCutCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DownloadCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListShortCutCommand;
import seedu.address.logic.commands.UnarchiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.TagsContainsKeywordsPredicate;
import seedu.address.testutil.ArchivePersonDescriptorBuilder;
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
    public void parseCommand_archive() throws Exception {
        Person person = new PersonBuilder().build();
        ArchivePersonDescriptor descriptor = new ArchivePersonDescriptorBuilder().build();
        descriptor.setIsArchived(true);
        ArchiveCommand actualCommand = (ArchiveCommand) parser.parseCommand(ArchiveCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased());
        ArchiveCommand expectedCommand = new ArchiveCommand(INDEX_FIRST_PERSON, descriptor);
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parseCommand_unarchive() throws Exception {
        Person person = new PersonBuilder().build();
        ArchivePersonDescriptor descriptor = new ArchivePersonDescriptorBuilder().build();
        descriptor.setIsArchived(false);
        UnarchiveCommand actualCommand = (UnarchiveCommand) parser.parseCommand(UnarchiveCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased());
        UnarchiveCommand expectedCommand = new UnarchiveCommand(INDEX_FIRST_PERSON, descriptor);
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parseCommand_download() throws Exception {
        assertTrue(parser.parseCommand(DownloadCommand.COMMAND_WORD) instanceof DownloadCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand expectedCommand = new FindCommand(new NameContainsKeywordsPredicate(keywords));

        assertEquals(expectedCommand, parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + String.join(" ", keywords)));
    }


    @Test
    public void parseCommand_filter() throws Exception {
        List<String> keywords = Arrays.asList("v", "vg");
        FilterCommand command = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FilterCommand(new TagsContainsKeywordsPredicate(keywords)), command);
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
    public void parseCommand_addShortCut() throws Exception {
        assertThrows(ParseException.class, () -> parser.parseCommand(AddShortCutCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(AddShortCutCommand.COMMAND_WORD + " " + PREFIX_ALIAS + " v "
                + PREFIX_FULLTAGNAME + " Vegan") instanceof AddShortCutCommand);
    }

    @Test
    public void parseCommand_delShortCut() throws Exception {
        assertThrows(ParseException.class, () -> parser.parseCommand(DelShortCutCommand.COMMAND_WORD));
        assertTrue(parser.parseCommand(DelShortCutCommand.COMMAND_WORD + " " + PREFIX_ALIAS + " v "
                + PREFIX_FULLTAGNAME + " Vegan") instanceof DelShortCutCommand);
    }

    @Test
    public void parseCommand_listShortCut() throws Exception {
        assertTrue(parser.parseCommand(ListShortCutCommand.COMMAND_WORD) instanceof ListShortCutCommand);
        assertTrue(parser.parseCommand(ListShortCutCommand.COMMAND_WORD + " 3") instanceof ListShortCutCommand);
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
