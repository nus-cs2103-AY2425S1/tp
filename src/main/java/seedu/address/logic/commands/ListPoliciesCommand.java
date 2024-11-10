package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.policy.Policy;

/**
 * Lists all policies of a specified client in Prudy.
 */
public class ListPoliciesCommand extends Command {

    public static final String COMMAND_WORD = "list-policies";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all policies of the client identified "
            + "by the index number used in the displayed client list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Policies listed for client: %1$s\n%2$s";
    public static final String MESSAGE_NO_POLICIES = "No policies found for client: %1$s";
    public static final String MESSAGE_INVALID_CLIENT_INDEX = "The index you provided exceeds the total number of "
            + "clients you have.\nPlease check the index of the client you are looking for using the 'list' command!";

    private static final Logger LOGGER = Logger.getLogger(ListPoliciesCommand.class.getName());

    private final Index clientIndex;

    /**
     * Constructs a ListPoliciesCommand to list all policies associated with a specified client.
     *
     * @param clientIndex The index of the client in the filtered client list whose policies are to be listed.
     *                    Must be a non-null positive integer index.
     */
    public ListPoliciesCommand(Index clientIndex) {
        requireNonNull(clientIndex);
        this.clientIndex = clientIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model, "Model must not be null");
        LOGGER.log(Level.INFO, "Executing ListPoliciesCommand with clientIndex={0}", clientIndex);

        List<Client> lastShownList = model.getFilteredClientList();
        assert lastShownList != null : "Client list should not be null";

        Client client = getClientFromList(lastShownList, clientIndex);
        List<Policy> policyList = getPoliciesForClient(client);

        if (policyList.isEmpty()) {
            LOGGER.log(Level.INFO, "No policies found for client {0}", client.getName());
            return new CommandResult(String.format(MESSAGE_NO_POLICIES, client.getName()));
        }

        String formattedPolicies = formatPolicies(policyList);
        LOGGER.log(Level.INFO, "Policies listed successfully for client {0}", client.getName());
        return new CommandResult(String.format(MESSAGE_SUCCESS, client.getName(), formattedPolicies));
    }

    /**
     * Retrieves the client at the specified index from the list.
     *
     * @param clients The list of clients.
     * @param index The index of the client in the list.
     * @return The client at the specified index.
     * @throws CommandException If the index is out of bounds.
     */
    private Client getClientFromList(List<Client> clients, Index index) throws CommandException {
        assert clients != null : "Clients list should not be null";
        if (index.getZeroBased() >= clients.size()) {
            LOGGER.log(Level.INFO, "Invalid client index: {0}", index.getZeroBased());
            throw new CommandException(MESSAGE_INVALID_CLIENT_INDEX);
        }
        return clients.get(index.getZeroBased());
    }

    /**
     * Retrieves the list of policies for a given client.
     *
     * @param client The client whose policies are being retrieved.
     * @return A list of policies belonging to the client.
     */
    private List<Policy> getPoliciesForClient(Client client) {
        requireNonNull(client, "Client must not be null");
        return client.getPolicies().stream().collect(Collectors.toList());
    }

    /**
     * Formats a list of policies into a newline-separated string representation.
     *
     * @param policies The list of policies to format.
     * @return A formatted string representation of the policies, separated by newlines.
     */
    private String formatPolicies(List<Policy> policies) {
        assert policies != null : "Policies list should not be null";
        return policies.stream()
                .map(Policy::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true; // short circuit if same object
        }
        if (!(other instanceof ListPoliciesCommand)) {
            return false; // check null and type compatibility
        }
        ListPoliciesCommand otherCommand = (ListPoliciesCommand) other;
        return clientIndex.equals(otherCommand.clientIndex);
    }
}
