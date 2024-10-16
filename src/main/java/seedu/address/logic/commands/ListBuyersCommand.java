package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all buyers in the client list to the user.
 */
public class ListBuyersCommand extends ListCommand {
    public static final String KEY_WORD = "buyers";

    /**
     * Executes the command to list all buyers in the client list and sets the display to show clients.
     *
     * @param model The model which contains the client data.
     * @return A CommandResult containing feedback to the user that all buyers have been listed.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Logic to list buyers
        model.updateFilteredClientList(Model.PREDICATE_SHOW_ALL_BUYERS_ONLY);
        model.setDisplayClients();
        return new CommandResult(String.format(ListCommand.MESSAGE_SUCCESS, KEY_WORD));
    }
}