package seedu.address.logic.commands;

import java.util.Comparator;

import seedu.address.model.Model;
import seedu.address.model.client.Client;

/**
 * Sorts all persons in the address book by name, then lists to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final Comparator<Client> COMPARATOR_BY_NAME = (o1, o2) ->
            o1.getName().toString().compareTo(o2.getName().toString());

    public static final String MESSAGE_SUCCESS = "Listed all persons in sorted order";


    @Override
    public CommandResult execute(Model model) {
        assert model != null;
        model.updateSortedPersonList(COMPARATOR_BY_NAME);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
