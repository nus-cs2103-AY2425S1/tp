//package seedu.address.logic.parser;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
//import static seedu.address.testutil.Assert.assertThrows;
//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.logic.commands.ClearCommand;
//import seedu.address.logic.commands.ExitCommand;
//import seedu.address.logic.commands.HelpCommand;
//import seedu.address.logic.commands.buyer.AddCommand;
//import seedu.address.logic.commands.buyer.DeleteCommand;
//import seedu.address.logic.commands.buyer.EditCommand;
//import seedu.address.logic.commands.buyer.EditCommand.EditBuyerDescriptor;
//import seedu.address.logic.commands.buyer.FindCommand;
//import seedu.address.logic.commands.buyer.ViewCommand;
//import seedu.address.logic.parser.exceptions.ParseException;
//import seedu.address.model.buyer.Buyer;
//import seedu.address.model.buyer.NameContainsKeywordsPredicate;
//import seedu.address.testutil.buyer.BuyerBuilder;
//import seedu.address.testutil.buyer.BuyerUtil;
//import seedu.address.testutil.buyer.EditBuyerDescriptorBuilder;
//
//public class BuyerListParserTest {
//
//    private final BuyerListParser parser = new BuyerListParser();
//
//    @Test
//    public void parseCommand_add() throws Exception {
//        Buyer buyer = new BuyerBuilder().build();
//        AddCommand command = (AddCommand) parser.parseCommand(BuyerUtil.getAddBuyerCommand(buyer));
//        assertEquals(new AddCommand(buyer), command);
//    }
//
//    @Test
//    public void parseCommand_clear() throws Exception {
//        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
//        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
//    }
//
//    @Test
//    public void parseCommand_delete() throws Exception {
//        DeleteCommand command = (DeleteCommand) parser.parseCommand(
//                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
//        assertEquals(new DeleteCommand(INDEX_FIRST), command);
//    }
//
//    @Test
//    public void parseCommand_edit() throws Exception {
//        Buyer buyer = new BuyerBuilder().build();
//        EditBuyerDescriptor descriptor = new EditBuyerDescriptorBuilder(buyer).build();
//        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
//                + INDEX_FIRST.getOneBased() + " " + BuyerUtil.getEditBuyerDescriptorDetails(descriptor));
//        assertEquals(new EditCommand(INDEX_FIRST, descriptor), command);
//    }
//
//    @Test
//    public void parseCommand_exit() throws Exception {
//        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
//        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
//    }
//
//    @Test
//    public void parseCommand_find() throws Exception {
//        List<String> keywords = Arrays.asList("foo", "bar", "baz");
//        FindCommand command = (FindCommand) parser.parseCommand(
//                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
//        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
//    }
//
//    @Test
//    public void parseCommand_help() throws Exception {
//        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
//        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
//    }
//
//    @Test
//    public void parseCommand_view() throws Exception {
//        assertTrue(parser.parseCommand(ViewCommand.COMMAND_WORD) instanceof ViewCommand);
//    }
//
//    @Test
//    public void parseCommand_unrecognisedInput_throwsParseException() {
//        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE),
//          () -> parser.parseCommand(""));
//    }
//
//    @Test
//    public void parseCommand_unknownCommand_throwsParseException() {
//        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
//    }
//}
