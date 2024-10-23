package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.SortCommand.ASCENDING;
import static seedu.address.logic.commands.SortCommand.DESCENDING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLDTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RenameTagCommand;
import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.ScheduleCommand.ScheduleDescriptor;
import seedu.address.logic.commands.SocialMediaCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.SocialMedia;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.ScheduleBuilder;
import seedu.address.testutil.ScheduleDescriptorBuilder;
import seedu.address.testutil.ScheduleUtil;

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
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_filter() throws Exception {
        FilterCommand command = (FilterCommand) parser.parseCommand(
                FilterCommand.COMMAND_WORD + " " + PREFIX_TAG + "friends");
        Set<Tag> expectedTags = new HashSet<>();
        expectedTags.add(new Tag("friends"));
        assertEquals(new FilterCommand(expectedTags), command);
    }

    @Test
    public void parseCommand_schedule() throws Exception {
        Schedule schedule = new ScheduleBuilder().build();
        ScheduleDescriptor descriptor = new ScheduleDescriptorBuilder(schedule).build();
        ScheduleCommand command = (ScheduleCommand) parser.parseCommand(ScheduleCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + ScheduleUtil.getScheduleDescriptorDetails(descriptor));
        assertEquals(new ScheduleCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        SortCommand ascCommandName = (SortCommand) parser.parseCommand(
                SortCommand.COMMAND_WORD + " " + PREFIX_NAME + ASCENDING);
        assertEquals(new SortCommand(ASCENDING, false), ascCommandName);
        SortCommand descCommandName = (SortCommand) parser.parseCommand(
                SortCommand.COMMAND_WORD + " " + PREFIX_NAME + DESCENDING);
        assertEquals(new SortCommand(DESCENDING, false), descCommandName);
        SortCommand ascCommandSchedule = (SortCommand) parser.parseCommand(
                SortCommand.COMMAND_WORD + " " + PREFIX_SCHEDULE + ASCENDING);
        assertEquals(new SortCommand(ASCENDING, true), ascCommandSchedule);
        SortCommand descCommandSchedule = (SortCommand) parser.parseCommand(
                SortCommand.COMMAND_WORD + " " + PREFIX_SCHEDULE + DESCENDING);
        assertEquals(new SortCommand(DESCENDING, true), descCommandSchedule);
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

    @Test
    public void parseCommand_renameTag() throws Exception {
        RenameTagCommand command = (RenameTagCommand) parser.parseCommand(
                RenameTagCommand.COMMAND_WORD + " " + PREFIX_OLDTAG + "friends" + " " + PREFIX_NEWTAG
                        + "enemies");
        assertEquals(new RenameTagCommand("friends", "enemies"), command);
    }

    @Test
    public void parseCommand_restore() throws Exception {
        assertTrue(parser.parseCommand(RestoreCommand.COMMAND_WORD) instanceof RestoreCommand);
        assertTrue(parser.parseCommand(RestoreCommand.COMMAND_WORD + " 3") instanceof RestoreCommand);
    }
    @Test
    public void parseCommand_backup() throws Exception {
        assertTrue(parser.parseCommand(BackupCommand.COMMAND_WORD) instanceof BackupCommand);
        assertTrue(parser.parseCommand(BackupCommand.COMMAND_WORD + " 3") instanceof BackupCommand);
    }

    @Test
    public void parseCommand_socialMedia() throws Exception {
        SocialMediaCommand command = (SocialMediaCommand) parser.parseCommand(
                SocialMediaCommand.COMMAND_WORD + " 1 " + PREFIX_IG + "username");
        assertEquals(new SocialMediaCommand("username", SocialMedia.Platform.INSTAGRAM, INDEX_FIRST_PERSON),
                command);
    }
}


