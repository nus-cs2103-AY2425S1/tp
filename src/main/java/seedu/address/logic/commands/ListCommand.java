package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Abstract class that serves as the base for commands to list different entities in the application
 * (such as buyers, sellers, clients, and properties). Subclasses of {@code ListCommand} are
 * responsible for specifying which entities are to be listed and implementing the listing logic.
 */
public abstract class ListCommand extends Command {

    /**
     * The main command word used to trigger the list functionality in the application.
     */
    public static final String COMMAND_WORD = "list";

    /**
     * Usage message to guide users on how to use the 'list' command. It provides examples of listing
     * buyers, sellers, clients, or properties from the database, and clarifies the allowed keys.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all existing properties, sellers, "
            + "buyers, or clients in the database.\n"
            + "Command format: " + COMMAND_WORD + " k/KEY\n"
            + "Example commands:\n"
            + "1. List all the buyers: " + COMMAND_WORD + " k/buyers\n"
            + "2. List all the properties: " + COMMAND_WORD + " k/properties\n"
            + "\n"
            + "Parameter considerations:\n"
            + "The key must be one of the following: \"buyers\", \"sellers\", \"clients\", or "
            + "\"properties\".\n"
            + "Only these four types of records are stored in the database.\n";

    /**
     * Success message template used to confirm that the listing operation has been successfully executed
     * for a specific type of entity. The placeholder will be replaced with the type of entity listed
     * (e.g., buyers, sellers, clients, or properties).
     */
    public static final String MESSAGE_SUCCESS = "Listed all %1$s";

    /**
     * Executes the list command to retrieve and display a specific category of records from the model.
     *
     * @param model The {@code Model} which contains the application's data and logic for filtering
     *              and retrieving records.
     * @return A {@code CommandResult} containing the feedback message for the user after the command is executed.
     */
    @Override
    public abstract CommandResult execute(Model model);
}
