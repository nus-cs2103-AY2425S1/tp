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
     * buyers, sellers, clients, properties, or meetings from the address book, and clarifies the allowed keys.
     */
    public static final String MESSAGE_USAGE = String.format(
            "%s: Shows a list of all existing buyers, sellers, clients (i.e., buyers and sellers), "
                    + "properties, or meetings in the address book.\n"
                    + "Parameters: k/KEY\nRestrictions:\n\tKey must be one of the following: "
                    + "\"buyers\", \"sellers\", \"clients\", \"properties\", or \"meetings\" (case-insensitive).\n"
                    + "\tOnly these 5 types of records are stored in the address book.",
            COMMAND_WORD
    );



    /**
     * Success message template used to confirm that the listing operation has been successfully executed
     * for a specific type of entity. The placeholder will be replaced with the type of entity listed
     * (e.g., buyers, sellers, clients, or properties).
     */
    public static final String MESSAGE_SUCCESS = "Listed all %1$s";

    public static final String MESSAGE_SUCCESS_EMPTY_LIST = "There are no %1$s yet!";

    /**
     * Executes the list command to retrieve and display a specific category of records from the model.
     *
     * @param model The {@code Model} which contains the application's data and logic for filtering
     *              and retrieving records.
     * @return A {@code CommandResult} containing the feedback message for the user after the command is executed.
     */
    @Override
    public abstract CommandResult execute(Model model);

    @Override
    public abstract boolean equals(Object object);
}
