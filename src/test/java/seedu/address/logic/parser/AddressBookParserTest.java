package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeletePolicyCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListExpiringPoliciesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.CompositePredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.policy.EditPolicyDescriptor;
import seedu.address.model.policy.LifePolicy;
import seedu.address.model.policy.PolicyType;
import seedu.address.model.policy.PremiumAmount;
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
    public void parseCommand_findClient() throws Exception {
        String input = "find-client n/Alice Bob";

        // Construct expected FindCommand
        List<Predicate<Person>> predicatesList = new ArrayList<>();
        predicatesList.add(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        Predicate<Person> combinedPredicate = new CompositePredicate(predicatesList);
        FindCommand expectedCommand = new FindCommand(combinedPredicate);

        // Parse command using AddressBookParser
        Command command = parser.parseCommand(input);

        // Assert that the parsed command matches the expected command
        assertEquals(expectedCommand, command);
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
    public void parseCommand_addPolicy() throws Exception {
        // This is hardcoded for now.
        // Will change in future commits.
        AddPolicyCommand command = (AddPolicyCommand) parser.parseCommand(
                AddPolicyCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                + " pt/life");
        LifePolicy life = new LifePolicy();
        assertEquals(new AddPolicyCommand(INDEX_FIRST_PERSON, life), command);
    }
    @Test
    public void parseCommand_editPolicy() throws Exception {
        // This is hardcoded for now.
        // Will change in future commits.
        EditPolicyCommand command = (EditPolicyCommand) parser.parseCommand(
                EditPolicyCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + " " + PREFIX_POLICY_TYPE + "life" + " pa/200");
        EditPolicyDescriptor policy = new EditPolicyDescriptor(PolicyType.LIFE);
        policy.setPremiumAmount(new PremiumAmount(200));
        assertEquals(new EditPolicyCommand(INDEX_FIRST_PERSON, policy), command);
    }
    @Test
    public void parseCommand_deletePolicy() throws Exception {
        // This is hardcoded for now.
        // Will change in future commits.
        DeletePolicyCommand command = (DeletePolicyCommand) parser.parseCommand(
                DeletePolicyCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + " " + PREFIX_POLICY_TYPE + "life");
        final Set<PolicyType> policyTypes = new HashSet<>();
        policyTypes.add(PolicyType.LIFE);
        assertEquals(new DeletePolicyCommand(INDEX_FIRST_PERSON, policyTypes), command);
    }

    @Test
    public void parseCommand_listExpiringPolicies() throws Exception {
        // test valid usage of the command with no arguments (should default to 30 days)
        assertTrue(parser.parseCommand(ListExpiringPoliciesCommand.COMMAND_WORD)
                instanceof ListExpiringPoliciesCommand);

        // test valid usage of the command with days argument (eg. "listExpiringPolicies 60")
        ListExpiringPoliciesCommand commandWithDays = (ListExpiringPoliciesCommand) parser.parseCommand(
                ListExpiringPoliciesCommand.COMMAND_WORD + " 60");
        assertTrue(commandWithDays instanceof ListExpiringPoliciesCommand);
        assertEquals(60, commandWithDays.getDaysFromExpiry());

        // test invalid usage where extra invalid arguments are provided (eg., "listExpiringPolicies extraArgument")
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListExpiringPoliciesCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(ListExpiringPoliciesCommand.COMMAND_WORD + " extraArgument"));

        // test invalid usage where days argument is not an integer (eg. "listExpiringPolicies abc")
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListExpiringPoliciesCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(ListExpiringPoliciesCommand.COMMAND_WORD + " abc"));
        // test invalid usage where days argument is a negative integer (eg. "listExpiringPolicies -5")
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListExpiringPoliciesCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand(ListExpiringPoliciesCommand.COMMAND_WORD + " -5"));
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
