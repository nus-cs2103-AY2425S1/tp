package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds an entity (person or appointment) to the address book.
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds an entity (person or appointment) to the address book. "
            + "Parameters: "
            + "ENTITY_TYPE "
            + "ENTITY_ARGUMENTS..."
            + "Example: " + COMMAND_WORD + " person "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_STATUS + "recovering "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

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
    protected abstract void addEntity(Model model) throws CommandException;

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
    protected abstract String formatEntity();
}
