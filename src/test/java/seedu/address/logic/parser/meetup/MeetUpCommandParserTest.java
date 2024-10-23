package seedu.address.logic.parser.meetup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETUP;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.meetup.AddCommand;
import seedu.address.logic.commands.meetup.DeleteCommand;
import seedu.address.logic.commands.meetup.EditCommand;
import seedu.address.logic.commands.meetup.EditCommand.EditMeetUpDescriptor;
import seedu.address.logic.commands.meetup.FindCommand;
import seedu.address.logic.commands.meetup.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.meetup.MeetUpContainsKeywordsPredicate;
import seedu.address.testutil.meetup.EditMeetUpDescriptorBuilder;
import seedu.address.testutil.meetup.MeetUpBuilder;
import seedu.address.testutil.meetup.MeetUpUtil;

public class MeetUpCommandParserTest {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private final MeetUpCommandParser parser = new MeetUpCommandParser();

    @Test
    public void parseCommand_add() throws Exception {
        MeetUp meetUp = new MeetUpBuilder().build();
        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(MeetUpUtil.getAddMeetUpCommand(meetUp));
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments");
        AddCommand command = (AddCommand) parser.parseCommand(commandWord, arguments);
        assertEquals(new AddCommand(meetUp), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_MEETUP.getOneBased());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments");
        DeleteCommand command = (DeleteCommand) parser.parseCommand(commandWord, arguments);
        assertEquals(new DeleteCommand(INDEX_FIRST_MEETUP), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        MeetUp meetUp = new MeetUpBuilder().build();
        EditMeetUpDescriptor descriptor = new EditMeetUpDescriptorBuilder(meetUp).build();
        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_MEETUP.getOneBased() + " " + MeetUpUtil.getEditMeetUpDescriptorDetails(descriptor));
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments");
        EditCommand command = (EditCommand) parser.parseCommand(commandWord, arguments);
        assertEquals(new EditCommand(INDEX_FIRST_MEETUP, descriptor), command);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(FindCommand.COMMAND_WORD + " " + PREFIX_NAME
                + keywords.stream().collect(Collectors.joining(" ")));
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments");
        FindCommand command = (FindCommand) parser.parseCommand(commandWord, arguments);
        assertEquals(new FindCommand(new MeetUpContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_view() throws Exception {
        assertTrue(parser.parseCommand(ViewCommand.COMMAND_WORD, "") instanceof ViewCommand);
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
                -> parser.parseCommand("unknownCommand", "unknownArguments"));
    }
}
