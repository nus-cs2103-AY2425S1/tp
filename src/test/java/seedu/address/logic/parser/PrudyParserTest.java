package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLAIM_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLAIM;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddClientCommand;
import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteClaimCommand;
import seedu.address.logic.commands.DeleteClientCommand;
import seedu.address.logic.commands.DeletePoliciesCommand;
import seedu.address.logic.commands.EditClaimCommand;
import seedu.address.logic.commands.EditClientCommand;
import seedu.address.logic.commands.EditClientCommand.EditClientDescriptor;
import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindClientCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListClaimsCommand;
import seedu.address.logic.commands.ListClientCommand;
import seedu.address.logic.commands.ListExpiringPoliciesCommand;
import seedu.address.logic.commands.ListPoliciesCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.claim.ClaimStatus;
import seedu.address.model.claim.EditClaimDescriptor;
import seedu.address.model.client.Client;
import seedu.address.model.client.CompositePredicate;
import seedu.address.model.client.NameContainsKeywordsPredicate;
import seedu.address.model.policy.EditPolicyDescriptor;
import seedu.address.model.policy.LifePolicy;
import seedu.address.model.policy.PolicyType;
import seedu.address.model.policy.PremiumAmount;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.ClientUtil;
import seedu.address.testutil.EditClientDescriptorBuilder;

public class PrudyParserTest {

    private final PrudyParser parser = new PrudyParser();

    @Test
    public void parseCommand_add() throws Exception {
        Client client = new ClientBuilder().build();
        AddClientCommand command = (AddClientCommand) parser.parseCommand(ClientUtil.getAddClientCommand(client));
        assertEquals(new AddClientCommand(client), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteClientCommand command = (DeleteClientCommand) parser.parseCommand(
                DeleteClientCommand.COMMAND_WORD + " " + INDEX_FIRST_CLIENT.getOneBased());
        assertEquals(new DeleteClientCommand(INDEX_FIRST_CLIENT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Client client = new ClientBuilder().build();
        EditClientDescriptor descriptor = new EditClientDescriptorBuilder(client).build();
        EditClientCommand command = (EditClientCommand) parser.parseCommand(EditClientCommand.COMMAND_WORD + " "
                + INDEX_FIRST_CLIENT.getOneBased() + " " + ClientUtil.getEditClientDescriptorDetails(descriptor));
        assertEquals(new EditClientCommand(INDEX_FIRST_CLIENT, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findClient() throws Exception {
        String input = "find-client n/Alice Bob";

        // Construct expected FindClientCommand
        List<Predicate<Client>> predicatesList = new ArrayList<>();
        predicatesList.add(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        Predicate<Client> combinedPredicate = new CompositePredicate(predicatesList);
        FindClientCommand expectedCommand = new FindClientCommand(combinedPredicate);

        // Parse command using PrudyParser
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
        assertTrue(parser.parseCommand(ListClientCommand.COMMAND_WORD) instanceof ListClientCommand);
        assertTrue(parser.parseCommand(ListClientCommand.COMMAND_WORD + " 3") instanceof ListClientCommand);
    }

    @Test
    public void parseCommand_addPolicy() throws Exception {
        AddPolicyCommand command = (AddPolicyCommand) parser.parseCommand(
                AddPolicyCommand.COMMAND_WORD + " " + INDEX_FIRST_CLIENT.getOneBased()
                + " pt/life");
        LifePolicy life = new LifePolicy();
        assertEquals(new AddPolicyCommand(INDEX_FIRST_CLIENT, life), command);
    }
    @Test
    public void parseCommand_editPolicy() throws Exception {
        EditPolicyCommand command = (EditPolicyCommand) parser.parseCommand(
                EditPolicyCommand.COMMAND_WORD + " " + INDEX_FIRST_CLIENT.getOneBased()
                        + " " + PREFIX_POLICY_TYPE + "life" + " pa/200");
        EditPolicyDescriptor policy = new EditPolicyDescriptor(PolicyType.LIFE);
        policy.setPremiumAmount(new PremiumAmount(200));
        assertEquals(new EditPolicyCommand(INDEX_FIRST_CLIENT, policy), command);
    }

    @Test
    public void parseCommand_editClaim() throws Exception {
        EditClaimCommand actualCommand = (EditClaimCommand) parser.parseCommand(
                EditClaimCommand.COMMAND_WORD + " " + INDEX_FIRST_CLIENT.getOneBased()
                        + " " + PREFIX_POLICY_TYPE + "health"
                        + " " + PREFIX_CLAIM_INDEX + "1"
                        + " " + PREFIX_CLAIM_STATUS + "approved"
                        + " " + PREFIX_CLAIM_DESC + "Claim approved for payment");
        EditClaimDescriptor claim = new EditClaimDescriptor();
        claim.setStatus(ClaimStatus.APPROVED);
        claim.setDescription("Claim approved for payment");
        EditClaimCommand expectedCommand = new EditClaimCommand(INDEX_FIRST_CLIENT, PolicyType.HEALTH,
                INDEX_FIRST_CLAIM, claim);
        assertEquals(expectedCommand, actualCommand);


    }
    @Test
    public void parseCommand_deletePolicy() throws Exception {
        DeletePoliciesCommand command = (DeletePoliciesCommand) parser.parseCommand(
                DeletePoliciesCommand.COMMAND_WORD + " " + INDEX_FIRST_CLIENT.getOneBased()
                        + " " + PREFIX_POLICY_TYPE + "life");
        final Set<PolicyType> policyTypes = new HashSet<>();
        policyTypes.add(PolicyType.LIFE);
        assertEquals(new DeletePoliciesCommand(INDEX_FIRST_CLIENT, policyTypes), command);
    }

    @Test
    public void parseCommand_listExpiringPolicies() throws Exception {
        // default usage (30 days)
        ListExpiringPoliciesCommand commandDefault = (ListExpiringPoliciesCommand)
                parser.parseCommand(ListExpiringPoliciesCommand.COMMAND_WORD);
        assertEquals(new ListExpiringPoliciesCommand(30), commandDefault);

        // custom days (e.g., 60)
        ListExpiringPoliciesCommand commandWithDays = (ListExpiringPoliciesCommand)
                parser.parseCommand(ListExpiringPoliciesCommand.COMMAND_WORD + " 60");
        assertEquals(new ListExpiringPoliciesCommand(60), commandWithDays);
    }

    @Test
    public void parseCommand_listPolicies() throws Exception {
        ListPoliciesCommand command = (ListPoliciesCommand) parser.parseCommand(
                ListPoliciesCommand.COMMAND_WORD + " " + INDEX_FIRST_CLIENT.getOneBased());
        assertEquals(new ListPoliciesCommand(INDEX_FIRST_CLIENT), command);
    }

    @Test
    public void parseCommand_listClaims() throws Exception {
        ListClaimsCommand expectedCommand = new ListClaimsCommand(INDEX_FIRST_CLIENT, PolicyType.HEALTH);
        String validInput = ListClaimsCommand.COMMAND_WORD + " " + INDEX_FIRST_CLIENT.getOneBased() + " pt/health";

        Command command = parser.parseCommand(validInput);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parseCommand_deleteClaim() throws Exception {
        String userInput = DeleteClaimCommand.COMMAND_WORD + " " + INDEX_FIRST_CLIENT.getOneBased()
                + " pt/health c/1";

        DeleteClaimCommand expectedCommand = new DeleteClaimCommand(
                INDEX_FIRST_CLIENT, PolicyType.HEALTH, INDEX_FIRST_CLAIM);

        DeleteClaimCommand actualCommand = (DeleteClaimCommand) parser.parseCommand(userInput);
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
                -> parser.parseCommand("unknownCommand"));
    }
}
