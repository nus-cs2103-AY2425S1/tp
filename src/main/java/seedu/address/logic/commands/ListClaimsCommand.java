package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.claim.Claim;
import seedu.address.model.client.Client;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyType;

/**
 * Lists all claims of a specified policy type for a client in Prudy.
 * If there are no claims under the specified policy type, or if the client index is invalid,
 * an appropriate message will be shown.
 */
public class ListClaimsCommand extends Command {

    public static final String COMMAND_WORD = "list-claims";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all claims under the specified policy type "
            + "for the client identified by the index number used in the displayed client list. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_POLICY_TYPE + "POLICY_TYPE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_POLICY_TYPE + "health";

    public static final String MESSAGE_LIST_CLAIMS_SUCCESS = "Claims listed for policy type '%1$s' of client: "
            + "%2$s\n\n%3$s";
    public static final String MESSAGE_NO_CLAIMS = "No claims found for policy type '%1$s' of client: %2$s";
    public static final String MESSAGE_INVALID_CLIENT_INDEX = "The index you provided exceeds the total number of "
            + "clients you have.\nPlease check the index of the client you are looking for using the 'list' command!";
    public static final String MESSAGE_NO_POLICY_OF_TYPE = "No policy of type '%1$s' found for client: %2$s";

    private static final Logger LOGGER = Logger.getLogger(ListClaimsCommand.class.getName());

    private final Index clientIndex;
    private final PolicyType policyType;

    /**
     * Creates a ListClaimsCommand to list claims for a specific policy type
     * associated with a specified client in Prudy.
     *
     * @param clientIndex The index of the client in the filtered client list.
     * @param policyType The type of the policy whose claims are to be listed.
     */
    public ListClaimsCommand(Index clientIndex, PolicyType policyType) {
        requireNonNull(clientIndex);
        requireNonNull(policyType);
        this.clientIndex = clientIndex;
        this.policyType = policyType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        LOGGER.log(Level.INFO, "Executing ListClaimsCommand with clientIndex={0} and policyType={1}",
                new Object[]{clientIndex, policyType});

        List<Client> lastShownList = model.getFilteredClientList();

        Client client = getClientByIndex(lastShownList, clientIndex);
        Policy policy = getPolicyByType(client, policyType);

        List<Claim> claims = policy.getClaimList();

        if (claims.isEmpty()) {
            LOGGER.log(Level.INFO, "No claims found for policy type {0} of client {1}",
                    new Object[]{policyType, client.getName()});
            return new CommandResult(String.format(MESSAGE_NO_CLAIMS, policyType, client.getName()));
        }

        String claimsString = formatClaims(claims);

        return new CommandResult(String.format(MESSAGE_LIST_CLAIMS_SUCCESS, policyType, client.getName(),
                claimsString));
    }

    /**
     * Retrieves the client from the list based on the provided index.
     *
     * @param lastShownList The list of clients.
     * @param index The index of the client.
     * @return The client at the specified index.
     * @throws CommandException If the index is out of bounds.
     */
    private Client getClientByIndex(List<Client> lastShownList, Index index) throws CommandException {
        assert lastShownList != null : "Client list should not be null";
        assert index != null : "Client index should not be null";
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_CLIENT_INDEX);
        }
        return lastShownList.get(index.getZeroBased());
    }

    /**
     * Finds the policy of the specified type for the given client.
     *
     * @param client The client whose policies are to be searched.
     * @param policyType The type of policy to find.
     * @return The policy of the specified type.
     * @throws CommandException If no policy of the specified type is found.
     */
    private Policy getPolicyByType(Client client, PolicyType policyType) throws CommandException {
        return client.getPolicies().stream()
                .filter(policy -> policy.getType().equals(policyType))
                .findFirst()
                .orElseThrow(() -> {
                    LOGGER.log(Level.INFO, "No policy of type {0} found for client {1}",
                            new Object[]{policyType, client.getName()});
                    return new CommandException(String.format(MESSAGE_NO_POLICY_OF_TYPE, policyType, client.getName()));
                });
    }

    /**
     * Formats the list of claims into a numbered string representation for display.
     * Each claim is indexed starting from 1, followed by the claim status and description.
     *
     * @param claims The list of claims to format.
     * @return A formatted string representation of the claims, where each claim is preceded
     *         by its 1-based index, followed by its status and description. Each claim is
     *         separated by a newline.
     */
    private String formatClaims(List<Claim> claims) {
        assert claims != null : "Claims list should not be null";
        return IntStream.range(0, claims.size())
                .mapToObj(index -> String.format("%d. Claim Status: %s | Claim Description: %s",
                        index + 1,
                        claims.get(index).getStatus().toString(),
                        claims.get(index).getClaimDescription()))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ListClaimsCommand)) {
            return false;
        }
        ListClaimsCommand otherCommand = (ListClaimsCommand) other;
        return clientIndex.equals(otherCommand.clientIndex)
                && policyType.equals(otherCommand.policyType);
    }
}
