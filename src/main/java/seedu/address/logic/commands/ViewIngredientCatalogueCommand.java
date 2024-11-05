package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;

import java.util.logging.Logger;

/**
 * Lists all persons in the address book to the user.
 */
public class ViewIngredientCatalogueCommand extends Command {

    public static final String COMMAND_WORD = "viewIngredientCatalogue";

    public static final String MESSAGE_SUCCESS = "The ingredient catalogue is shown below: \n";
    private static final Logger logger = LogsCenter.getLogger(ViewIngredientCatalogueCommand.class);


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String result = model.getIngredientCatalogue().toString();
        logger.info("Viewing updated ingredient catalogue: " + result); // Debug log
        return new CommandResult(MESSAGE_SUCCESS + result);
    }
}
