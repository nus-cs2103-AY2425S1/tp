package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteNCommand;
import seedu.address.logic.commands.DeleteYCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterByJobCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListWeddingCommand;
import seedu.address.logic.commands.TagAddCommand;
import seedu.address.logic.commands.TagDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.JobContainsKeywordsPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;
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
        Person person = new PersonBuilder().build();
        String name = person.getName().fullName;
        DeleteCommand command = (DeleteCommand) parser.parseCommand(DeleteCommand.COMMAND_WORD + " n/" + name);
        assertEquals(new DeleteCommand(name), command);
    }

    @Test
    public void parseCommand_deleteY() throws Exception {
        Person person = new PersonBuilder().withName("Alice Pauline").build();
        DeleteYCommand command = (DeleteYCommand) parser.parseCommand(
                DeleteYCommand.COMMAND_WORD + " " + person.getName().fullName);
        assertTrue(command instanceof DeleteYCommand);
    }

    @Test
    public void parseCommand_deleteN() throws Exception {
        Person person = new PersonBuilder().withName("Alice Pauline").build();
        DeleteNCommand command = (DeleteNCommand) parser.parseCommand(
                DeleteNCommand.COMMAND_WORD + " " + person.getName().fullName);
        assertTrue(command instanceof DeleteNCommand);
    }

    @Test
    public void parseCommand_invalidCommand_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parseCommand("invalidCommand"));
    }

    @Test
    public void parseCommand_edit() throws Exception {
        // Create a person with a valid name and tag
        Person person = new PersonBuilder().withName(VALID_NAME_AMY).withPhone("85355255")
                .withEmail("amy@gmail.com").withAddress("123, Jurong West Ave 6, #08-111")
                .withJob("Caterer").withTags(VALID_TAG_AMY).build();

        // Build the descriptor for editing the person
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();

        // Create the user input string for the edit command
        String userInput = EditCommand.COMMAND_WORD + " n/" + person.getName().fullName
                + " p/85355255 e/amy@gmail.com a/123, Jurong West Ave 6, #08-111 j/Caterer "
                + PREFIX_TAG + VALID_TAG_AMY;

        // Parse the command using the parser
        EditCommand command = (EditCommand) parser.parseCommand(userInput);

        // Check if the command is an instance of EditCommand
        assertTrue(command instanceof EditCommand);
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

    @Test
    public void parseCommand_listWedding() throws Exception {
        assertTrue(parser.parseCommand(ListWeddingCommand.COMMAND_WORD) instanceof ListWeddingCommand);
        assertTrue(parser.parseCommand(ListWeddingCommand.COMMAND_WORD + " 3") instanceof ListWeddingCommand);
    }

    @Test
    public void parseCommand_tagAdd() throws Exception {
        final String tag = "Jane and Tom 230412";
        TagAddCommand command = (TagAddCommand) parser.parseCommand(TagAddCommand.COMMAND_WORD + " n/"
                + VALID_NAME_AMY + " " + PREFIX_TAG + tag);
        Tag stubTag = new Tag(VALID_TAG_AMY);
        Name stubName = new Name(VALID_NAME_AMY);
        Set<Tag> stubTagList = new HashSet<>();
        stubTagList.add(stubTag);
        assertEquals(new TagAddCommand(stubName, stubTagList), command);
    }

    @Test
    public void parseCommand_tagDelete() throws Exception {
        final String tag = "Jane and Tom 230412";
        TagDeleteCommand command = (TagDeleteCommand) parser.parseCommand(TagDeleteCommand.COMMAND_WORD + " n/"
                + VALID_NAME_AMY + " " + PREFIX_TAG + tag);
        Tag stubTag = new Tag(VALID_TAG_AMY);
        Name stubName = new Name(VALID_NAME_AMY);
        Set<Tag> stubTagList = new HashSet<>();
        stubTagList.add(stubTag);
        assertEquals(new TagDeleteCommand(stubName, stubTagList), command);
    }

    @Test
    public void parseCommand_filterByJob() throws Exception {
        List<String> keywords = Arrays.asList("Engineer", "Doctor");
        FilterByJobCommand command = (FilterByJobCommand) parser.parseCommand(
                FilterByJobCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FilterByJobCommand(new JobContainsKeywordsPredicate(keywords)), command);
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
