package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;

/**
 * Displays all ingredients in the ingredient catalogue to the user.
 */
public class ViewIngredientCatalogueCommand extends Command {

    /** The command word for viewing the ingredient catalogue. */
    public static final String COMMAND_WORD = "viewIngredientCatalogue";

    /** Message to be displayed upon successful execution of the command. */
    public static final String MESSAGE_SUCCESS = "The ingredient catalogue is shown below: \n";

    private static final Logger logger = LogsCenter.getLogger(ViewIngredientCatalogueCommand.class);

    /**
     * Executes the command to display the ingredient catalogue.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A {@code CommandResult} containing the result message and any other relevant data.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String result = model.getIngredientCatalogue().toString();
        logger.info("Viewing updated ingredient catalogue: " + result);
        return new CommandResult(MESSAGE_SUCCESS + result);
    }
}