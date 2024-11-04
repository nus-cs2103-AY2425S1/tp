package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
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

    private final Index clientIndex;

    public ListPoliciesCommand(Index clientIndex) {
        this.clientIndex = clientIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Client> lastShownList = model.getFilteredClientList();

        if (clientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_CLIENT_INDEX);
        }

        Client client = lastShownList.get(clientIndex.getZeroBased());
        List<Policy> policyList = client.getPolicies().stream().collect(Collectors.toList());

        if (policyList.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_POLICIES, client.getName()));
        }

        String policies = policyList.stream()
                .map(Policy::toString)
                .collect(Collectors.joining("\n"));

        return new CommandResult(String.format(MESSAGE_SUCCESS, client.getName(), policies));
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
