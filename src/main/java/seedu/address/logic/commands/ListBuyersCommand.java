package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.model.Model;

/**
 * Lists all buyers in the client list to the user.
 */
public class ListBuyersCommand extends ListCommand {
    public static final String KEY_WORD = "buyers";
    private static final Logger logger = Logger.getLogger(ListBuyersCommand.class.getName());

    /**
     * Constructor for ListBuyersCommand.
     * Logs the creation of the command.
     */
    public ListBuyersCommand() {
        logger.info("ListBuyersCommand object created");
    }

    /**
     * Executes the command to list all buyers in the client list and sets the display to show clients.
     *
     * @param model The model which contains the client data.
     * @return A CommandResult containing feedback to the user that all buyers have been listed.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        logger.info("Executing ListBuyersCommand to list all buyers");

        // Logic to list buyers
        model.updateFilteredClientList(Model.PREDICATE_SHOW_ALL_BUYERS_ONLY);
        logger.info("Filtered client list updated to show buyers only");

        model.setDisplayClients();
        logger.info("Client display set to show buyer list");

        // Check if resulting filtered list is empty and set response message accordingly
        boolean isListEmpty = model.isFilteredClientListEmpty();
        String responseMessage = String.format(
                isListEmpty ? ListCommand.MESSAGE_SUCCESS_EMPTY_LIST : ListCommand.MESSAGE_SUCCESS,
                KEY_WORD
        );

        // Log the state of the filtered client list and the response message
        if (isListEmpty) {
            logger.info(String.format("Filtered client list is empty. Sending response: %s", responseMessage));
        } else {
            logger.info(String.format("Filtered client list is not empty. Sending response: %s", responseMessage));
        }

        return new CommandResult(responseMessage);
    }


    /**
     * Compares this ListBuyersCommand to another object for equality.
     * Two ListBuyersCommand objects are considered equal if they are of the same class at runtime.
     *
     * @param other the object to compare with this ListBuyersCommand
     * @return true if the specified object is equal to this ListBuyersCommand; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof ListBuyersCommand;
    }
}
