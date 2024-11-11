package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.model.Model;

/**
 * Lists all clients (both buyers and sellers) in the client list to the user.
 */
public class ListClientsCommand extends ListCommand {
    public static final String KEY_WORD = "clients";
    private static final Logger logger = Logger.getLogger(ListClientsCommand.class.getName());

    /**
     * Constructor for ListClientsCommand.
     * Logs the creation of the command.
     */
    public ListClientsCommand() {
        logger.info("ListClientsCommand object created");
    }

    /**
     * Executes the command to list all clients (buyers and sellers) and sets the display to show clients.
     *
     * @param model The model which contains the client data.
     * @return A CommandResult containing feedback to the user that all clients have been listed.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        logger.info("Executing ListClientsCommand to list all clients");

        // Logic to list clients (i.e. buyers and sellers)
        model.updateFilteredClientList(Model.PREDICATE_SHOW_ALL_CLIENTS);
        logger.info("Filtered client list updated to show all clients");

        model.setDisplayClients();
        logger.info("Client display set to show all clients");

        // Check if the resulting filtered list is empty and set response message accordingly
        boolean isListEmpty = model.isFilteredClientListEmpty();
        String responseMessage = String.format(
                isListEmpty ? ListCommand.MESSAGE_SUCCESS_EMPTY_LIST : ListCommand.MESSAGE_SUCCESS,
                KEY_WORD
        );

        // Log the state of the filtered client list and the response message
        logger.info(String.format("Filtered client list is %s. Sending response: %s",
                isListEmpty ? "empty" : "not empty", responseMessage));

        return new CommandResult(responseMessage);
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
