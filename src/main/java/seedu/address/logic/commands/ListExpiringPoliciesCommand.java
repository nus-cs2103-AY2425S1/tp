package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyExpiryDatePredicate;

/**
 * Lists all policies in Prudy that are nearing expiry within the user-specified number of days.
 * Defaults to 30 days if no argument is provided.
 */
public class ListExpiringPoliciesCommand extends Command {

    /**
     * Command word to trigger this command in the application's UI.
     */
    public static final String COMMAND_WORD = "list-expiring-policies";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all policies nearing expiry within the specified number of days.\n"
            + "If no number is provided, it defaults to 30 days.\n"
            + "Parameters: [days from expiry] (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 60";

    private static final String MESSAGE_SUCCESS = "The following policies are expiring within %1$d day(s):\n\n";
    private static final String MESSAGE_NO_EXPIRING_POLICY = "No policies expiring within the next %1$d day(s)!";
    private static final String MESSAGE_FAILURE = "Failed to retrieve expiring policies. Please try again.";
    private static final String MESSAGE_POLICY_LISTED_DETAILS = "Insuree name: %1$s   |   Insuree phone: %2$s\n"
            + "Policy Type: %3$s   |   Premium Amount: %4$s\nCoverage Amount: %5$s   |   Expiry Date: %6$s\n\n";

    private static final Logger LOGGER = Logger.getLogger(ListExpiringPoliciesCommand.class.getName());

    private final int daysFromExpiry;

    /**
     * Creates a ListExpiringPoliciesCommand to list policies nearing expiry within the given number of days.
     * Defaults to 30 days if no number is provided.
     * The Default behaviour is handles within {@code ListExpiringPoliciesCommandParser}.
     */
    public ListExpiringPoliciesCommand(int daysFromExpiry) {
        assert daysFromExpiry > 0 : "daysFromExpiry must be a positive integer";
        this.daysFromExpiry = daysFromExpiry;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model, "Model must not be null");
        LOGGER.log(Level.INFO, "Executing ListExpiringPoliciesCommand with daysFromExpiry={0}", daysFromExpiry);

        try {
            String resultMessage = getExpiringPoliciesMessage(model);
            return new CommandResult(resultMessage);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occurred while retrieving expiring policies.", e);
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

    /**
     * Retrieves a message listing all policies nearing expiry within the specified days or a no-policy-found message.
     *
     * @param model The model to retrieve the client and policy data from.
     * @return A message listing expiring policies or indicating no expiring policies were found.
     */
    private String getExpiringPoliciesMessage(Model model) {
        LocalDate currentDate = LocalDate.now();
        PolicyExpiryDatePredicate predicate = new PolicyExpiryDatePredicate(currentDate, daysFromExpiry);
        List<Client> clients = requireNonNull(model.getFilteredClientList(), "Client list must not be null");

        StringBuilder resultMessage = new StringBuilder(String.format(MESSAGE_SUCCESS, daysFromExpiry));
        boolean hasExpiringPolicies = appendExpiringPolicies(resultMessage, clients, predicate);

        if (!hasExpiringPolicies) {
            LOGGER.log(Level.INFO, "No expiring policies found within {0} days", daysFromExpiry);
            return String.format(MESSAGE_NO_EXPIRING_POLICY, daysFromExpiry);
        }

        LOGGER.log(Level.INFO, "Expiring policies found within {0} days", daysFromExpiry);
        return resultMessage.toString();
    }

    /**
     * Appends details of expiring policies to the result message if they match the expiry predicate.
     *
     * @param resultMessage The StringBuilder to append the policy details.
     * @param clients       The list of clients to check for expiring policies.
     * @param predicate     The predicate to filter policies based on expiry date.
     * @return True if at least one expiring policy is found, otherwise false.
     */
    private boolean appendExpiringPolicies(StringBuilder resultMessage, List<Client> clients,
                                           PolicyExpiryDatePredicate predicate) {
        boolean hasExpiringPolicies = false;

        for (Client client : clients) {
            if (appendClientExpiringPolicies(resultMessage, client, predicate)) {
                hasExpiringPolicies = true;
            }
        }
        return hasExpiringPolicies;
    }

    /**
     * Appends expiring policies of a specific client to the result message.
     *
     * @param resultMessage The StringBuilder to append the policy details.
     * @param client        The client whose policies are to be checked.
     * @param predicate     The predicate to filter policies based on expiry date.
     * @return True if at least one expiring policy is found for the client, otherwise false.
     */
    private boolean appendClientExpiringPolicies(StringBuilder resultMessage, Client client,
                                                 PolicyExpiryDatePredicate predicate) {
        requireNonNull(client, "Client must not be null");
        Set<Policy> policies = requireNonNull(client.getPolicies(),
                "Policies set must not be null for client: " + client.getName());

        boolean hasExpiringPolicy = false;

        for (Policy policy : policies) {
            if (isExpiringPolicy(policy, predicate)) {
                appendPolicyDetails(resultMessage, client, policy);
                hasExpiringPolicy = true;
            }
        }
        return hasExpiringPolicy;
    }

    /**
     * Checks if a policy matches the expiry predicate.
     *
     * @param policy    The policy to be checked.
     * @param predicate The predicate to filter policies based on expiry date.
     * @return True if the policy is expiring, otherwise false.
     */
    private boolean isExpiringPolicy(Policy policy, PolicyExpiryDatePredicate predicate) {
        requireNonNull(policy, "Policy must not be null");
        return predicate.test(policy);
    }

    /**
     * Appends the details of a single policy to the result message.
     *
     * @param resultMessage The StringBuilder to append the details to.
     * @param client        The client who holds the policy.
     * @param policy        The policy whose details are to be appended.
     */
    private void appendPolicyDetails(StringBuilder resultMessage, Client client, Policy policy) {
        resultMessage.append(String.format(
                MESSAGE_POLICY_LISTED_DETAILS,
                client.getName().toString(),
                client.getPhone().toString(),
                policy.getType().toString(),
                policy.getPremiumAmount().toString(),
                policy.getCoverageAmount().toString(),
                policy.getExpiryDate()
        ));
    }

    public int getDaysFromExpiry() {
        return daysFromExpiry;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ListExpiringPoliciesCommand)) {
            return false;
        }
        ListExpiringPoliciesCommand otherCommand = (ListExpiringPoliciesCommand) other;
        return daysFromExpiry == otherCommand.daysFromExpiry;
    }

    @Override
    public String toString() {
        return COMMAND_WORD + " " + daysFromExpiry;
    }
}
