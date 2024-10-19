package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Command to list all properties in the address book.
 */
public class ListPropertiesCommand extends ListCommand {

    /**
     * The keyword used to trigger the listing of properties in the database.
     */
    public static final String KEY_WORD = "properties";

    /**
     * Executes the command to list all properties in the address book.
     *
     * @param model The {@code Model} which contains the application data and logic.
     * @return A {@code CommandResult} containing the feedback message for the user after the command is executed.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Logic to display properties
        model.setDisplayProperties();
        return new CommandResult(String.format(ListCommand.MESSAGE_SUCCESS, KEY_WORD));
    }
}
