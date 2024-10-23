package seedu.address.logic.commands;

import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Lists all persons in the address book to the user.
 */
public class ViewIngredientCatalogueCommand extends Command {

    public static final String COMMAND_WORD = "viewIngredientCatalogue";

    public static final String MESSAGE_SUCCESS = "The ingredient catalogue is shown below: \n";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String result = model.getIngredientCatalogue().toString();
        return new CommandResult(MESSAGE_SUCCESS + result);
    }
}
