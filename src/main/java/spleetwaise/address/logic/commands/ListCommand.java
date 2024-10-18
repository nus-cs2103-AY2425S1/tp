package spleetwaise.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static spleetwaise.address.model.AddressBookModel.PREDICATE_SHOW_ALL_PERSONS;

import spleetwaise.address.model.AddressBookModel;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(AddressBookModel model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
