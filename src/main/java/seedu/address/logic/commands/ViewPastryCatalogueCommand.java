package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ViewPastryCatalogueCommand extends Command {

    public static final String COMMAND_WORD = "viewPastryCatalogue";

    public static final String MESSAGE_SUCCESS = "The pastry catalogue is shown below: \n\n";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String result = model.getPastryCatalogue().toString();
        return new CommandResult(MESSAGE_SUCCESS + result);
    }
}
