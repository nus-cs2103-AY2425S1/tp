package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Pastry;
import seedu.address.model.product.PastryCatalogue;

/**
 * Removes an existing pastry from the bakery's pastry catalogue.
 */
public class RemovePastryCommand extends Command {
    public static final String COMMAND_WORD = "removePastry";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a pastry from the bakery's catalogue. "
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " Croissant";

    public static final String MESSAGE_REMOVE_PASTRY_SUCCESS = "Pastry removed: %1$s";
    public static final String MESSAGE_PASTRY_NOT_FOUND = "This pastry does not exist in the catalogue.";

    private final String pastryName;

    /**
     * Creates a RemovePastryCommand to remove the specified {@code Pastry} by name.
     */
    public RemovePastryCommand(String pastryName) {
        requireNonNull(pastryName);
        this.pastryName = pastryName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        PastryCatalogue pastryCatalogue = model.getPastryCatalogue();
        Pastry pastryToRemove = findPastryByName(pastryCatalogue, pastryName);

        if (pastryToRemove == null) {
            throw new CommandException(MESSAGE_PASTRY_NOT_FOUND);
        }

        pastryCatalogue.deleteProduct(pastryToRemove.getProductId());

        return new CommandResult(String.format(MESSAGE_REMOVE_PASTRY_SUCCESS, pastryName));
    }

    /**
     * Finds a pastry by its name in the pastry catalogue.
     * @return The pastry if found, otherwise null.
     */
    private Pastry findPastryByName(PastryCatalogue pastryCatalogue, String name) {
        return pastryCatalogue.getCatalogue().values().stream()
                .filter(product -> product instanceof Pastry)
                .map(product -> (Pastry) product)
                .filter(pastry -> pastry.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemovePastryCommand)) {
            return false;
        }

        RemovePastryCommand otherCommand = (RemovePastryCommand) other;
        return pastryName.equals(otherCommand.pastryName);
    }
}
