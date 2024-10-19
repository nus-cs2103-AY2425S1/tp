package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Command to list all sellers in the client book.
 */
public class ListSellersCommand extends ListCommand {

    /**
     * The keyword used to trigger the listing of sellers in the database.
     */
    public static final String KEY_WORD = "sellers";

    /**
     * Executes the command to list all sellers in the client book.
     *
     * @param model The {@code Model} which contains the application data and logic.
     * @return A {@code CommandResult} containing the feedback message for the user after the command is executed.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Filter the client list to only show sellers
        model.updateFilteredClientList(Model.PREDICATE_SHOW_ALL_SELLERS_ONLY);

        // Set the model to display the clients
        model.setDisplayClients();

        // Return success message with the appropriate entity type
        return new CommandResult(String.format(ListCommand.MESSAGE_SUCCESS, KEY_WORD));
    }
}
