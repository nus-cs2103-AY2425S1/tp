package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all clients (both buyers and sellers) in the client list to the user.
 */
public class ListClientsCommand extends ListCommand {
    public static final String KEY_WORD = "clients";

    /**
     * Executes the command to list all clients (buyers and sellers) and sets the display to show clients.
     *
     * @param model The model which contains the client data.
     * @return A CommandResult containing feedback to the user that all clients have been listed.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Logic to list clients (i.e. buyers and sellers)
        model.updateFilteredClientList(Model.PREDICATE_SHOW_ALL_CLIENTS);
        model.setDisplayClients();
        return new CommandResult(String.format(ListCommand.MESSAGE_SUCCESS, KEY_WORD));
    }

    /**
     * Compares this ListClientsCommand to another object for equality.
     * Two ListClientsCommand objects are considered equal if they are of the same class at runtime.
     *
     * @param other the object to compare with this ListClientsCommand
     * @return true if the specified object is equal to this ListClientsCommand; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof ListClientsCommand;
    }
}
