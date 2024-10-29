package tuteez.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static tuteez.testutil.Assert.assertThrows;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_REMARK;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import tuteez.logic.commands.AddCommand;
import tuteez.logic.commands.AddRemarkCommand;
import tuteez.logic.commands.ClearCommand;
import tuteez.logic.commands.DeleteCommand;
import tuteez.logic.commands.DeleteRemarkCommand;
import tuteez.logic.commands.EditCommand;
import tuteez.logic.commands.EditCommand.EditPersonDescriptor;
import tuteez.logic.commands.ExitCommand;
import tuteez.logic.commands.FindCommand;
import tuteez.logic.commands.HelpCommand;
import tuteez.logic.commands.ListCommand;
import tuteez.logic.commands.RemarkCommand;
import tuteez.logic.parser.exceptions.ParseException;
import tuteez.model.person.predicates.NameContainsKeywordsPredicate;
import tuteez.model.person.Person;
import tuteez.model.remark.Remark;
import tuteez.testutil.EditPersonDescriptorBuilder;
import tuteez.testutil.PersonBuilder;
import tuteez.testutil.PersonUtil;

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
    public void parseCommand_deleteAltCommand() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD_ALT + " " + INDEX_FIRST_PERSON.getOneBased());
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
    public void parseCommand_addRemark() throws Exception {
        AddRemarkCommand command = (AddRemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + AddRemarkCommand.ADD_REMARK_PARAM + " " + "remark");
        assertEquals(new AddRemarkCommand(INDEX_FIRST_PERSON, new Remark("remark")), command);
    }

    @Test
    public void parseCommand_addRemarkAltCommand() throws Exception {
        AddRemarkCommand command = (AddRemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD_ALT + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + AddRemarkCommand.ADD_REMARK_PARAM + " " + "remark");
        assertEquals(new AddRemarkCommand(INDEX_FIRST_PERSON, new Remark("remark")), command);
    }

    @Test
    public void parseCommand_deleteRemark() throws Exception {
        DeleteRemarkCommand command = (DeleteRemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + DeleteRemarkCommand.DELETE_REMARK_PARAM + " 1");
        assertEquals(new DeleteRemarkCommand(INDEX_FIRST_PERSON, INDEX_FIRST_REMARK), command);
    }

    @Test
    public void parseCommand_deleteRemarkAltCommand() throws Exception {
        DeleteRemarkCommand command = (DeleteRemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD_ALT
                + " " + INDEX_FIRST_PERSON.getOneBased() + " " + DeleteRemarkCommand.DELETE_REMARK_PARAM + " 1");
        assertEquals(new DeleteRemarkCommand(INDEX_FIRST_PERSON, INDEX_FIRST_REMARK), command);
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
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
