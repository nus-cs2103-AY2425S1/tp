package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.util.FieldQuery;
import seedu.address.logic.commands.util.SearchField;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonSearchPredicate;
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
                DeleteCommand.SHORT_COMMAND_WORD + " " + PREFIX_ID + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.LONG_COMMAND_WORD + " "
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
        List<FieldQuery> fieldQueryList = List.of(
                new FieldQuery(SearchField.NAME, "name"),
                new FieldQuery(SearchField.PHONE, "phone"),
                new FieldQuery(SearchField.EMAIL, "email"),
                new FieldQuery(SearchField.LOCATION, "location"),
                new FieldQuery(SearchField.REMARK, "remark"));
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + PREFIX_NAME + " name " + PREFIX_PHONE + " phone "
                        + PREFIX_EMAIL + " email " + PREFIX_LOCATION + " location "
                        + PREFIX_REMARK + " remark");
        assertEquals(new FindCommand(new PersonSearchPredicate(fieldQueryList)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.LONG_COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.LONG_COMMAND_WORD + " 3") instanceof ListCommand);
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
    public void parseCommand_invalidAddCommandVariant_throwsParseException() {
        String parseExceptionMessage = AddCommand.generateInvalidVariantMessage(AddCommand.INVALID_VARIANTS.get(0),
                AddCommand.SHORT_COMMAND_WORD, AddCommand.LONG_COMMAND_WORD);
        assertThrows(ParseException.class, parseExceptionMessage, () ->
                parser.parseCommand(AddCommand.INVALID_VARIANTS.get(0)));
    }

    @Test
    public void parseCommand_invalidEditCommandVariant_throwsParseException() {
        String parseExceptionMessage = EditCommand.generateInvalidVariantMessage(EditCommand.INVALID_VARIANTS.get(0),
                EditCommand.SHORT_COMMAND_WORD, EditCommand.LONG_COMMAND_WORD);
        assertThrows(ParseException.class, parseExceptionMessage, () ->
                parser.parseCommand(EditCommand.INVALID_VARIANTS.get(0)));
    }

    @Test
    public void parseCommand_invalidDeleteCommandVariant_throwsParseException() {
        String parseExceptionMessage = DeleteCommand.generateInvalidVariantMessage(
                DeleteCommand.INVALID_VARIANTS.get(0),
                DeleteCommand.SHORT_COMMAND_WORD, DeleteCommand.LONG_COMMAND_WORD);
        assertThrows(ParseException.class, parseExceptionMessage, () ->
                parser.parseCommand(DeleteCommand.INVALID_VARIANTS.get(0)));
    }

    @Test
    public void parseCommand_invalidListCommandVariant_throwsParseException() {
        String parseExceptionMessage = ListCommand.generateInvalidVariantMessage(ListCommand.INVALID_VARIANTS.get(0),
                ListCommand.SHORT_COMMAND_WORD, ListCommand.LONG_COMMAND_WORD);
        assertThrows(ParseException.class, parseExceptionMessage, () ->
                parser.parseCommand(ListCommand.INVALID_VARIANTS.get(0)));
    }

    @Test
    public void parseCommand_invalidExitCommandVariant_throwsParseException() {
        String parseExceptionMessage = ExitCommand.generateInvalidVariantMessage(ExitCommand.INVALID_VARIANTS.get(0),
                ExitCommand.COMMAND_WORD);
        assertThrows(ParseException.class, parseExceptionMessage, () ->
                parser.parseCommand(ExitCommand.INVALID_VARIANTS.get(0)));
    }

    @Test
    public void parseCommand_invalidHelpCommandVariant_throwsParseException() {
        String parseExceptionMessage = HelpCommand.generateInvalidVariantMessage(HelpCommand.INVALID_VARIANTS.get(0),
                HelpCommand.COMMAND_WORD);
        assertThrows(ParseException.class, parseExceptionMessage, () ->
                parser.parseCommand(HelpCommand.INVALID_VARIANTS.get(0)));
    }


}
