package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.client.Client;

import static java.util.Objects.requireNonNull;

public class ListBuyersCommand extends ListCommand {
    public static final String KEY_WORD = "buyers";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Logic to list buyers
        model.updateFilteredClientList(Client::isBuyer);
        model.setDisplayClients();
        return new CommandResult(String.format(ListCommand.MESSAGE_SUCCESS, KEY_WORD));
    }
}
