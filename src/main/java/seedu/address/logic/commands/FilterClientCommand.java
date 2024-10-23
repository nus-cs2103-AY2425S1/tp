package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Name;

/**
 * Filters clients based on their name.
 */
public class FilterClientCommand extends Command {

    /** The command word to trigger the filtering action. */
    public static final String COMMAND_WORD = "filterclient";

    /**
     * Usage information for the filterclient command.
     * Provides a description of the command's purpose and the format for entering client names.
     */
    public static final String MESSAGE_USAGE = String.format(
            "%s: Filters the clients based on their name.\nParameters: %sNAME\nRestrictions: %s",
            COMMAND_WORD,
            PREFIX_NAME,
            Name.MESSAGE_CONSTRAINTS
    );


    /** Message indicating that the filtering operation failed. */
    public static final String MESSAGE_FAILURE = "Unable to filter clients.";

    public static final String MESSAGE_SUCCESS = "Listed all clients";

    /** Message used to format the arguments passed to the command. */
    public static final String MESSAGE_ARGUMENTS = "Name: %1$s";
    private static final Logger logger = LogsCenter.getLogger(FilterClientCommand.class);

    /** The client whose name is being used for filtering. */
    private final Name name;

    /**
     * Constructs a FilterClientCommand to filter the specified {@code Client}.
     *
     * @param name The client to filter by.
     * @throws NullPointerException If the provided client is null.
     */
    public FilterClientCommand(Name name) {
        requireAllNonNull(name);
        this.name = name;
        assert this.name != null : "Name should not be null";
    }

    /**
     * Executes the command to filter clients based on the provided name.
     *
     * @param model The model which contains the client data to be filtered.
     * @return The result of the command execution.
     * @throws CommandException If the filtering operation fails.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Filtering clients with name starting with: " + name);
        model.updateFilteredClientList(client -> client.getName().toString().matches("(?i)^" + name.toString() + ".*"));
        logger.info("Displaying clients with name starting with: " + name);
        model.setDisplayClients();
        return new CommandResult(String.format(MESSAGE_SUCCESS + " with name starting with: " + name.toString()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof FilterClientCommand)) {
            return false;
        }
        // state check
        FilterClientCommand e = (FilterClientCommand) other;
        return name.equals(e.name);
    }
}
