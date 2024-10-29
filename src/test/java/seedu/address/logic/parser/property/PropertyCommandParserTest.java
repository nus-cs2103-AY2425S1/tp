package seedu.address.logic.parser.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.property.AddCommand;
import seedu.address.logic.commands.property.DeleteCommand;
import seedu.address.logic.commands.property.EditCommand;
import seedu.address.logic.commands.property.FindCommand;
import seedu.address.logic.commands.property.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.property.AddressContainsKeywordsPredicate;
import seedu.address.model.property.LandlordNameContainsKeywordsPredicate;
import seedu.address.model.property.Property;
import seedu.address.testutil.property.EditPropertyDescriptorBuilder;
import seedu.address.testutil.property.PropertyBuilder;
import seedu.address.testutil.property.PropertyUtil;


public class PropertyCommandParserTest {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private final PropertyCommandParser parser = new PropertyCommandParser();

    @Test
    public void parseCommand_add() throws Exception {
        Property property = new PropertyBuilder().build();
        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(PropertyUtil.getAddPropertyCommand(property));
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments");
        AddCommand command = (AddCommand) parser.parseCommand(commandWord, arguments);
        assertEquals(new AddCommand(property), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments");
        DeleteCommand command = (DeleteCommand) parser.parseCommand(commandWord, arguments);
        assertEquals(new DeleteCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Property property = new PropertyBuilder().build();
        EditCommand.EditPropertyDescriptor descriptor = new EditPropertyDescriptorBuilder(property).build();
        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + PropertyUtil.getEditPropertyDescriptorDetails(descriptor));
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments");
        EditCommand command = (EditCommand) parser.parseCommand(commandWord, arguments);
        assertEquals(new EditCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_findByLandlordName() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(FindCommand.COMMAND_WORD + " " + PREFIX_NAME
                + keywords.stream().collect(Collectors.joining(" ")));
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments");
        FindCommand command = (FindCommand) parser.parseCommand(commandWord, arguments);
        assertEquals(new FindCommand(new LandlordNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findByAddress() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(FindCommand.COMMAND_WORD + " " + PREFIX_ADDRESS
                + keywords.stream().collect(Collectors.joining(" ")));
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments");
        FindCommand command = (FindCommand) parser.parseCommand(commandWord, arguments);
        assertEquals(new FindCommand(new AddressContainsKeywordsPredicate(keywords)), command);
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
