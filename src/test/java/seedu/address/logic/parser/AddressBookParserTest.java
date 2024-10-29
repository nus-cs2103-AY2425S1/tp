package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGroups.GROUP_A;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPaths.VALID_NO_DUPS_IMPORT_FILE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindGroupCommand;
import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.commands.GroupsCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.UntagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.GroupUtil;
import seedu.address.testutil.GroupsUtil;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.TypicalPaths;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_group() throws Exception {
        assertTrue(parser.parseCommand(GroupUtil.groupCommand()) instanceof GroupCommand);
    }

    @Test
    public void parseCommand_groups() throws Exception {
        assertTrue(parser.parseCommand(GroupsUtil.groupsCommand()) instanceof GroupsCommand);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_deleteGroup() throws Exception {
        DeleteGroupCommand command = (DeleteGroupCommand) parser.parseCommand(
                DeleteGroupCommand.COMMAND_WORD + " " + GROUP_A.getGroupName().toString());
        assertEquals(new DeleteGroupCommand(GROUP_A.getGroupName()), command);
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
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findGroup() throws Exception {
        GroupContainsKeywordsPredicate groupPredicate =
                new GroupContainsKeywordsPredicate(Collections.singletonList(GROUP_A.getGroupName().toString()));
        FindGroupCommand command = (FindGroupCommand) parser.parseCommand(
                FindGroupCommand.COMMAND_WORD + " " + GROUP_A.getGroupName().toString());
        assertEquals(new FindGroupCommand(groupPredicate), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }
    @Test
    public void parseCommand_tag() throws Exception {
        Tag tag = new Tag("test");
        Set<Tag> tags = Set.of(tag);
        TagCommand expectedCommand = new TagCommand(INDEX_FIRST_PERSON, tags);
        assertEquals(expectedCommand,
                parser.parseCommand(TagCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + "t/test"));
    }

    @Test
    public void parseCommand_untag() throws Exception {
        Tag tag = new Tag("test");
        Set<Tag> tags = Set.of(tag);
        UntagCommand expectedCommand = new UntagCommand(INDEX_FIRST_PERSON, tags);
        assertEquals(expectedCommand,
                parser.parseCommand(UntagCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + "t/test"));
    }

    @Test
    public void parseCommand_import() throws Exception {
        ImportCommand expectedCommand = new ImportCommand(VALID_NO_DUPS_IMPORT_FILE);
        assertEquals(expectedCommand,
                parser.parseCommand(ImportCommand.COMMAND_WORD + " " + TypicalPaths.getTypicalPath().toString()
                        + "\\src\\test\\data\\ImportCommandTest\\valid_noDups_importFile.csv"));
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
