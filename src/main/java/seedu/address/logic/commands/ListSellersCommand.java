package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.client.Client;

import static java.util.Objects.requireNonNull;

public class ListSellersCommand extends ListCommand {
    public static final String KEY_WORD = "sellers";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Logic to list sellers
        model.updateFilteredClientList(Client::isSeller);
        model.setDisplayClients();
        return new CommandResult(String.format(ListCommand.MESSAGE_SUCCESS, KEY_WORD));
    }
}
