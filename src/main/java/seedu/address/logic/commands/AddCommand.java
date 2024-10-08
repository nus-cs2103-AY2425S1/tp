package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds an entity (person or appointment) to the address book.
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (alreadyExists(model)) {
            throw new CommandException(getDuplicateEntityMessage());
        }

        addEntity(model);
        return new CommandResult(String.format(getSuccessMessage(), formatEntity()));
    }

    /*
     * Checks if the entity being added to model already exists.
     */
    protected abstract boolean alreadyExists(Model model);

    /*
     * Adds the entity to the model.
     */
    protected abstract void addEntity(Model model);

    /*
     * Returns success message to display upon adding entity.
     */
    protected abstract String getSuccessMessage();

    /*
     * Returns the message to display when there is a duplicate.
     */
    protected abstract String getDuplicateEntityMessage();

    /**
     * Formats the entity for displaying in the success message.
     */
    protected abstract Object formatEntity();
}
