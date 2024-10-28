package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.model.Model;

/**
 * Command to list all properties in the address book.
 */
public class ListPropertiesCommand extends ListCommand {

    /**
     * The keyword used to trigger the listing of properties in the database.
     */
    public static final String KEY_WORD = "properties";
    private static final Logger logger = Logger.getLogger(ListPropertiesCommand.class.getName());

    /**
     * Constructor for ListPropertiesCommand.
     * Logs the creation of the command.
     */
    public ListPropertiesCommand() {
        logger.info("ListPropertiesCommand object created");
    }

    /**
     * Executes the command to list all properties in the address book.
     *
     * @param model The {@code Model} which contains the application data and logic.
     * @return A {@code CommandResult} containing the feedback message for the user after the command is executed.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        logger.info("Executing ListPropertiesCommand to list all properties");

        // Logic to display properties
        model.setDisplayProperties();
        logger.info("Display updated to show all properties");

        return new CommandResult(String.format(ListCommand.MESSAGE_SUCCESS, KEY_WORD));
    }

    /**
     * Compares this ListPropertiesCommand to another object for equality.
     * Two ListPropertiesCommand objects are considered equal if they are of the same class at runtime.
     *
     * @param other the object to compare with this ListPropertiesCommand
     * @return true if the specified object is equal to this ListPropertiesCommand; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof ListPropertiesCommand;
    }
}
