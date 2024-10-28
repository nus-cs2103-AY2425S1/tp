package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.model.Model;

/**
 * Command to list all sellers in the client book.
 */
public class ListSellersCommand extends ListCommand {

    /**
     * The keyword used to trigger the listing of sellers in the database.
     */
    public static final String KEY_WORD = "sellers";
    private static final Logger logger = Logger.getLogger(ListSellersCommand.class.getName());

    /**
     * Constructor for ListSellersCommand.
     * Logs the creation of the command.
     */
    public ListSellersCommand() {
        logger.info("ListSellersCommand object created");
    }

    /**
     * Executes the command to list all sellers in the client book.
     *
     * @param model The {@code Model} which contains the application data and logic.
     * @return A {@code CommandResult} containing the feedback message for the user after the command is executed.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        logger.info("Executing ListSellersCommand to list all sellers");

        // Filter the client list to only show sellers
        model.updateFilteredClientList(Model.PREDICATE_SHOW_ALL_SELLERS_ONLY);

        // Set the model to display the clients
        model.setDisplayClients();
        logger.info("Display updated to show all sellers");

        // Return success message with the appropriate entity type
        return new CommandResult(String.format(ListCommand.MESSAGE_SUCCESS, KEY_WORD));
    }

    /**
     * Compares this ListSellersCommand to another object for equality.
     * Two ListSellersCommand objects are considered equal if they are of the same class at runtime.
     *
     * @param other the object to compare with this ListSellersCommand
     * @return true if the specified object is equal to this ListSellersCommand; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof ListSellersCommand;
    }
}
