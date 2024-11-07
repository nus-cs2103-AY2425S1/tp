package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.client.Client;

/**
 * Finds and lists all clients Prudy that match the specified criteria.
 */
public class FindClientCommand extends Command {

    public static final String COMMAND_WORD = "find-client";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all clients that match the specified criteria "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [pt/POLICY_TYPE]\n"
            + "Example: " + COMMAND_WORD + " n/John Doe p/99998888";

    private final Predicate<Client> predicate;

    public FindClientCommand(Predicate<Client> predicate) {
        assert predicate != null : "Predicate should not be null";
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredClientList(predicate);
        assert model.getFilteredClientList() != null : "Filtered client list should not be null";
        assert model.getFilteredClientList().size() >= 0 : "Filtered client list size should be non-negative";
        return new CommandResult(
                String.format(Messages.MESSAGE_CLIENTS_LISTED_OVERVIEW, model.getFilteredClientList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindClientCommand // instanceof handles nulls
                && predicate.equals(((FindClientCommand) other).predicate)); // state check
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }

    public Predicate<Client> getPredicate() {
        return predicate;
    }
}
