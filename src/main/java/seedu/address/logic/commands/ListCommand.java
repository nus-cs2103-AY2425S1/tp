package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.property.Property;

/**
 * Lists all persons in the address book to the user.
 */
public abstract class ListCommand extends Command {
    /**
     * Represents the various types of entities that can be listed in the address book.
     * This enum is used to specify the kind of records a property agent wishes to list,
     * allowing for easy differentiation between different categories.
     *
     * <p>Possible values include:</p>
     */

    /* For parsing */
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all existing properties, sellers, buyers, "
            + "or clients in the database.\n"
            + "Command format: " + COMMAND_WORD + " k/KEY\n"
            + "Example commands:\n"
            + "1. List all the buyers: " + COMMAND_WORD + " k/buyers\n"
            + "2. List all the properties: " + COMMAND_WORD + " k/properties\n"
            + "\n"
            + "Parameter considerations:\n"
            + "The key must be one of the following: \"buyers\", \"sellers\", \"clients\", or \"properties\".\n"
            + "Only these four types of records are stored in the database.\n";

    public static final String MESSAGE_SUCCESS = "Listed all %1$s";

    @Override
    public abstract CommandResult execute(Model model);
}
