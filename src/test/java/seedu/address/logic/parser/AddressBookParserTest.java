package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FullNameContainsPredicate;
import seedu.address.model.person.JobCodePredicate;
import seedu.address.model.person.Person;
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
        assertThrows(ParseException.class, () -> parser.parseCommand(ClearCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    //    @Test
    //    public void parseCommand_edit() throws Exception {
    //        Person person = new PersonBuilder().build();
    //        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
    //        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
    //                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
    //        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    //    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertThrows(ParseException.class, () -> parser.parseCommand(ExitCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_findName() throws Exception {
        Person person = new PersonBuilder().withName("Bobby Coo").build();
        String userInput = PersonUtil.getFindCommandByName(person);
        FindCommand command = (FindCommand) parser.parseCommand(userInput);
        assertEquals(new FindCommand(new FullNameContainsPredicate("Bobby Coo")), command);
    }

    //    @Test
    //    public void parseCommand_findNameEmail() throws Exception {
    //        Person person = new PersonBuilder().withName("Bobby Coo")
    //                .withEmail("bobby@example.com").build();
    //        String userInput = PersonUtil.getFindCommandByNameEmail(person);
    //        FindCommand command = (FindCommand) parser.parseCommand(userInput);
    //        assertEquals(new FindCommand(new NameEmailPredicate("Bobby Coo", "bobby@example.com")), command);
    //    }
    //
    //    @Test
    //    public void parseCommand_findNamePhone() throws Exception {
    //        Person person = new PersonBuilder().withName("Bobby Coo")
    //                .withPhone("12121212").build();
    //        String userInput = PersonUtil.getFindCommandByNamePhone(person);
    //        FindCommand command = (FindCommand) parser.parseCommand(userInput);
    //        assertEquals(new FindCommand(new NamePhonePredicate("Bobby Coo", "12121212")), command);
    //    }

    @Test
    public void parseCommand_findJobCode() throws Exception {
        Person person = new PersonBuilder().withJobCode("SWE1234").build();
        String userInput = PersonUtil.getFindCommandByJobCode(person);
        FindCommand command = (FindCommand) parser.parseCommand(userInput);
        assertEquals(new FindCommand(new JobCodePredicate("SWE1234")), command);
    }

    //    @Test
    //    public void parseCommand_findTag() throws Exception {
    //        Person person = new PersonBuilder().withTag("tp").build();
    //        String userInput = PersonUtil.getFindCommandByTag(person);
    //        FindCommand command = (FindCommand) parser.parseCommand(userInput);
    //        assertEquals(new FindCommand(new TagPredicate(Arrays.asList("tp"))), command);
    //   }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertThrows(ParseException.class, () -> parser.parseCommand(HelpCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertThrows(ParseException.class, () -> parser.parseCommand(ListCommand.COMMAND_WORD + " 3"));
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
