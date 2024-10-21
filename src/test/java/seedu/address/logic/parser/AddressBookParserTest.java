package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeletePotentialCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EmployeeCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindEmployeeCommand;
import seedu.address.logic.commands.FindPotentialCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListEmployeeCommand;
import seedu.address.logic.commands.ListPotentialCommand;
import seedu.address.logic.commands.PotentialCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ContractEndDate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PredicateContainer;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_employee() throws Exception {
        Person person = new PersonBuilder().withIsEmployee(true).build();
        EmployeeCommand command = (EmployeeCommand) parser.parseCommand(PersonUtil.getEmployeeCommand(person));
        assertEquals(new EmployeeCommand(person), command);
    }

    @Test
    public void parseCommand_potential() throws Exception {
        Person person = new PersonBuilder().withIsEmployee(false).build();
        PotentialCommand command = (PotentialCommand) parser.parseCommand(PersonUtil.getPotentialCommand(person));
        assertEquals(new PotentialCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " ph " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeletePotentialCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().withIsEmployee(true).build();
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
        NameContainsKeywordsPredicate nameContainsKeywordsPredicate = new NameContainsKeywordsPredicate(keywords);
        PredicateContainer predicateContainer = new PredicateContainer()
                .addNameContainsKeywordsPredicate(nameContainsKeywordsPredicate);

        // FindCommand
        FindCommand expected1 = new FindCommand(predicateContainer);
        String input1 = FindCommand.COMMAND_WORD + " " + FindCommand.ARGUMENT_WORD + " " + PREFIX_NAME
                + keywords.stream().collect(Collectors.joining(" "));
        FindCommand actual1 = (FindCommand) parser.parseCommand(input1);
        assertEquals(expected1, actual1);

        // FindEmployeeCommand
        FindEmployeeCommand expected2 = new FindEmployeeCommand(predicateContainer);
        String input2 = FindCommand.COMMAND_WORD + " " + FindEmployeeCommand.ARGUMENT_WORD + " " + PREFIX_NAME
                + keywords.stream().collect(Collectors.joining(" "));
        FindEmployeeCommand actual2 = (FindEmployeeCommand) parser.parseCommand(input2);
        assertEquals(expected2, actual2);

        // FindPotentialCommand
        FindPotentialCommand expected3 = new FindPotentialCommand(predicateContainer);
        String input3 = FindCommand.COMMAND_WORD + " " + FindPotentialCommand.ARGUMENT_WORD + " " + PREFIX_NAME
                + keywords.stream().collect(Collectors.joining(" "));
        FindPotentialCommand actual3 = (FindPotentialCommand) parser.parseCommand(input3);
        assertEquals(expected3, actual3);

        // Multiple Parameters
        List<String> keywords2 = Arrays.asList("doo", "dar", "daz");
        EmailContainsKeywordsPredicate emailContainsKeywordsPredicate = new EmailContainsKeywordsPredicate(keywords2);

        PredicateContainer predicateContainer2 = new PredicateContainer()
                .addNameContainsKeywordsPredicate(nameContainsKeywordsPredicate)
                .addEmailContainsKeywordsPredicate(emailContainsKeywordsPredicate);
        FindCommand expected4 = new FindCommand(predicateContainer2);
        String input = FindCommand.COMMAND_WORD + " " + FindCommand.ARGUMENT_WORD + " "
                + PREFIX_NAME + keywords.stream().collect(Collectors.joining(" ")) + " "
                + PREFIX_EMAIL + keywords2.stream().collect(Collectors.joining(" "));
        FindCommand actual4 = (FindCommand) parser.parseCommand(input);
        assertEquals(expected4, actual4);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        //list command
        assertTrue(parser.parseCommand(
                ListCommand.COMMAND_WORD + " " + ListCommand.ARGUMENT_WORD) instanceof ListCommand);

        //list employee command
        assertTrue(parser.parseCommand(
                ListCommand.COMMAND_WORD + " " + ListEmployeeCommand.ARGUMENT_WORD) instanceof ListEmployeeCommand);

        //list potential hire command
        assertTrue(parser.parseCommand(
                ListCommand.COMMAND_WORD + " " + ListPotentialCommand.ARGUMENT_WORD) instanceof ListPotentialCommand);

        //no parameter
        assertThrows(ParseException.class, () -> parser.parseCommand(ListCommand.COMMAND_WORD));

        //wrong parameter (is not e or ph)
        assertThrows(ParseException.class, () -> parser.parseCommand(ListCommand.COMMAND_WORD + " 3"));

        //additional text behind parameter
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " e 3") instanceof ListEmployeeCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " ph 3") instanceof ListPotentialCommand);
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
