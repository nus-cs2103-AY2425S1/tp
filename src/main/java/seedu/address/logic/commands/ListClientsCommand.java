package seedu.address.logic.commands;

import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class ListClientsCommand extends ListCommand {
    public static final String KEY_WORD = "clients";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Logic to list clients (i.e. buyers and sellers)
        model.updateFilteredClientList(client -> true);
        model.setDisplayClients();
        return new CommandResult(String.format(ListCommand.MESSAGE_SUCCESS, KEY_WORD));
    }
}
