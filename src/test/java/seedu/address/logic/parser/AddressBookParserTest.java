package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.eventcommands.AddEventCommand;
import seedu.address.logic.commands.eventcommands.ClearEventCommand;
import seedu.address.logic.commands.eventcommands.DeleteEventCommand;
import seedu.address.logic.commands.eventcommands.EditEventCommand;
import seedu.address.logic.commands.eventcommands.FindEventCommand;
import seedu.address.logic.commands.eventcommands.ScheduleCommand;
import seedu.address.logic.commands.eventcommands.SearchEventCommand;
import seedu.address.logic.commands.personcommands.AddPersonCommand;
import seedu.address.logic.commands.personcommands.ClearPersonCommand;
import seedu.address.logic.commands.personcommands.DeletePersonCommand;
import seedu.address.logic.commands.personcommands.EditPersonCommand;
import seedu.address.logic.commands.personcommands.EditPersonCommand.EditPersonDescriptor;
import seedu.address.logic.commands.personcommands.FindPersonCommand;
import seedu.address.logic.commands.personcommands.LinkPersonCommand;
import seedu.address.logic.commands.personcommands.SearchPersonCommand;
import seedu.address.logic.commands.personcommands.UnlinkPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.types.common.DateTime;
import seedu.address.model.types.common.EventInSchedulePredicate;
import seedu.address.model.types.common.EventNameContainsKeywordsPredicate;
import seedu.address.model.types.common.EventTagContainsKeywordsPredicate;
import seedu.address.model.types.common.Name;
import seedu.address.model.types.common.NameContainsKeywordsPredicate;
import seedu.address.model.types.common.PersonTagContainsKeywordsPredicate;
import seedu.address.model.types.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void regex_modelNeither() {
        Pattern pattern = parser.getParserRegex();

        Matcher matcher = pattern.matcher("cmd  zz  arg");
        assert matcher.matches();

        String commandWord = matcher.group("commandWord");
        String modelTypeShortHand = matcher.group("modelType");
        ModelType modelType = ModelType.fromShorthand(modelTypeShortHand);
        String arguments = (modelType == ModelType.NEITHER)
                ? matcher.group("combined")
                : matcher.group("arguments");

        assertEquals("cmd", commandWord);
        assertEquals(ModelType.NEITHER, modelType);
        assertEquals("  zz  arg", arguments);
    }

    @Test
    public void regex_modelNotNeither() {
        Pattern pattern = parser.getParserRegex();

        Matcher matcher = pattern.matcher("cmd  p  arg");
        assert matcher.matches();

        String commandWord = matcher.group("commandWord");
        String modelTypeShortHand = matcher.group("modelType");
        ModelType modelType = ModelType.fromShorthand(modelTypeShortHand);
        String arguments = (modelType == ModelType.NEITHER)
                ? matcher.group("combined")
                : matcher.group("arguments");

        assertEquals("cmd", commandWord);
        assertEquals(ModelType.PERSON, modelType);
        assertEquals("  arg", arguments);
    }

    @Test
    public void isPrompted_check() throws ParseException {
        final AddressBookParser tempParser = new AddressBookParser();
        ClearCommand.setPrompted(true);
        ClearCommand firstCommand = (ClearCommand) tempParser.parseCommand("yes");
        assertEquals(new ClearCommandParser().parseClear(), firstCommand);

        ClearCommand.setPrompted(true);
        ClearCommand secondCommand = (ClearCommand) tempParser.parseCommand("y");
        assertEquals(new ClearCommandParser().parseClear(), secondCommand);

        ClearCommand.setPrompted(true);
        ClearCommand thirdCommand = (ClearCommand) tempParser.parseCommand("zzz");
        assertEquals(new ClearCommandParser().parseAbort(), thirdCommand);

        // Reset
        new ClearCommandParser();
        ClearCommand.setConfirmed(false);
        ClearCommand.setPrompted(false);
    }

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddPersonCommand command = (AddPersonCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddPersonCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeletePersonCommand command = (DeletePersonCommand) parser.parseCommand(
                DeletePersonCommand.COMMAND_WORD + " p " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeletePersonCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditPersonCommand command = (EditPersonCommand) parser.parseCommand(EditPersonCommand.COMMAND_WORD + " p "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditPersonCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        // find person
        List<String> firstKeywords = Arrays.asList("foo", "bar", "baz");
        FindPersonCommand firstCommand = (FindPersonCommand) parser.parseCommand(
                FindPersonCommand.COMMAND_WORD + " p " + firstKeywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPersonCommand(new NameContainsKeywordsPredicate(firstKeywords)), firstCommand);

        // find event
        List<String> secondKeywords = Arrays.asList("foo", "bar", "baz");
        FindEventCommand secondCommand = (FindEventCommand) parser.parseCommand(
                FindEventCommand.COMMAND_WORD + " e " + secondKeywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindEventCommand(new EventNameContainsKeywordsPredicate(secondKeywords)), secondCommand);
    }

    @Test
    public void parseCommand_search() throws Exception {
        // search person
        List<String> firstKeywords = Arrays.asList("foo", "bar", "baz");
        SearchPersonCommand firstCommand = (SearchPersonCommand) parser.parseCommand(
                SearchPersonCommand.COMMAND_WORD + " p " + firstKeywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchPersonCommand(new PersonTagContainsKeywordsPredicate(firstKeywords)), firstCommand);

        // search event
        List<String> secondKeywords = Arrays.asList("foo", "bar", "baz");
        SearchEventCommand secondCommand = (SearchEventCommand) parser.parseCommand(
                SearchPersonCommand.COMMAND_WORD + " e " + secondKeywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchEventCommand(new EventTagContainsKeywordsPredicate(secondKeywords)), secondCommand);
    }

    @Test
    public void parseCommand_schedule() throws Exception {
        ScheduleCommand firstCommand = (ScheduleCommand) parser.parseCommand(
                ScheduleCommand.COMMAND_WORD + " 7");
        assertEquals(new ScheduleCommand(new EventInSchedulePredicate(7)), firstCommand);

        ScheduleCommand secondCommand = (ScheduleCommand) parser.parseCommand(
                ScheduleCommand.COMMAND_WORD + " 2024-10-15");
        assertEquals(new ScheduleCommand(new EventInSchedulePredicate(new DateTime("2024-10-15 00:00"))),
                secondCommand);
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
    public void parseCommand_link() throws Exception {
        LinkPersonCommand command = (LinkPersonCommand) parser.parseCommand(
                LinkPersonCommand.COMMAND_WORD + " 1 " + " ev/Event Name");
        assertEquals(new LinkPersonCommand(INDEX_FIRST_EVENT, new Name("Event Name")), command);
    }

    @Test
    public void parseCommand_unlink() throws Exception {
        UnlinkPersonCommand command = (UnlinkPersonCommand) parser.parseCommand(
                UnlinkPersonCommand.COMMAND_WORD + " 1 " + " ev/Event Name");
        assertEquals(new UnlinkPersonCommand(INDEX_FIRST_EVENT, new Name("Event Name")), command);
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
    public void getHint_addCommandHint() {
        assertEquals(AddPersonCommand.MESSAGE_USAGE, parser.getHint("add p"));
        assertEquals(AddEventCommand.MESSAGE_USAGE, parser.getHint("add e"));
        assertEquals(AddCommand.MESSAGE_USAGE, parser.getHint("a"));
    }

    @Test
    public void getHint_deleteCommandHint() {
        assertEquals(DeletePersonCommand.MESSAGE_USAGE, parser.getHint("delete p"));
        assertEquals(DeleteEventCommand.MESSAGE_USAGE, parser.getHint("delete e"));
        assertEquals(DeleteCommand.MESSAGE_USAGE, parser.getHint("d"));
    }

    @Test
    public void getHint_editCommandHint() {
        assertEquals(EditPersonCommand.MESSAGE_USAGE, parser.getHint("edit p"));
        assertEquals(EditEventCommand.MESSAGE_USAGE, parser.getHint("edit e"));
        assertEquals(EditCommand.MESSAGE_USAGE, parser.getHint("edit "));
        assertEquals(EditCommand.MESSAGE_USAGE + "\n" + ExitCommand.MESSAGE_USAGE, parser.getHint("e"));
    }

    @Test
    public void getHint_exitCommandHint() {
        assertEquals(ExitCommand.MESSAGE_USAGE, parser.getHint("ex"));
    }

    @Test
    public void getHint_findCommandHint() {
        assertEquals(FindPersonCommand.MESSAGE_USAGE, parser.getHint("find p"));
        assertEquals(FindEventCommand.MESSAGE_USAGE, parser.getHint("find e"));
        assertEquals(FindCommand.MESSAGE_USAGE, parser.getHint("f"));
    }

    @Test
    public void getHint_searchCommandHint() {
        assertEquals(SearchPersonCommand.MESSAGE_USAGE, parser.getHint("search p"));
        assertEquals(SearchEventCommand.MESSAGE_USAGE, parser.getHint("search e"));
        assertEquals(SearchCommand.MESSAGE_USAGE + "\n" + ScheduleCommand.MESSAGE_HINT, parser.getHint("s"));
        assertEquals(SearchCommand.MESSAGE_USAGE, parser.getHint("se"));
    }

    @Test
    public void getHint_scheduleCommandHint() {
        assertEquals(ScheduleCommand.MESSAGE_USAGE, parser.getHint("sc"));
    }

    @Test
    public void getHint_listAndLinkCommandHint() {
        assertEquals(ListCommand.MESSAGE_USAGE, parser.getHint("lis"));
        assertEquals(LinkPersonCommand.MESSAGE_USAGE, parser.getHint("lin"));
        assertEquals(ListCommand.MESSAGE_USAGE + "\n" + LinkPersonCommand.MESSAGE_HINT,
                parser.getHint("l"));
    }

    @Test
    public void getHint_unlinkCommandHint() {
        assertEquals(UnlinkPersonCommand.MESSAGE_USAGE, parser.getHint("u"));
    }

    @Test
    public void getHint_clearCommandHint() {
        assertEquals(ClearPersonCommand.MESSAGE_USAGE, parser.getHint("clear p"));
        assertEquals(ClearEventCommand.MESSAGE_USAGE, parser.getHint("clear e"));
        assertEquals(ClearCommand.MESSAGE_USAGE, parser.getHint("c"));
    }

    @Test
    public void getHint_helpCommandHint() {
        assertEquals(HelpCommand.MESSAGE_USAGE, parser.getHint("h"));
    }

    @Test
    public void getHint_unrecognizedCommandHint() {
        assertEquals(HelpCommand.MESSAGE_USAGE, parser.getHint("123"));
    }
}
