package seedu.address.logic.parser.buyer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BUYER;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.buyer.AddCommand;
import seedu.address.logic.commands.buyer.DeleteCommand;
import seedu.address.logic.commands.buyer.EditCommand;
import seedu.address.logic.commands.buyer.EditCommand.EditBuyerDescriptor;
import seedu.address.logic.commands.buyer.FindCommand;
import seedu.address.logic.commands.buyer.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.buyer.NameContainsKeywordsPredicate;
import seedu.address.testutil.buyer.BuyerBuilder;
import seedu.address.testutil.buyer.BuyerUtil;
import seedu.address.testutil.buyer.EditBuyerDescriptorBuilder;

public class BuyerCommandParserTest {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private final BuyerCommandParser parser = new BuyerCommandParser();

    @Test
    public void parseCommand_add() throws Exception {
        Buyer buyer = new BuyerBuilder().build();
        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(BuyerUtil.getAddBuyerCommand(buyer));
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments");
        AddCommand command = (AddCommand) parser.parseCommand(commandWord, arguments);
        assertEquals(new AddCommand(buyer), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_BUYER.getOneBased());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments");
        DeleteCommand command = (DeleteCommand) parser.parseCommand(commandWord, arguments);
        assertEquals(new DeleteCommand(INDEX_FIRST_BUYER), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Buyer buyer = new BuyerBuilder().build();
        EditBuyerDescriptor descriptor = new EditBuyerDescriptorBuilder(buyer).build();
        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_BUYER.getOneBased() + " " + BuyerUtil.getEditBuyerDescriptorDetails(descriptor));
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments");
        EditCommand command = (EditCommand) parser.parseCommand(commandWord, arguments);
        assertEquals(new EditCommand(INDEX_FIRST_BUYER, descriptor), command);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(FindCommand.COMMAND_WORD + " "
                + keywords.stream().collect(Collectors.joining(" ")));
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        String commandWord = matcher.group("commandWord");
        String arguments = matcher.group("arguments");
        FindCommand command = (FindCommand) parser.parseCommand(commandWord, arguments);
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
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
